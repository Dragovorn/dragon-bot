package com.dragovorn.dragonbot.gui;

import com.dragovorn.dragonbot.gui.filter.DocumentSizeFilter;
import com.dragovorn.dragonbot.gui.listener.SendListener;

import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import java.awt.*;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 12:05 PM.
 * as of 8/6/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class ConsoleWindow {

    private JPanel panel;

    private JFrame jFrame;

    private JTextArea console;

    private JTextField command;

    private JButton button;

    private static ConsoleWindow instance;

    public ConsoleWindow() {
        instance = this;

        Dimension size = new Dimension(800, 300);

        panel = new JPanel();

        panel.setLayout(new FlowLayout());
        panel.setPreferredSize(size);
        panel.setMinimumSize(size);
        panel.setMaximumSize(size);
        panel.setSize(size);

        console = new JTextArea(16, 65);
        console.setEditable(false);
        console.setLineWrap(true);

        command = new JTextField(58);
        command.addActionListener(new SendListener());

        DefaultStyledDocument document = new DefaultStyledDocument();
        document.setDocumentFilter(new DocumentSizeFilter(102));

        command.setDocument(document);

        button = new JButton("Send");
        button.setSize(5, 5);
        button.addActionListener(new SendListener());

        panel.add(new JScrollPane(console));
        panel.add(command);
        panel.add(button);

        jFrame = new JFrame("Console");

        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        jFrame.add(panel);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    public JTextArea getConsole() {
        return this.console;
    }

    public JTextField getCommand() {
        return this.command;
    }

    public static ConsoleWindow getInstance() {
        return instance;
    }
}