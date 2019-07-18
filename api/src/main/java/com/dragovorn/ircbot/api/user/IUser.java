package com.dragovorn.ircbot.api.user;

import com.google.common.collect.ImmutableMap;

public interface IUser {

    boolean isValid();
    boolean hasTag(String tag);

    String getLogin();
    String getHostname();
    String getUsername();
    String getTag(String tag);

    ImmutableMap<String, String> getTags();
}
