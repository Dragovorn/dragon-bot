package com.dragovorn.dragonbot.http;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 7:23 PM.
 * as of 8/21/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public enum HttpRequestMethod {
    DELETE("DELETE"),
    GET("GET"),
    HEAD("HEAD"),
    OPTIONS("OPTIONS"),
    POST("POST"),
    PUT("PUT"),
    TRACE("TRACE");

    String key;

    HttpRequestMethod(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return this.key;
    }
}