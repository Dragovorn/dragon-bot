package com.dragovorn.dragonbot.http;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 12:58 AM.
 * as of 8/23/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public abstract class StringHttpResponseHandler extends HttpResponseHandler {

    public static String DEFAULT_CHARSET = "UTF-8";

    private static String extractContentCharset(Map<String, List<String>> headers) {
        List<String> contentTypes = headers.get("Content-Type");

        if (contentTypes != null) {
            String contentType = contentTypes.get(0);
            String charset = null;

            if (contentType != null) {
                for (String param : contentType.replace(" ", "").split(";")) {
                    if (param.startsWith("charset=")) {
                        charset = param.split("=", 2)[1];
                        break;
                    }
                }
            }

            return charset;
        }

        return DEFAULT_CHARSET;
    }

    private static String getContentString(byte[] content, String charset) {
        if (content == null || content.length == 0) {
            return "";
        }

        if (charset == null) {
            charset = DEFAULT_CHARSET;
        }

        try {
            return new String(content, charset);
        } catch (UnsupportedEncodingException exception) {
            return "";
        }
    }

    public abstract void onSuccess(int code, Map<String, List<String>> headers, String content);

    @Override
    public void onSuccess(int code, Map<String, List<String>> headers, byte[] content) {
        onSuccess(code, headers, getContentString(content, extractContentCharset(headers)));
    }

    public abstract void onFailure(int code, Map<String, List<String>> headers, String content);

    @Override
    public void onFailure(int code, Map<String, List<String>> headers, byte[] content) {
        onFailure(code, headers, getContentString(content, extractContentCharset(headers)));
    }

    @Override
    public abstract void onFailure(Throwable throwable);
}