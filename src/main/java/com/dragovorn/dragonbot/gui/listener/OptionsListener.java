package com.dragovorn.dragonbot.gui.listener;

import com.dragovorn.dragonbot.gui.MainWindow;
import com.dragovorn.dragonbot.gui.panel.OptionsPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 4:40 PM.
 * as of 8/6/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class OptionsListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent event) {
        MainWindow.getInstance().setContentPane(new OptionsPanel());
        MainWindow.getInstance().setTitle(MainWindow.TITLE + " - Options");
        MainWindow.getInstance().pack();
    }
}