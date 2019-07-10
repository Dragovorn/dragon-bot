package com.dragovorn.dragonbot.api.web;

import com.dragovorn.ircbot.api.IAPI;
import com.google.gson.JsonObject;

import java.io.IOException;

// TODO: Move this into a dragon-bot api folder instead of dragon-bot main, will happen when it gets it's own repo
public interface ITwitchAPI extends IAPI, IWebAPI {

    void invalidateToken(String token) throws IOException;

    String getUsernameFromAccessToken(String token) throws IOException;

    JsonObject lookupUser(String username) throws IOException;
    JsonObject lookupUser(long userId) throws IOException;
}
