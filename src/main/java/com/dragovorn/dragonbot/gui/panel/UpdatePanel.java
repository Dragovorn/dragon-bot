package com.dragovorn.dragonbot.gui.panel;

import com.amazonaws.services.s3.AmazonS3;
import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.bot.DragonBot;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Map;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 3:52 PM.
 * as of 8/13/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class UpdatePanel extends JPanel {

    private AmazonS3 client;

    public UpdatePanel(AmazonS3 client) {
        this.client = client;

        JLabel label = new JLabel("Checking for updates...");

        Dimension size = new Dimension(250, 30);
        add(label);
        setSize(size);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
    }

    public boolean update() {
        /*
        final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";

                if (!FileLocations.updater.getName().endsWith(".jar")) {
                    System.exit(0);
                    return false;
                }

                final ArrayList<String> command = new ArrayList<>();
                command.add(javaBin);
                command.add("-jar");
                command.add(FileLocations.updater.getPath());
                command.add(new File(Core.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getPath());

                final ProcessBuilder builder = new ProcessBuilder(command);
                builder.start();
         */

        try {
            Map<String, String> releases = DragonBot.getInstance().getGitHubAPI().getReleases();

            for (Map.Entry<String, String> entry : releases.entrySet()) {
                if (!entry.getKey().equals(Bot.getInstance().getVersion().substring(1))) {
                    double newVersion = Double.valueOf(entry.getKey().substring(1, 4));
                    double botVersion = Double.valueOf(Bot.getInstance().getVersion().substring(1, 4));
                }
            }
        } catch (IOException exception) { /* This won't happen */}


        return false;
    }
}