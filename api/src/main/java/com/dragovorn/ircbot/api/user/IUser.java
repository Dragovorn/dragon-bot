package com.dragovorn.ircbot.api.user;

import com.google.common.collect.ImmutableMap;

public interface IUser {

    boolean isMod();
    boolean hasTag(String tag);

    long getUserId();

    String getLogin();
    String getHostname();
    String getUsername();
    String getTag(String tag);

    ImmutableMap<String, String> getTags();
}
