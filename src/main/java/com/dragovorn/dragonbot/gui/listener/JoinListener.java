package com.dragovorn.dragonbot.gui.listener;

import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.gui.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 3:25 PM.
 * as of 8/6/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class JoinListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent event) {
        if (!Bot.getInstance().getChannel().equals("")) {
            return; // Bot is already in a channel.
        }

        if (!MainWindow.getInstance().getChannel().getText().equals("") || !Bot.getInstance().getConfiguration().getChannel().equals("")) {
            if (!MainWindow.getInstance().getChannel().getText().equals("")) {
                Bot.getInstance().connectTo("#" + MainWindow.getInstance().getChannel().getText());
                MainWindow.getInstance().getChannelPrompt().setText(MainWindow.getInstance().getChannel().getText());
            } else {
                Bot.getInstance().connectTo("#" + Bot.getInstance().getConfiguration().getChannel());
            }

            MainWindow.getInstance().getChannelButton().setText("Leave Channel");
            MainWindow.getInstance().getChannelButton().removeActionListener(MainWindow.getInstance().getChannelButton().getActionListeners()[0]);
            MainWindow.getInstance().getChannelButton().addActionListener(new LeaveListener());
            MainWindow.getInstance().getChannel().setText("");
            MainWindow.getInstance().getChannel().removeActionListener(MainWindow.getInstance().getChannel().getActionListeners()[0]);
            MainWindow.getInstance().getChannel().setEditable(false);
        }
    }
}