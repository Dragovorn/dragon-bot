package com.dragovorn.dragonbot.gui.listener;

import com.dragovorn.dragonbot.gui.MainWindow;
import com.dragovorn.dragonbot.gui.panel.OptionsPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 4:43 PM.
 * as of 8/6/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
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

        MainWindow.getInstance().getFrame().setContentPane(MainWindow.getInstance().getPanel());
        MainWindow.getInstance().getFrame().setTitle(MainWindow.TITLE);
        MainWindow.getInstance().getFrame().pack();
    }
}