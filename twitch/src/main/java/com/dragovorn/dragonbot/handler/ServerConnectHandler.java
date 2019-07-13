package com.dragovorn.dragonbot.handler;

import com.dragovorn.ircbot.api.event.Listener;
import com.dragovorn.ircbot.api.event.irc.server.ServerConnectEvent;

import java.io.IOException;

public class ServerConnectHandler {

    @Listener
    public void onServerConnect(ServerConnectEvent event) throws IOException {
        event.getConnection().sendRawLine("CAP REQ :twitch.tv/membership");
        event.getConnection().sendRawLine("CAP REQ :twitch.tv/commands");
        event.getConnection().sendRawLine("CAP REQ :twitch.tv/tags");

        event.getConnection().joinChannel("dragovorn");
    }
}
