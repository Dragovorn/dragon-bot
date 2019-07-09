package com.dragovorn.dragonbot.api.web;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;

public enum RequestType {
    GET(HttpGet.class),
    PUT(HttpPut.class),
    POST(HttpPost.class),
    DELETE(HttpDelete.class);

    private Class<? extends HttpRequestBase> clazz;

    RequestType(Class<? extends HttpRequestBase> clazz) {
        this.clazz = clazz;
    }

    public <T extends HttpRequestBase> T construct() {
        try {
            return (T) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
