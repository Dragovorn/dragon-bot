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

    private static final String BASE_URL = "https://api.github.com/";
    private static final String REPO = "repos/";
    private final String owner;
    private final String repository;

    private final HttpClient client;

    private final boolean prereleases;

    public GitHubAPI(String owner, String repository, boolean prereleases) {
        this.owner = owner;
        this.repository = repository;
        this.prereleases = prereleases;
        this.client = HttpClientBuilder.create().build();
    }

    public JSONObject getLatestRelease() throws IOException {
        HttpResponse response = client.execute(makeGetRequest(BASE_URL + REPO + owner + "/" + repository + "/releases/latest"));

        return new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
    }

    public JSONObject getRelease(String tag) throws IOException {
        HttpResponse response = client.execute(makeGetRequest(BASE_URL + REPO + owner + "/" + repository + "/releases/tags/" + tag));

        return new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
    }

    public Map<String, String> getReleases() throws IOException {
        HashMap<String, String> strs = new HashMap<>();

        HttpResponse response = client.execute(makeGetRequest(BASE_URL + REPO + owner + "/" + repository + "/releases"));

        JSONArray array = new JSONArray(EntityUtils.toString(response.getEntity(), "UTF-8"));

        for (Object object : array) {
            JSONObject jsonObject = new JSONObject(object.toString());

            if (jsonObject.getBoolean("prerelease") && !prereleases) {
                continue;
            }

            strs.put(jsonObject.getString("tag_name").substring(1), jsonObject.getJSONArray("assets").getJSONObject(0).getString("browser_download_url"));
        }

        return strs;
    }

    private HttpGet makeGetRequest(String url) {
        HttpGet request = new HttpGet(url);
        request.addHeader("content-type", "application/json");

        return request;
    }
}