package com.dragovorn.dragonbot.irc;

import com.dragovorn.ircbot.impl.user.BotAccount;

public final class TwitchBotAccount extends BotAccount {

    @Override
    public String getPassword() {
        return "oauth:" + super.getPassword();
    }
}
