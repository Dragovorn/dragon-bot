package com.dragovorn.dragonbot.user;

import com.dragovorn.ircbot.api.factory.IFactory;
import com.dragovorn.ircbot.api.user.UserInfo;

import java.io.IOException;

public final class TwitchUserFactory implements IFactory<TwitchUser, UserInfo> {

    @Override
    public TwitchUser create(UserInfo param) throws IOException {
        return new TwitchUser(param);
    }
}
