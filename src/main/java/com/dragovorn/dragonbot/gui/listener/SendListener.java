package com.dragovorn.dragonbot.gui.listener;

import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.api.bot.command.ConsoleCommand;
import com.dragovorn.dragonbot.gui.ConsoleWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 2:03 PM.
 * as of 8/6/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
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