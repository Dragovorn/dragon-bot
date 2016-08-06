package com.dragovorn.dragonbot.gui.listener;

import com.dragovorn.dragonbot.bot.Bot;
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
        String cmd = ConsoleWindow.getInstance().getCommand().getText();

        ConsoleWindow.getInstance().getCommand().setText("");

        if (cmd.startsWith("chat ")) {
            Bot.getInstance().getLogger().info(cmd);
            Bot.getInstance().sendMessage(cmd.substring(5));
        } else {
            Bot.getInstance().getLogger().info("\'" + cmd.split(" ")[0] + "\' is not a valid developer command!");
        }
    }
}