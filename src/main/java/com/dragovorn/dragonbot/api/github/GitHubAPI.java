package com.dragovorn.dragonbot.api.github;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

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

    public GitHubAPI(String owner, String repository) {
        this.owner = owner;
        this.repository = repository;

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
}