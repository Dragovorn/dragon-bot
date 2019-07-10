package com.dragovorn.dragonbot.api.irc;

public interface IIRCServer {

    int getPort();

    String getIP();

    IConnection connect();
}
