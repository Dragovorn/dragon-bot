package com.dragovorn.dragonbot.gui.listener;

import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.gui.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 3:34 PM.
 * as of 8/6/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class LeaveListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent event) {
        if (Bot.getInstance().getChannel().equals("")) {
            return; // The bot isn't in a channel.
        }

        Bot.getInstance().leaveChannel();

        MainWindow.getInstance().getChannel().setEditable(true);
        MainWindow.getInstance().getChannel().addActionListener(new JoinListener());
        MainWindow.getInstance().getChannelButton().setText("Join Channel");
        MainWindow.getInstance().getChannelButton().removeActionListener(this);
        MainWindow.getInstance().getChannelButton().addActionListener(new JoinListener());
    }
}