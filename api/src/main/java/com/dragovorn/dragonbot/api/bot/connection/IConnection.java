package com.dragovorn.dragonbot.api.bot.connection;

public interface IConnection {

    String getUsername();
    String getPassword();
    String getChannel();

    boolean isSSL();
    boolean shouldVerifySSL();
}
