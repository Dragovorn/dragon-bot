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
import com.dragovorn.dragonbot.gui.listener.ApplyListener;
import com.dragovorn.dragonbot.gui.listener.BackListener;

import javax.swing.*;
import java.awt.*;

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

        this.console = new JCheckBox("Enable Development Console");
        this.console.setToolTipText("Enable the development console to see the logs in real time, as well as run developer commands.");
        this.console.setSelected(Bot.getInstance().getConfiguration().getConsole());

        this.autoConnect = new JCheckBox("Auto-connect to \'" + Bot.getInstance().getConfiguration().getChannel() + "\'");
        this.autoConnect.setToolTipText("Allow the bot to automatically connect to \'" + Bot.getInstance().getConfiguration().getChannel() + "\' when it starts up.");
        this.autoConnect.setSelected(Bot.getInstance().getConfiguration().getAutoConnect());

        JButton back = new JButton("Back");
        back.addActionListener(new BackListener());

        JButton apply = new JButton("Apply");
        apply.addActionListener(new ApplyListener());

        options.add(this.console);

        if (!Bot.getInstance().getConfiguration().getChannel().equals("")) {
            options.add(this.autoConnect);
        }

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

        return false;
    }

    public static OptionsPanel getInstance() {
        return instance;
    }
}