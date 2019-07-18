package com.dragovorn.ircbot.impl.user;

import com.dragovorn.ircbot.api.user.IUser;
import com.dragovorn.ircbot.api.user.UserInfo;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class User implements IUser {

    protected String username;
    protected String login;
    protected String hostname;

    protected ImmutableMap<String, String> tags;

    public User(UserInfo userInfo) {
        this.username = userInfo.getUsername();
        this.login = userInfo.getLogin();
        this.hostname = userInfo.getHostname();
        this.tags = userInfo.getTags();
    }

    @Override
    public boolean isValid() {
        return !this.username.equals("") || !this.login.equals("") || !this.hostname.equals("");
    }

    @Override
    public boolean hasTag(String tag) {
        return this.tags.containsKey(tag);
    }

    @Override
    public String getLogin() {
        return this.login;
    }

    @Override
    public String getHostname() {
        return this.hostname;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getTag(String tag) {
        return this.tags.get(tag);
    }

    @Override
    public ImmutableMap<String, String> getTags() {
        return this.tags;
    }
}
