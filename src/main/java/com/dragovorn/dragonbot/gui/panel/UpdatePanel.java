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
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.dragovorn.dragonbot.gui.panel;

import com.dragovorn.dragonbot.DragonBotMain;
import com.dragovorn.dragonbot.FileLocations;
import com.dragovorn.dragonbot.bot.Bot;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class UpdatePanel extends JPanel {

    public UpdatePanel() {
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
            Map<String, String> releases = com.dragovorn.dragonbot.bot.DragonBot.getInstance().getGitHubAPI().getReleases();

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
                    Bot.getInstance().getLogger().info("Detected newer version (v" + entry.getKey() + ")");
                    launchUpdater(entry.getValue());
                    return true;
                } else if (newVersion == botVersion) {
                    if (newPatch > botPatch) {
                        Bot.getInstance().getLogger().info("Detected newer version (v" + entry.getKey() + ")");
                        launchUpdater(entry.getValue());
                        return true;
                    } else if (newSnapshot == 0 && (oldSnapshot != 0 && newSnapshot > oldSnapshot)) {
                        Bot.getInstance().getLogger().info("Detected newer version (v" + entry.getKey() + ")");
                        launchUpdater(entry.getValue());
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
        removeAll();




        final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";

        if (!FileLocations.updater.getName().endsWith(".jar")) {
            System.exit(0);
            return;
        }

        final ArrayList<String> command = new ArrayList<>();
        command.add(javaBin);
        command.add("-jar");
        command.add(FileLocations.updater.getPath());
        command.add(DragonBotMain.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        command.add(url);

        final ProcessBuilder builder = new ProcessBuilder(command);
        builder.start();
    }
}