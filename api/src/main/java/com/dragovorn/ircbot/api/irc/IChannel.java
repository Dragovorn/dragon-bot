package com.dragovorn.ircbot.api.irc;

public interface IChannel {

    void join();
    void part();
    void sendMessage(String message);

    String getName();
}
