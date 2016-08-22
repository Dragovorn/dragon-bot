package com.dragovorn.dragonbot.api.twitch.resource;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 6:59 PM.
 * as of 8/21/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public abstract class AbstractResource {

    protected static final ObjectMapper objectMapper = new ObjectMapper();

    protected static final AsyncHttpClient http = new AsyncHttpdClient();

    private final String baseUrl;
}