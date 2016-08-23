package com.dragovorn.dragonbot.api.twitch.resource;

import com.dragovorn.dragonbot.api.twitch.handler.BaseFailureHandler;
import com.dragovorn.dragonbot.api.twitch.model.Error;
import com.dragovorn.dragonbot.http.AsyncHttpClient;
import com.dragovorn.dragonbot.http.StringHttpResponseHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 6:59 PM.
 * as of 8/21/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public abstract class AbstractResource {

    protected static final ObjectMapper objectMapper = new ObjectMapper();

    protected static final AsyncHttpClient http = new AsyncHttpClient();

    private final String baseUrl;

    protected AbstractResource(String baseUrl, int version) {
        this.baseUrl = baseUrl;
        http.setHeader("ACCEPT", "application/vnd.twitchtv.v" + Integer.toString(version) + ".json");
        configureObjectManager();
    }

    private void configureObjectManager() {
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
    }

    public void setAuthAccessToken(String accessToken) {
        if (accessToken != null) {
            http.setHeader("Authorization", String.format("OAuth %s", accessToken));
        } else {
            http.removeHeader("Authorization");
        }
    }

    public void setClientId(String clientId) {
        if (clientId != null && clientId.length() > 0) {
            http.setHeader("Client-ID", clientId);
        } else {
            http.removeHeader("Client-ID");
        }
    }

    protected String getBaseUrl() {
        return baseUrl;
    }

    protected static abstract class TwitchHttpResponseHandler extends StringHttpResponseHandler {

        private BaseFailureHandler handler;

        public TwitchHttpResponseHandler(BaseFailureHandler handler) {
            this.handler = handler;
        }

        @Override
        public abstract void onSuccess(int code, Map<String, List<String>> headers, String content);

        @Override
        public void onFailure(int code, Map<String, List<String>> headers, String content) {
            try {
                if (content.length() > 0) {
                    Error error = objectMapper.readValue(content, Error.class);
                    handler.onFailure(code, error.getStatusText(), error.getMessage());
                } else {
                    handler.onFailure(code, "", "");
                }
            } catch (IOException exception) {
                handler.onFailure(exception);
            }
        }

        @Override
        public void onFailure(Throwable throwable) {
            handler.onFailure(throwable);
        }
    }
}