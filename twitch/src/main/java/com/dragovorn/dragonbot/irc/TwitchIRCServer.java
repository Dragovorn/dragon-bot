package com.dragovorn.dragonbot.irc;

import com.dragovorn.ircbot.api.irc.IConnection;
import com.dragovorn.ircbot.api.irc.IIRCServer;
import com.dragovorn.ircbot.impl.irc.Connection;

import java.io.IOException;

public final class TwitchIRCServer implements IIRCServer {

    private static final String IP_ADDRESS = "irc.twitch.tv";

    private IConnection connection;

    @Override
    public int getPort() {
        return 6667;
    }

    @Override
    public boolean isConnected() {
        return this.connection != null;
    }

    @Override
    public String getIP() {
        return IP_ADDRESS;
    }

    @Override
    public IConnection getConnection() {
        if (this.connection == null) {
            try {
                this.connection = new Connection(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return this.connection;
    }
}
