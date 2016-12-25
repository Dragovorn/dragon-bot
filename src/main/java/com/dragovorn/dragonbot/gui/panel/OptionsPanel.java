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

package com.dragovorn.dragonbot.gui.panel;

import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.bot.DragonBot;
import com.dragovorn.dragonbot.gui.MainWindow;
import com.dragovorn.dragonbot.gui.TextPrompt;
import com.dragovorn.dragonbot.gui.listener.ApplyListener;
import com.dragovorn.dragonbot.gui.listener.BackListener;

import javax.swing.*;
import java.awt.*;

// TODO Add options for file management and other config options
public class OptionsPanel extends JPanel {

    private JCheckBox console;
    private JCheckBox autoConnect;

    private JTextField username;

    private final JLabel testStatus;

    private JPasswordField oauth;

    private static OptionsPanel instance;

    private boolean tested;

    public OptionsPanel() {
        instance = this;
        this.tested = false;
        this.testStatus = new JLabel();

        Dimension size = new Dimension(500, 500);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
        setSize(size);

        JPanel options = new JPanel();
        options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));

        JPanel buttons = new JPanel();

        this.console = new JCheckBox("Enable Development Console");
        this.console.setToolTipText("Enable the development console to see the logs in real time, as well as run developer commands.");
        this.console.setSelected(Bot.getInstance().getConfiguration().getConsole());

        this.autoConnect = new JCheckBox("Auto-connect to \'" + Bot.getInstance().getConfiguration().getChannel() + "\'");
        this.autoConnect.setToolTipText("Allow the bot to automatically connect to \'" + Bot.getInstance().getConfiguration().getChannel() + "\' when it starts up.");
        this.autoConnect.setSelected(Bot.getInstance().getConfiguration().getAutoConnect());

        this.username = new JTextField(10);
        this.username.setToolTipText("The username for the bot");
        this.username.setMaximumSize(this.username.getPreferredSize());
        this.username.setText(Bot.getInstance().getConfiguration().getName());
        new TextPrompt("Username", this.username);

        this.oauth = new JPasswordField(15);
        this.oauth.setToolTipText("The OAuth key for the twitch account you are binding to the bot");
        this.oauth.setMaximumSize(this.oauth.getPreferredSize());
        this.oauth.setText(Bot.getInstance().getConfiguration().getAuth());
        new TextPrompt("OAuth", this.oauth);

        JButton testTwitch = new JButton("Test");

        JPanel twitchSettings = new JPanel();
        twitchSettings.setLayout(new BoxLayout(twitchSettings, BoxLayout.X_AXIS));

        JButton unlockTwitch = new JButton("Unlock Twitch Account Settings");
        unlockTwitch.addActionListener(event -> {
            options.remove(unlockTwitch);
            options.add(twitchSettings);

            MainWindow.getInstance().pack();
        });

        JButton lockTwitch = new JButton("Lock");
        lockTwitch.addActionListener(event -> {
            options.remove(twitchSettings);
            options.add(unlockTwitch);
            testStatus.setText("");

            MainWindow.getInstance().pack();
        });

        testTwitch.addActionListener(event -> {
            testStatus.setText("Testing...");
            testStatus.setForeground(Color.yellow);
            lockTwitch.setEnabled(false);
            testTwitch.setEnabled(false);
            MainWindow.getInstance().pack();

            if (this.username.getText().equals("")) {
                Bot.getInstance().getLogger().info("You require a username to connect to twitch!");

                testStatus.setText("No Username!");
                testStatus.setForeground(Color.red);
                lockTwitch.setEnabled(true);
                testTwitch.setEnabled(true);

                return;
            }

            if (this.oauth.getPassword().length == 0) {
                Bot.getInstance().getLogger().info("You require an oauth key to connect to twitch!");

                testStatus.setText("No Auth Key!");
                testStatus.setForeground(Color.red);
                lockTwitch.setEnabled(true);
                testTwitch.setEnabled(true);

                return;
            }

            if (!(this.tested = DragonBot.getInstance().testConnection(this.username.getText(), String.valueOf(this.oauth.getPassword())))) {
                Bot.getInstance().getLogger().info("Failed to connect to twitch!");

                testStatus.setText("Failed to connect!");
                testStatus.setForeground(Color.red);
                lockTwitch.setEnabled(true);
                testTwitch.setEnabled(true);

                return;
            }

            testStatus.setText("Success!");
            testStatus.setForeground(Color.green);
            lockTwitch.setEnabled(true);
            testTwitch.setEnabled(true);
        });

        JButton back = new JButton("Back");
        back.addActionListener(new BackListener());

        JButton apply = new JButton("Apply");
        apply.addActionListener(new ApplyListener());

        options.add(this.console);

        if (!Bot.getInstance().getConfiguration().getChannel().equals("")) {
            options.add(this.autoConnect);
        }

        twitchSettings.add(this.username);
        twitchSettings.add(this.oauth);
        twitchSettings.add(testTwitch);
        twitchSettings.add(lockTwitch);
        twitchSettings.add(testStatus);

        options.add(unlockTwitch);

        buttons.add(back);
        buttons.add(apply);

        add(options);
        add(buttons);
    }

    public JCheckBox getConsole() {
        return this.console;
    }

    public JCheckBox getAutoConnect() {
        return this.autoConnect;
    }

    public boolean discrepancies() {
        if (this.console.isSelected() != Bot.getInstance().getConfiguration().getConsole()) {
            return true;
        }

        if (this.autoConnect.isSelected() != Bot.getInstance().getConfiguration().getAutoConnect()) {
            return true;
        }

        return hasAccountInfoChanged();
    }

    public boolean hasAccountInfoChanged() {
        return (!Bot.getInstance().getName().equals(this.username.getText()) || !Bot.getInstance().getPassword().equals(String.valueOf(this.oauth.getPassword())));
    }

    public boolean accountInfoTested() {
        return this.tested;
    }

    public void updateInfo() {
        Bot.getInstance().setName(this.username.getText());
        Bot.getInstance().setPassword(String.valueOf(this.oauth.getPassword()));
    }

    public JLabel getTestStatus() {
        return this.testStatus;
    }

    public static OptionsPanel getInstance() {
        return instance;
    }
}