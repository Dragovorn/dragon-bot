package com.dragovorn.dragonbot.api.bot.event;

import com.dragovorn.dragonbot.bot.User;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 2:35 PM.
 * as of 8/11/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class UserMessageEvent {

    private final User user;

    private final String message;

    private final boolean command;

    public UserMessageEvent(User user, String message, boolean command) {
        this.user = user;
        this.message = message;
        this.command = command;
    }

    public User getUser() {
        return this.user;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isCommand() {
        return this.command;
    }
}