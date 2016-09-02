/*
 * Copyright (c) 2016. Andrew Burr
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 *  associated documentation files (the "Software"), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge, publish, distribute,
 *  sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE..
 */

package com.dragovorn.dragonbot.api.twitch;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

public class TwitchAPI {

    private static final String BASE_URL = "https://api.twitch.tv/kraken/";
    private static final String CHANNELS = "channels/";
    private static final String STREAMS = "streams/";
    private final String clientId;

    private final HttpClient client;

    public TwitchAPI(String clientId) {
        this.clientId = clientId;
        this.client = HttpClientBuilder.create().build();
    }

    public JSONObject getChannel(String channel) throws IOException {
        HttpResponse response = client.execute(makeGetRequest(BASE_URL + CHANNELS + channel));

        return new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
    }

    public JSONObject getStream(String channel) throws IOException {
        HttpResponse response = client.execute(makeGetRequest(BASE_URL + STREAMS + channel));

        return new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
    }

    public JSONObject getFollowers(String channel) throws IOException {
        return null;
    }

    private HttpGet makeGetRequest(String url) {
        HttpGet request = new HttpGet(url);
        request.addHeader("content-type", "application/json");
        request.addHeader("Client-ID", clientId);
        request.addHeader("Accept", "application/vnd.twitchtv.v3+json");

        return request;
    }
}