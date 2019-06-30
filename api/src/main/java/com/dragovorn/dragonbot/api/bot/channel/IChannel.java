package com.dragovorn.dragonbot.api.bot.channel;

public interface IChannel {

    void join();
    void sendMessage(String message);
    void sendRawMessage(String message);

    String getName();
}
