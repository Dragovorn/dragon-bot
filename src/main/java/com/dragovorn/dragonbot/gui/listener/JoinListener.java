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
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE..
 */

package com.dragovorn.dragonbot.gui.listener;

import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.gui.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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