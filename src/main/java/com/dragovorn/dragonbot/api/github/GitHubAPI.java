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
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.dragovorn.dragonbot.api.github;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

public class GitHubAPI {

    private static final String BASE_URL = "https://api.github.com/";
    private static final String REPO = "repos/";
    private final String owner;
    private final String repository;

    private final HttpClient client;

    public GitHubAPI(String owner, String repository) {
        this.owner = owner;
        this.repository = repository;
        this.client = HttpClientBuilder.create().build();
    }

    public JSONObject getLatestRelease() throws IOException {
        HttpResponse response = client.execute(makeGetRequest(BASE_URL + REPO + this.owner + "/" + this.repository + "/releases/latest"));

        return new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
    }

    public JSONObject getRelease(String tag) throws IOException {
        HttpResponse response = client.execute(makeGetRequest(BASE_URL + REPO + this.owner + "/" + this.repository + "/releases/tags/" + tag));

        return new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
    }

    public int getNumCommitsBetween(String tag1, String tag2) throws IOException {
        HttpResponse response = client.execute(makeGetRequest(BASE_URL + REPO + this.owner + "/" + this.repository + "/compare/" + tag1 + "..." + tag2));

        JSONObject object = new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));

        if (object.has("message")) {
            return 0;
        }

        return object.getInt("total_commits");
    }

    private HttpGet makeGetRequest(String url) {
        HttpGet request = new HttpGet(url);
        request.addHeader("content-type", "application/json");

        return request;
    }
}