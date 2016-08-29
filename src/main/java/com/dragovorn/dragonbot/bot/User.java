package com.dragovorn.dragonbot.bot;

import com.google.common.collect.ImmutableMap;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 7:55 AM.
 * as of 8/6/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class User {

    private final String name;
    private final String login;
    private final String hostname;

    private final ImmutableMap<String, String> tags;

    User(String name, String login, String hostname, ImmutableMap<String, String> tags) {
        this.name = name;
        this.login = login;
        this.hostname = hostname;
        this.tags = tags;
    }

    public String getTag(String tag) {
        return tags.get(tag);
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getHostname() {
        return hostname;
    }

    public ImmutableMap<String, String> getTags() {
        return tags;
    }

    public boolean isMod() {
        return tags.get("mod").equals("1") || name.equals(Bot.getInstance().getChannel());
    }
}