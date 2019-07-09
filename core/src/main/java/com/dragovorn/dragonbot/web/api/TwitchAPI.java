package com.dragovorn.dragonbot.web.api;

import com.dragovorn.dragonbot.DragonBot;
import com.dragovorn.dragonbot.api.web.RequestType;
import com.dragovorn.dragonbot.api.web.api.ITwitchAPI;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class TwitchAPI implements ITwitchAPI {

    private static final String API_BASE_URL = "https://api.twitch.tv/";
    private static final String ID_BASE_URL = "https://id.twitch.tv/";

    @Override
    public void mutateRequest(HttpRequestBase requestBase, String url, Map<String, Object> data) {
        requestBase.addHeader("Client-ID", DragonBot.CLIENT_ID); // Add our client-id to all requests made.

        if (!data.containsKey("meta.token")) { // If we have a token meta then we want to add a token to the headers too
            return;
        }

        if (data.containsKey("meta.v5")) { // Since twitch can't keep their authentication headers straight between versions.
            requestBase.addHeader("Authorization", "OAuth " + data.get("meta.token"));
        } else {
            requestBase.addHeader("Authorization", "Bearer " + data.get("meta.token"));
        }
    }

    @Override
    public void invalidateToken(String token) throws IOException {
        makeRequest(RequestType.POST, APIVersion.OAUTH2.createURL("revoke"), new HashMap<String, Object>() {{
                put("client_id", DragonBot.CLIENT_ID);
                put("token", token);
            }});
    }

    @Override
    public String getUsernameFromAccessToken(String token) throws IOException {
        return makeRequest(RequestType.GET, APIVersion.HELIX.createURL("users"), new HashMap<String, Object>() {{
            put("meta.token", token);
        }}).get("data").getAsJsonArray().get(0).getAsJsonObject().get("login").getAsString();
    }

    @Override
    public JsonObject lookupUser(String username) throws IOException {
        return makeRequest(RequestType.GET, APIVersion.HELIX.createURL("users"), new HashMap<String, Object>() {{
            put("login", username);
        }}).getAsJsonArray("data").get(0).getAsJsonObject();
    }

    @Override
    public JsonObject lookupUser(long userId) throws IOException {
        return makeRequest(RequestType.GET, APIVersion.HELIX.createURL("users"), new HashMap<String, Object>() {{
            put("id", userId);
        }}).getAsJsonArray("data").get(0).getAsJsonObject();
    }

    private enum APIVersion {
        HELIX("helix", API_BASE_URL),
        OAUTH2("oauth2", ID_BASE_URL);

        private String url;
        private String baseUrl;

        APIVersion(String url, String baseUrl) {
            this.url = url;
            this.baseUrl = baseUrl;
        }

        public String createURL(String path) {
            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }

            if (path.length() > 0 && !path.startsWith("/")) {
                path = "/" + path;
            }

            return this.baseUrl + this.url + path;
        }
    }
}
