package com.dragovorn.ircbot.api.bot;

public abstract class IRCBot implements IIRCBot {

    private static IIRCBot instance;

    public IRCBot() {
        instance = this;
    }

    public static IIRCBot getInstance() {
        return instance;
    }
}
