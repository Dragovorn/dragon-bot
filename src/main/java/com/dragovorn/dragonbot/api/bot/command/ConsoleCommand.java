package com.dragovorn.dragonbot.api.bot.command;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 4:34 PM.
 * as of 8/16/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public abstract class ConsoleCommand {
    private final boolean argsRequired;

    private final int requiredArgs;

    private final String name;

    public ConsoleCommand(String name, int requiredArgs, boolean argsRequired) {
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

    public abstract void execute(String[] args);
}