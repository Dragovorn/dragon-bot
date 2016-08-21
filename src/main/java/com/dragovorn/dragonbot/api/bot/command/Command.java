package com.dragovorn.dragonbot.api.bot.command;

import com.dragovorn.dragonbot.bot.User;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 9:33 AM.
 * as of 8/10/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public abstract class Command {

    private final boolean argsRequired;

    private final int requiredArgs;

    private final String name;

    public Command(String name, int requiredArgs, boolean argsRequired) {
        this.name = name;
        this.requiredArgs = requiredArgs;
        this.argsRequired = argsRequired;
    }

    public final boolean isArgsRequired() {
        return argsRequired;
    }

    public final int getArgs() {
        return requiredArgs;
    }

    public final String getName() {
        return name;
    }

    public abstract void execute(User user, String[] args);
}