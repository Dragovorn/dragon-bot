package com.dragovorn.dragonbot.api.channel;

public interface IChannel {

    void join();
    void sendMessage(String message);
    void sendRawMessage(String message);

    String getName();
}
