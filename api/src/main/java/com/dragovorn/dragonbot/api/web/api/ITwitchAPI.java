package com.dragovorn.dragonbot.api.web.api;

import com.dragovorn.dragonbot.api.IAPI;
import com.google.gson.JsonObject;

import java.io.IOException;

public interface ITwitchAPI extends IAPI, IWebAPI {

    void invalidateToken(String token) throws IOException;

    String getUsernameFromAccessToken(String token) throws IOException;

    JsonObject lookupUser(String username) throws IOException;
    JsonObject lookupUser(long userId) throws IOException;
}
