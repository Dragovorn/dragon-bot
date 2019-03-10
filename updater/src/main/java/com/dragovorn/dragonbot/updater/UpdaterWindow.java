package com.dragovorn.dragonbot.updater;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;
import java.awt.Dimension;

public class UpdaterWindow {

    private JProgressBar progressBar;

    public UpdaterWindow(String updated) {
        JPanel panel = new JPanel();

        Dimension size = new Dimension(250, 30);

        panel.setSize(size);
        panel.setPreferredSize(size);
        panel.setMinimumSize(size);
        panel.setMaximumSize(size);

        this.progressBar = new JProgressBar(0, 100);
        this.progressBar.setString("Updating " + updated + "...");
        this.progressBar.setStringPainted(true);

        panel.add(this.progressBar);

        JFrame frame = new JFrame("Dragon Bot Updater");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JProgressBar getProgressBar() {
        return this.progressBar;
    }
}
