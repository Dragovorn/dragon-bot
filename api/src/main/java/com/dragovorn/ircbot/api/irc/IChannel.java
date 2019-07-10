package com.dragovorn.ircbot.api.irc;

public interface IChannel {

    void join();
    void sendMessage(String message);
    void sendRawMessage(String message);

    String getName();
}
