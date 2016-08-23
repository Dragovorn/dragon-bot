package com.dragovorn.dragonbot.http;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 12:38 AM.
 * as of 8/23/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class AsyncHttpClient extends HttpClient {

    private final ExecutorService threadPool;

    public AsyncHttpClient() {
        super();

        threadPool = Executors.newCachedThreadPool();
    }

    @Override
    public void delete(final String url, final HttpResponseHandler handler) {
        threadPool.execute(() -> {
            AsyncHttpClient.super.delete(url, handler);
        });
    }

    @Override
    public void delete(final String url, final RequestParams params, final HttpResponseHandler handler) {
        threadPool.execute(() -> {
            AsyncHttpClient.super.delete(url, params, handler);
        });
    }

    @Override
    public void get(final String url, final HttpResponseHandler handler) {
        threadPool.execute(() -> {
            AsyncHttpClient.super.get(url, handler);
        });
    }

    @Override
    public void get(final String url, final RequestParams params, final HttpResponseHandler handler) {
        threadPool.execute(() -> {
            AsyncHttpClient.super.get(url, params, handler);
        });
    }

    @Override
    public void head(final String url, final HttpResponseHandler handler) {
        threadPool.execute(() -> {
            AsyncHttpClient.super.head(url, handler);
        });
    }

    @Override
    public void head(final String url, final RequestParams params, final HttpResponseHandler handler) {
        threadPool.execute(() -> {
            AsyncHttpClient.super.head(url, params, handler);
        });
    }

    @Override
    public void post(final String url, final HttpResponseHandler handler) {
        threadPool.execute(() -> {
            AsyncHttpClient.super.post(url, handler);
        });
    }

    @Override
    public void post(final String url, final RequestParams params, final HttpResponseHandler handler) {
        threadPool.execute(() -> {
            AsyncHttpClient.super.post(url, params, handler);
        });
    }

    @Override
    public void put(final String url, final HttpResponseHandler handler) {
        threadPool.execute(() -> {
            AsyncHttpClient.super.put(url, handler);
        });
    }

    @Override
    public void put(final String url, final RequestParams params, final HttpResponseHandler handler) {
        threadPool.execute(() -> {
            AsyncHttpClient.super.put(url, params, handler);
        });
    }
}