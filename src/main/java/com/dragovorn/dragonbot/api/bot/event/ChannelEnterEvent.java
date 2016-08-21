package com.dragovorn.dragonbot.api.bot.event;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 3:23 PM.
 * as of 8/11/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class ChannelEnterEvent {

    private final String channel;

    public ChannelEnterEvent(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return this.channel;
    }
}