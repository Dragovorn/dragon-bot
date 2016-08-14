package com.dragovorn.dragonbot.gui.panel;

import com.amazonaws.services.s3.transfer.TransferManager;

import javax.swing.*;
import java.awt.*;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 3:52 PM.
 * as of 8/13/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class UpdatePanel extends JPanel {

    private JProgressBar progressBar;

    public UpdatePanel(TransferManager manager) {
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setString("Updating...");

        Dimension size = new Dimension(200, 30);
        add(progressBar);
        setSize(size);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
    }
}