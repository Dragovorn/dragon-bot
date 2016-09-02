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

package com.dragovorn.dragonbot.gui.listener;

import com.dragovorn.dragonbot.gui.MainWindow;
import com.dragovorn.dragonbot.gui.panel.OptionsPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent event) {
        if (OptionsPanel.getInstance().discrepancies()) {
            String buttons[] = { "No", "Yes, Discard changes" };

            int PromptResults = JOptionPane.showOptionDialog(null, "Are you sure you want to go back and discard your changes?", "Configuration", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, buttons, buttons[0]);

            if (PromptResults == 0) {
                return;
            }
        }

        MainWindow.getInstance().setContentPane(MainWindow.getInstance().getPanel());
        MainWindow.getInstance().setTitle(MainWindow.TITLE);
        MainWindow.getInstance().pack();
    }
}