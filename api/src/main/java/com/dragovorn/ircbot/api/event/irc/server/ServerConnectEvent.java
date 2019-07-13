package com.dragovorn.ircbot.api.event.irc.server;

import com.dragovorn.ircbot.api.event.irc.IRCEvent;
import com.dragovorn.ircbot.api.irc.IConnection;

public class ServerConnectEvent extends IRCEvent {

    public ServerConnectEvent(IConnection connection) {
        super(connection);
    }
}
