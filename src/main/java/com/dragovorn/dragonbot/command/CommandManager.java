package com.dragovorn.dragonbot.command;

import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.bot.BotState;
import com.google.common.collect.ImmutableList;
import com.sun.istack.internal.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 9:33 AM.
 * as of 8/10/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class CommandManager {

    private List<Command> commands;

    public CommandManager() {
        commands = new ArrayList<>();
    }

    public void registerCommand(Command command) {
        if (Bot.getInstance().getState() != BotState.STARTING) {
            return;
        }

        commands.add(command);
    }

    public ImmutableList<Command> getCommands() {
        return ImmutableList.copyOf(commands);
    }

    @Nullable
    public String[] parseCommand(@NotNull String prefix, @NotNull String message) {
        prefix = "!" + prefix;

        if (message.startsWith(prefix)) {
            return message.substring(prefix.length()).split(" ");
        }

        return null;
    }
}