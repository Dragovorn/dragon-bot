package com.dragovorn.dragonbot.api.bot.channel;

public interface IChannel {

    void sendMessage(String message);
    void join();

    String getName();
}
