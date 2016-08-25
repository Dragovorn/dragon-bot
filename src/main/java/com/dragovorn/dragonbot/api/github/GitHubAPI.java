package com.dragovorn.dragonbot.api.github;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 5:54 PM.
 * as of 8/24/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class GitHubAPI {

    private enum GitApiType {

        REPO("repos/");

        private String path;

        GitApiType(String path) {
            this.path = path;
        }
    }

    private static final String BASE_URL = "https://api.github.com/";
    private final String owner;
    private final String repository;

    private HttpClient client;

    private final boolean prereleases;

    public GitHubAPI(String owner, String repository, boolean prereleases) {
        this.owner = owner;
        this.repository = repository;
        this.prereleases = prereleases;

        client = HttpClientBuilder.create().build();
    }

    public JSONObject getLatestRelease() throws IOException {
        HttpGet request = new HttpGet(BASE_URL + GitApiType.REPO.path + owner + "/" + repository + "/releases/latest");
        request.addHeader("content-type", "application/json");
        HttpResponse response = client.execute(request);

        return new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
    }

    public JSONObject getRelease(String tag) throws IOException {
        HttpGet request = new HttpGet(BASE_URL + GitApiType.REPO.path + owner + "/" + repository + "/releases/tags/" + tag);
        request.addHeader("content-type", "application/json");
        HttpResponse response = client.execute(request);

        return new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
    }

    public Map<String, String> getReleases() throws IOException {
        HashMap<String, String> strs = new HashMap<>();

        HttpClient client = HttpClientBuilder.create().build();

        HttpGet request = new HttpGet("https://api.github.com/repos/dragovorn/dragon-bot-twitch/releases");
        request.addHeader("content-type", "application/json");

        HttpResponse response = client.execute(request);

        JSONArray array = new JSONArray(EntityUtils.toString(response.getEntity(), "UTF-8"));

        for (Object object : array) {
            JSONObject jsonObject = new JSONObject(object.toString());

            if (jsonObject.getBoolean("prerelease") && prereleases) {
                continue;
            }

            strs.put(jsonObject.getString("tag_name").substring(1), jsonObject.getJSONArray("assets").getJSONObject(0).getString("browser_download_url"));
        }

        return strs;
    }
}