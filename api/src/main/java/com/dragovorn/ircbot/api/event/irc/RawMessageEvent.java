package com.dragovorn.ircbot.api.event.irc;

import com.dragovorn.ircbot.api.irc.IConnection;

public abstract class RawMessageEvent extends IRCEvent {

    private String message;

    RawMessageEvent(IConnection connection, String message) {
        super(connection);

        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
