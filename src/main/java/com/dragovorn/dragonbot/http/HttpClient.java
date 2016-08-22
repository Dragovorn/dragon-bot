package com.dragovorn.dragonbot.http;

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

    protected void request(String url, HttpRequestMethod method, RequestParams params, HttpRequestHandler handler) {

    }

    public void setUserAgent(String userAgent) {
        headers.put("User-Agent", userAgent);
    }
}