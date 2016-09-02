package com.dragovorn.dragonbot.api.twitch;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 3:23 AM.
 * as of 8/27/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
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