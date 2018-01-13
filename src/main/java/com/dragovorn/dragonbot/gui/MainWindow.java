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

package com.dragovorn.dragonbot.gui;

import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.gui.filter.DocumentSizeFilter;
import com.dragovorn.dragonbot.gui.listener.JoinListener;
import com.dragovorn.dragonbot.gui.listener.LeaveListener;
import com.dragovorn.dragonbot.gui.listener.OptionsListener;

import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow {

    public static String TITLE = "Dragon Bot (v%s)";

    private JFrame frame;

    private JTextField channel;

    private JButton channelButton;
    private JButton options;

    private TextPrompt channelPrompt;

    private JPanel panel;

    private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

    private static MainWindow instance;

    public MainWindow(Container container) {
        instance = this;

        init(container);
    }

    private JPanel makePanel() {
        if (panel != null) {
            return null;
        }

        Dimension size = new Dimension(500, 250);

        this.panel = new JPanel();
        this.panel.setLayout(new FlowLayout());
        this.panel.setSize(size);
        this.panel.setPreferredSize(size);
        this.panel.setMaximumSize(size);
        this.panel.setMinimumSize(size);

        this.channel = new JTextField(9);

        DefaultStyledDocument document = new DefaultStyledDocument();
        document.setDocumentFilter(new DocumentSizeFilter(25));

        this.channel.setDocument(document);

        this.channelPrompt = new TextPrompt((Bot.getInstance().getConfiguration().getChannel().equals("") ? "Channel Name" : Bot.getInstance().getConfiguration().getChannel()), this.channel);

        if (Bot.getInstance().getConfiguration().getAutoConnect()) {
            this.channelButton = new JButton("Leave Channel");
            this.channelButton.addActionListener(new LeaveListener());
            this.channel.setEditable(false);
        } else {
            this.channelButton = new JButton("Join Channel");
            this.channel.addActionListener(new JoinListener());
            this.channelButton.addActionListener(new JoinListener());
        }

        this.options = new JButton("Options");
        this.options.addActionListener(new OptionsListener());

        this.panel.add(this.channel);
        this.panel.add(this.channelButton);
        this.panel.add(this.options);

        return this.panel;
    }

    private void init(Container container) {
        if (container == null) {
            container = makePanel();
            this.panel = (JPanel) container;
        } else {
            this.panel = makePanel();
        }

        this.frame = new JFrame(TITLE);
        this.frame.setLocation(this.screen.width / 2 - container.getSize().width / 2, this.screen.height / 2 - container.getSize().height / 2);
        this.frame.setResizable(true);
        this.frame.setContentPane(container);
        this.frame.pack();
        this.frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent event) {
                Bot.getInstance().getLogger().info("Shutting down!");

                Bot.getInstance().stop();
            }
        });
        this.frame.setVisible(true);
    }

    public JTextField getChannel() {
        return this.channel;
    }

    public TextPrompt getChannelPrompt() {
        return this.channelPrompt;
    }

    public JButton getChannelButton() {
        return this.channelButton;
    }

    public JButton getOptions() {
        return this.options;
    }

    public JPanel getPanel() {
        return this.panel;
    }

    public void center() {
        Container container = this.frame.getContentPane();

        setLocation(this.screen.width / 2 - container.getSize().width / 2, this.screen.height / 2 - container.getSize().height / 2);
    }

    public void setLocation(int x, int y) {
        this.frame.setLocation(x, y);
    }

    public void setContentPane(Container container) {
        this.frame.setContentPane(container);
    }

    public void setTitle(String title) {
        this.frame.setTitle(title);
    }

    public void pack() {
        this.frame.pack();
    }

    public static MainWindow getInstance() {
        return instance;
    }
}