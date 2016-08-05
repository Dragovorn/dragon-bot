package com.dragovorn.dragonbot.gui;

import javax.swing.*;
import java.awt.*;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 5:03 PM.
 * as of 8/5/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class ConsoleWindow extends JPanel {

    private JTextArea console;

    private ConsoleWindow() {
        Dimension size = new Dimension(500, 500);

        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);

        console = new JTextArea(28, 40);
        console.setEditable(false);

        add(new JScrollPane(console));
    }

    public static ConsoleWindow makeWindow() {
        ConsoleWindow window = new ConsoleWindow();

        JFrame frame = new JFrame("Console");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.add(window);
        frame.pack();
        frame.setVisible(true);

        return window;
    }

    public JTextArea getConsole() {
        return this.console;
    }
}