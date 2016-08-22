package com.dragovorn.dragonbot.http;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 7:26 PM.
 * as of 8/21/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class RequestParams {

    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private ConcurrentHashMap<String, String> stringParams = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, File> fileParams = new ConcurrentHashMap<>();

    private Charset charset;

    public RequestParams() {
        charset = DEFAULT_CHARSET;
    }

    public RequestParams(String key, String value) {
        this();
        stringParams.put(key, value);
    }

    public RequestParams(String key, File file) {
        this();
        fileParams.put(key, file);
    }

    public boolean containsKey(String key) {
        return stringParams.containsKey(key) || fileParams.containsKey(key);
    }

    public void put(String key, String value) {
        stringParams.put(key, value);
    }

    public void put(String key, short value) {
        stringParams.put(key, Short.toString(value));
    }

    public void put(String key, int value) {
        stringParams.put(key, Integer.toString(value));
    }

    public void put(String key, double value) {
        stringParams.put(key, Double.toString(value));
    }

    public void put(String key, float value) {
        stringParams.put(key, Float.toString(value));
    }

    public void put(String key, long value) {
        stringParams.put(key, Long.toString(value));
    }

    public void put(String key, boolean value) {
        stringParams.put(key, Boolean.toString(value));
    }

    public void put(String key, char value) {
        stringParams.put(key, Character.toString(value));
    }

    public void put(Map<String, String> otherMap) {
        stringParams.putAll(otherMap);
    }

    public void put(String key, File file) {
        fileParams.put(key, file);
    }

    public String getString(String key) {
        return stringParams.get(key);
    }

    public File getFile(String key) {
        return fileParams.get(key);
    }

    public void remove(String key) {
        stringParams.remove(key);
    }

    public Set<Map.Entry<String, String>> stringEntrySet() {
        return stringParams.entrySet();
    }

    public Set<Map.Entry<String, File>> fileEntrySet() {
        return fileParams.entrySet();
    }

    public boolean hasFiles() {
        return fileParams.size() > 0;
    }

    public int size() {
        return stringParams.size() + fileParams.size();
    }

    public Charset getCharset() {
        return this.charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public String toEncodedString() {
        try {
            StringBuilder builder = new StringBuilder();

            for (ConcurrentHashMap.Entry<String, String> param : stringParams.entrySet()) {
                if (builder.length() > 0) {
                    builder.append("&");
                }

                builder.append(URLEncoder.encode(param.getKey(), charset.name()));
                builder.append("=");
                builder.append(URLEncoder.encode(param.getValue(), charset.name()));
            }

            return builder.toString();
        } catch (UnsupportedEncodingException exception) {
            exception.printStackTrace();
            return "";
        }
    }
}