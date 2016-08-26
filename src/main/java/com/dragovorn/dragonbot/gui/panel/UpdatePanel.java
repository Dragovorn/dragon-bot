package com.dragovorn.dragonbot.gui.panel;

import com.amazonaws.services.s3.AmazonS3;
import com.dragovorn.dragonbot.Core;
import com.dragovorn.dragonbot.FileLocations;
import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.bot.DragonBot;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
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
        try {
            Map<String, String> releases = DragonBot.getInstance().getGitHubAPI().getReleases();

            for (Map.Entry<String, String> entry : releases.entrySet()) {
                double newVersion = Double.valueOf(entry.getKey().substring(0, 4));
                double botVersion = Double.valueOf(Bot.getInstance().getVersion().substring(1, 5));

                char newPatch = entry.getKey().charAt(4);
                char botPatch = Bot.getInstance().getVersion().charAt(5);

                int newSnapshot = 0;
                int oldSnapshot = 0;

                if (Bot.getInstance().getConfiguration().getPreReleases()) {
                    if (entry.getKey().contains("SNAPSHOT")) {
                        newSnapshot = Integer.valueOf(entry.getKey().split("-")[1]);
                    }

                    if (Bot.getInstance().getVersion().contains("SNAPSHOT")) {
                        oldSnapshot = Integer.valueOf(Bot.getInstance().getVersion().split("-")[1]);
                    }
                }

                if (newVersion > botVersion) {
                    launchUpdater("v" + entry.getValue());
                    return true;
                } else if (newVersion == botVersion) {
                    if (newPatch > botPatch) {
                        launchUpdater("v" + entry.getValue());
                        return true;
                    } else if (newSnapshot == 0 || (oldSnapshot != 0 && newSnapshot > oldSnapshot)) {
                        launchUpdater("v" + entry.getValue());
                        return true;
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return false;
    }

    private void launchUpdater(String url) throws Exception {
        final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";

        if (!FileLocations.updater.getName().endsWith(".jar")) {
            System.exit(0);
            return;
        }

        final ArrayList<String> command = new ArrayList<>();
        command.add(javaBin);
        command.add("-jar");
        command.add(FileLocations.updater.getPath());
        command.add(Core.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        command.add(url);

        final ProcessBuilder builder = new ProcessBuilder(command);
        builder.start();
    }
}