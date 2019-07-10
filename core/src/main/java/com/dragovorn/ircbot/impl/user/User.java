package com.dragovorn.ircbot.impl.user;

import com.dragovorn.ircbot.api.user.IUser;
import com.google.common.collect.ImmutableMap;

public class User implements IUser {

    protected String username;

    @Override
    public boolean isValid() {
        return true; // TODO: Stubbed.
    }

    @Override
    public boolean hasTag(String tag) {
        throw new UnsupportedOperationException("Unimplemented");
    }

    @Override
    public String getLogin() {
        throw new UnsupportedOperationException("Unimplemented");
    }

    @Override
    public String getHostname() {
        throw new UnsupportedOperationException("Unimplemented");
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getTag(String tag) {
        throw new UnsupportedOperationException("Unimplemented");
    }

    @Override
    public ImmutableMap<String, String> getTags() {
        throw new UnsupportedOperationException("Unimplemented");
    }
}
