package com.dragovorn.dragonbot.gui.panel;

import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.gui.listener.ApplyListener;
import com.dragovorn.dragonbot.gui.listener.BackListener;

import javax.swing.*;
import java.awt.*;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 4:19 PM.
 * as of 8/6/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class OptionsPanel extends JPanel {

    private JCheckBox console;
    private JCheckBox autoConnect;

    private static OptionsPanel instance;

    public OptionsPanel() {
        instance = this;

        Dimension size = new Dimension(500, 500);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
        setSize(size);

        JPanel options = new JPanel();
        options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));

        JPanel buttons = new JPanel();

        console = new JCheckBox("Enable Development Console");
        console.setToolTipText("Enable the development console to see the logs in real time, as well as run developer commands.");
        console.setSelected(Bot.getInstance().getConfiguration().getConsole());

        autoConnect = new JCheckBox("Auto-connect to \'" + Bot.getInstance().getConfiguration().getChannel() + "\'");
        autoConnect.setToolTipText("Allow the bot to automatically connect to \'" + Bot.getInstance().getConfiguration().getChannel() + "\' when it starts up.");
        autoConnect.setSelected(Bot.getInstance().getConfiguration().getAutoConnect());

        JButton back = new JButton("Back");
        back.addActionListener(new BackListener());

        JButton apply = new JButton("Apply");
        apply.addActionListener(new ApplyListener());

        options.add(console);
        if (!Bot.getInstance().getConfiguration().getChannel().equals("")) {
            options.add(autoConnect);
        }

        buttons.add(back);
        buttons.add(apply);

        add(options);
        add(buttons);
    }

    public JCheckBox getConsole() {
        return console;
    }

    public JCheckBox getAutoConnect() {
        return autoConnect;
    }

    public boolean discrepancies() {
        if (console.isSelected() != Bot.getInstance().getConfiguration().getConsole()) {
            return true;
        }

        if (autoConnect.isSelected() != Bot.getInstance().getConfiguration().getAutoConnect()) {
            return true;
        }

        return false;
    }

    public static OptionsPanel getInstance() {
        return instance;
    }
}