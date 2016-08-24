package com.dragovorn.dragonbot.api.github;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 5:54 PM.
 * as of 8/24/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class GitHubAPI {

    private static final String BASE_URL = "https://github.com/";
    private final String owner;
    private final String repository;

    public GitHubAPI(String owner, String repository) {
        this.owner = owner;
        this.repository = repository;
    }
}