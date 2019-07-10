package com.dragovorn.ircbot.api.irc;

public interface IIRCServer {

    int getPort();

    boolean isConnected();

    String getIP();

    IConnection getConnection();
}
