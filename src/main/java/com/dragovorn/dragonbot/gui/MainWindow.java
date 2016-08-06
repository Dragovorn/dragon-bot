package com.dragovorn.dragonbot.gui;

import com.dragovorn.dragonbot.bot.Bot;

import javax.swing.*;
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

    private JFrame frame;

    private JPanel panel;

    private static MainWindow instance;

    public MainWindow() {
        instance = this;

        Dimension size = new Dimension(500, 500);

        panel = new JPanel();
        panel.setSize(size);
        panel.setPreferredSize(size);
        panel.setMaximumSize(size);
        panel.setMinimumSize(size);
        frame = new JFrame("Dragon Bot v" + Bot.getInstance().getVersion());
        frame.add(panel);
        frame.pack();
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent event) {
                Bot.getInstance().getLogger().info("Shutting down!");

                Bot.getInstance().stop();
            }
        });
        frame.setVisible(true);
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