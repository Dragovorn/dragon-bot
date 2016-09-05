/*
 * Copyright (c) 2016. Andrew Burr
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 *  associated documentation files (the "Software"), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge, publish, distribute,
 *  sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.dragovorn.dragonbot.api.bot.command;

import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.bot.BotState;
import com.google.common.collect.ImmutableList;
import com.sun.istack.internal.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private List<Command> commands;
    private List<ConsoleCommand> consoleCommands;

    public CommandManager() {
        commands = new ArrayList<>();
        consoleCommands = new ArrayList<>();
    }

    public void registerCommand(Command command) {
        if (Bot.getInstance().getState() != BotState.STARTING) {
            return;
        }

        if (commands.contains(command)) {
            return;
        }

        commands.add(command);
    }

    public void registerConsoleCommand(ConsoleCommand command) {
        if (Bot.getInstance().getState() != BotState.STARTING) {
            return;
        }

        if (consoleCommands.contains(command)) {
            return;
        }

        consoleCommands.add(command);
    }

    public ImmutableList<Command> getCommands() {
        return ImmutableList.copyOf(commands);
    }

    public ImmutableList<ConsoleCommand> getConsoleCommands() {
        return ImmutableList.copyOf(consoleCommands);
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