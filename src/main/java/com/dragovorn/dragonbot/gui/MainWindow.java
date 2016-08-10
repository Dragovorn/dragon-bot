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

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 6:02 PM.
 * as of 8/5/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class MainWindow {

    public static final String TITLE = "Dragon Bot v" + Bot.getInstance().getVersion();

    private JFrame frame;

    private JTextField channel;

    private JButton channelButton;
    private JButton options;

    private TextPrompt channelPrompt;

    private JPanel panel;

    private static MainWindow instance;

    public MainWindow() {
        instance = this;

        Dimension size = new Dimension(500, 150);

        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setSize(size);
        panel.setPreferredSize(size);
        panel.setMaximumSize(size);
        panel.setMinimumSize(size);

        channel = new JTextField(9);

        DefaultStyledDocument document = new DefaultStyledDocument();
        document.setDocumentFilter(new DocumentSizeFilter(25));

        channel.setDocument(document);

        channelPrompt = new TextPrompt((Bot.getInstance().getConfiguration().getChannel().equals("") ? "Channel Name" : Bot.getInstance().getConfiguration().getChannel()), channel);

        if (Bot.getInstance().getConfiguration().getAutoConnect()) {
            channelButton = new JButton("Leave Channel");
            channelButton.addActionListener(new LeaveListener());
            channel.setEditable(false);
        } else {
            channelButton = new JButton("Join Channel");
            channel.addActionListener(new JoinListener());
            channelButton.addActionListener(new JoinListener());
        }

        options = new JButton("Options");
        options.addActionListener(new OptionsListener());

        panel.add(channel);
        panel.add(channelButton);
        panel.add(options);

        frame = new JFrame(TITLE);
        frame.setResizable(false);
        frame.setContentPane(panel);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent event) {
                Bot.getInstance().getLogger().info("Shutting down!");

                Bot.getInstance().stop();
            }
        });
        frame.setVisible(true);
    }

    public JTextField getChannel() {
        return channel;
    }

    public TextPrompt getChannelPrompt() {
        return channelPrompt;
    }

    public JButton getChannelButton() {
        return channelButton;
    }

    public JButton getOptions() {
        return options;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void remove(Component component) {
        this.panel.remove(component);
        this.frame.pack();

    }

    public void add(Component component) {
        this.panel.add(component);
        this.frame.pack();
    }

    public static MainWindow getInstance() {
        return instance;
    }
}