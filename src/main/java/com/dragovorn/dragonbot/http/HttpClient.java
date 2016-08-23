package com.dragovorn.dragonbot.http;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 7:04 PM.
 * as of 8/21/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public abstract class HttpClient {

    public static final String USER_AGENT = "Java-Async-Http";

    private final Map<String, String> headers;

    private int connectionTimeout = 20000;
    private int dataRetrievalTimeout = 20000;

    private boolean followRedirects = true;

    public HttpClient() {
        headers = Collections.synchronizedMap(new LinkedHashMap<String, String>());
        setUserAgent(USER_AGENT);
    }

    protected void request(String url, HttpRequestMethod method, RequestParams params, HttpResponseHandler handler) {
        HttpURLConnection connection = null;

        if (params == null) {
            params = new RequestParams();
        }

        if (method != HttpRequestMethod.POST && method != HttpRequestMethod.PUT) {
            if (params.size() > 0) {
                url = url + "?" + params.toEncodedString();
            }
        }

        try {
            URL resourceUrl = new URL(url);
            connection = (HttpURLConnection) resourceUrl.openConnection();

            connection.setConnectTimeout(connectionTimeout);
            connection.setReadTimeout(dataRetrievalTimeout);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(followRedirects);
            connection.setRequestMethod(method.toString());
            connection.setDoInput(true);

            for (Map.Entry<String, String> header : headers.entrySet()) {
                connection.setRequestProperty(header.getKey(), header.getValue());
            }

            handler.onStart(connection);

            if (method == HttpRequestMethod.POST || method == HttpRequestMethod.PUT) {
                connection.setDoOutput(true);

                if (params.hasFiles()) {
                    connection.setChunkedStreamingMode(32 * 1024);
                    MultipartWriter.write(connection, params);
                } else {
                    byte[] content = params.toEncodedString().getBytes();
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + params.getCharset().name());
                    connection.setRequestProperty("Content-Length", Long.toString(content.length));
                    connection.setFixedLengthStreamingMode(content.length);

                    try (OutputStream outputStream = connection.getOutputStream()) {
                        outputStream.write(content);
                    }
                }
            }

            handler.processResponse(connection);
            handler.onFinish(connection);
        } catch (IOException exception) {
            handler.onFailure(exception);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public void delete(String url, HttpResponseHandler handler) {
        request(url, HttpRequestMethod.DELETE, null, handler);
    }

    public void delete(String url, RequestParams params, HttpResponseHandler handler) {
        request(url, HttpRequestMethod.DELETE, params, handler);
    }

    public void get(String url, HttpResponseHandler handler) {
        request(url, HttpRequestMethod.GET, null, handler);
    }

    public void get(String url, RequestParams params, HttpResponseHandler handler) {
        request(url, HttpRequestMethod.GET, params, handler);
    }

    public void head(String url, HttpResponseHandler handler) {
        request(url, HttpRequestMethod.HEAD, null, handler);
    }

    public void head(String url, RequestParams params, HttpResponseHandler handler) {
        request(url, HttpRequestMethod.HEAD, params, handler);
    }

    public void post(String url, HttpResponseHandler handler) {
        request(url, HttpRequestMethod.POST, null, handler);
    }

    public void post(String url, RequestParams params, HttpResponseHandler handler) {
        request(url, HttpRequestMethod.POST, params, handler);
    }

    public void put(String url, HttpResponseHandler handler) {
        request(url, HttpRequestMethod.PUT, null, handler);
    }

    public void put(String url, RequestParams params, HttpResponseHandler handler) {
        request(url, HttpRequestMethod.PUT, params, handler);
    }

    public void setHeader(String name, String value) {
        headers.put(name, value);
    }

    public void removeHeader(String name) {
        headers.remove(name);
    }

    public String getUserAgent() {
        return headers.get("User-Agent");
    }

    public void setUserAgent(String userAgent) {
        headers.put("User-Agent", userAgent);
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getDataRetrievalTimeout() {
        return dataRetrievalTimeout;
    }

    public void setDataRetrievalTimeout(int dataRetrievalTimeout) {
        this.dataRetrievalTimeout = dataRetrievalTimeout;
    }

    public boolean willFollowRedirects() {
        return followRedirects;
    }

    public void setFollowRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
    }

    public void setBasicAuth(String username, String password) {
        String encoded = DatatypeConverter.printBase64Binary((username + ":" + password).getBytes());

        headers.put("Authorization", "Basic " + encoded);
    }

    public void clearBasicAuth() {
        headers.remove("Authorization");
    }
}