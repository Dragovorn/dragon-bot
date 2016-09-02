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
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE..
 */

package com.dragovorn.dragonbot.gui;

import com.dragovorn.dragonbot.gui.filter.DocumentSizeFilter;
import com.dragovorn.dragonbot.gui.listener.SendListener;

import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import java.awt.*;

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

    public JPanel getPanel() {
        return this.panel;
    }

    public JFrame getFrame() {
        return this.jFrame;
    }

    public static ConsoleWindow getInstance() {
        return instance;
    }
}