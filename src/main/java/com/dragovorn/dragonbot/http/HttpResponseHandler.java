package com.dragovorn.dragonbot.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 7:53 PM.
 * as of 8/22/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public abstract class HttpResponseHandler {

    protected static int BUFFER_SIZE = 1024 * 8;

    public void onStart(HttpURLConnection connection) { }

    public void onFinish(HttpURLConnection connection) { }

    public abstract void onSuccess(int code, Map<String, List<String>> headers, byte[] content);

    public abstract void onFailure(int code, Map<String, List<String>> headers, byte[] content);

    public abstract void onFailure(Throwable throwable);

    public void onProgressChanged(long bytesReceived, long totalBytes) { }

    protected byte[] readFrom(InputStream stream, long length) throws IOException {
        if (stream == null) {
            return new byte[0];
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];

        int bytesRead;

        while ((bytesRead = stream.read(buffer, 0, buffer.length)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
            onProgressChanged(bytesRead, length);
        }

        outputStream.flush();
        outputStream.close();

        return outputStream.toByteArray();
    }

    protected void processResponse(HttpURLConnection connection) {
        try {
            int responseCode = connection.getResponseCode();
            long contentLength = connection.getContentLength();

            Map<String, List<String>> responseHeaders = connection.getHeaderFields();

            if (responseCode >= 200 && responseCode < 300) {
                byte[] responseContent = readFrom(connection.getInputStream(), contentLength);
                onSuccess(responseCode, responseHeaders, responseContent);
            } else {
                byte[] responseContent = readFrom(connection.getErrorStream(), contentLength);
                onFailure(responseCode, responseHeaders, responseContent);
            }
        } catch (IOException exception) {
            onFailure(exception);
        }
    }
}