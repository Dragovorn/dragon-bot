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

package com.dragovorn.dragonbot.gui.listener;

import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.api.bot.command.ConsoleCommand;
import com.dragovorn.dragonbot.gui.ConsoleWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SendListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent event) {
        String cmd = "!" + ConsoleWindow.getInstance().getCommand().getText();

        ConsoleWindow.getInstance().getCommand().setText("");

        String[] args;

        for (ConsoleCommand command : Bot.getInstance().getCommandManager().getConsoleCommands()) {
            if ((args = Bot.getInstance().getCommandManager().parseCommand(command.getName(), cmd)) != null) {
                if (command.getArgs() == -1 || (command.isArgsRequired() ? args.length - 1 == command.getArgs() : args.length - 1 <= command.getArgs())) {
                    command.execute(Bot.getInstance().getCommandManager().parseCommand(command.getName(), cmd));
                    return;
                }
            }
        }

        Bot.getInstance().getLogger().info("\'" + cmd.substring(1) + "\' is not a valid developer command!");
    }
}