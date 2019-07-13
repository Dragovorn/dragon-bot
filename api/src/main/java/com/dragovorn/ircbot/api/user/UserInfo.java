package com.dragovorn.ircbot.api.user;

import com.google.common.collect.ImmutableMap;

public class UserInfo {

    private final String login;
    private final String hostname;
    private final String username;

    private final ImmutableMap<String, String> tags;

    public UserInfo(String login, String hostname, String username, ImmutableMap<String, String> tags) {
        this.login = login;
        this.hostname = hostname;
        this.username = username;
        this.tags = tags;
    }

    public String getLogin() {
        return this.login;
    }

    public String getHostname() {
        return this.hostname;
    }

    public String getUsername() {
        return this.username;
    }

    public ImmutableMap<String, String> getTags() {
        return this.tags;
    }
}
