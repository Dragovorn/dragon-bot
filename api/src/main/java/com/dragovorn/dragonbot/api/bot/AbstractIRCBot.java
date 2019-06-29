package com.dragovorn.dragonbot.api.bot;

public abstract class AbstractIRCBot implements IIRCBot {

    private static IIRCBot instance;

    public AbstractIRCBot() {
        instance = this;
    }

    public static IIRCBot getInstance() {
        return instance;
    }
}
