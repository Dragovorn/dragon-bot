package com.dragovorn.dragonbot.gui.listener;

import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.gui.ConsoleWindow;
import com.dragovorn.dragonbot.gui.MainWindow;
import com.dragovorn.dragonbot.gui.panel.OptionsPanel;
import com.dragovorn.dragonbot.log.ConciseFormatter;
import com.dragovorn.dragonbot.log.ConsoleHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Handler;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 6:05 PM.
 * as of 8/6/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
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

        if (OptionsPanel.getInstance().getAutoConnect().isSelected() != Bot.getInstance().getConfiguration().getAutoConnect()) {
            Bot.getInstance().getConfiguration().setAutoConnect(OptionsPanel.getInstance().getAutoConnect().isSelected());
        }

        MainWindow.getInstance().getFrame().setContentPane(MainWindow.getInstance().getPanel());
        MainWindow.getInstance().getFrame().setTitle(MainWindow.TITLE);
        MainWindow.getInstance().getFrame().pack();
    }
}