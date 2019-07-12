package com.dragovorn.ircbot.api.event.irc;

import com.dragovorn.ircbot.api.event.IEvent;
import com.dragovorn.ircbot.api.irc.IConnection;

public class IRCEvent implements IEvent {

    private final IConnection connection;

    public IRCEvent(IConnection connection) {
        this.connection = connection;
    }

    public IConnection getConnection() {
        return this.connection;
    }
}
