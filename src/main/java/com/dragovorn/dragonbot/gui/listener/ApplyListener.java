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
import com.dragovorn.dragonbot.DragonBot;
import com.dragovorn.dragonbot.exception.ConnectionException;
import com.dragovorn.dragonbot.gui.ConsoleWindow;
import com.dragovorn.dragonbot.gui.MainWindow;
import com.dragovorn.dragonbot.gui.panel.OptionsPanel;
import com.dragovorn.dragonbot.log.ConciseFormatter;
import com.dragovorn.dragonbot.log.ConsoleHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Handler;

public class ApplyListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent event) {
        if (OptionsPanel.getInstance().getConsole().isSelected() != Bot.getInstance().getConfiguration().getConsole()) {
            Bot.getInstance().getConfiguration().setConsole(OptionsPanel.getInstance().getConsole().isSelected());

            if (OptionsPanel.getInstance().getConsole().isSelected()) {
                ConsoleHandler console = new ConsoleHandler();
                console.setTextArea(new ConsoleWindow().getConsole());
                console.setFormatter(new ConciseFormatter());
                Bot.getInstance().getLogger().addHandler(console);
            } else {
                for (Handler handler : Bot.getInstance().getLogger().getHandlers()) {
                    if (handler instanceof ConsoleHandler) {
                        handler.close();
                    }

                    ConsoleWindow.getInstance().getFrame().dispose();
                }
            }
        }

        DragonBot.getInstance().getConfiguration().setAPIKey(OptionsPanel.getInstance().getClientId());
        DragonBot.getInstance().getTwitchAPI().setClientId(OptionsPanel.getInstance().getClientId());

        if (OptionsPanel.getInstance().getAutoConnect().isSelected() != Bot.getInstance().getConfiguration().getAutoConnect()) {
            Bot.getInstance().getConfiguration().setAutoConnect(OptionsPanel.getInstance().getAutoConnect().isSelected());
        }

        try {
            if (OptionsPanel.getInstance().hasAccountInfoChanged()) {
                if (OptionsPanel.getInstance().accountInfoTested()) {
                    OptionsPanel.getInstance().updateInfo();
                    DragonBot.getInstance().connect();

                    MainWindow.getInstance().getChannelButton().setEnabled(true);
                    MainWindow.getInstance().getChannelButton().setToolTipText("");
                } else {
                    OptionsPanel.getInstance().getTestStatus().setText("Test account!");
                    OptionsPanel.getInstance().getTestStatus().setForeground(Color.red);

                    String buttons[] = { "Ok" };

                    JOptionPane.showOptionDialog(null, "Please make sure to test your account connectivity before applying changes!", "Twitch Account", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, buttons, buttons[0]);
                    return;
                }
            }
        } catch (ConnectionException | IOException exception) {
            Bot.getInstance().getLogger().info("Failed to connect to twitch! (Post connection)");

            MainWindow.getInstance().getChannelButton().setEnabled(false);
            MainWindow.getInstance().getChannelButton().setToolTipText("The current account was unable to connect!");
        }

        MainWindow.getInstance().setContentPane(MainWindow.getInstance().getPanel());
        MainWindow.getInstance().setTitle(MainWindow.TITLE);
        MainWindow.getInstance().pack();
    }
}