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
import com.dragovorn.dragonbot.bot.DragonBot;
import com.dragovorn.dragonbot.gui.MainWindow;
import net.dgardiner.markdown.MarkdownProcessor;
import net.dgardiner.markdown.flavours.github.GithubFlavour;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UpdatePanel extends JPanel {

    private volatile boolean stop = false;
    private boolean hasResponded = false;

    public UpdatePanel() {
        JLabel label = new JLabel("Checking for updates...");

        Dimension size = new Dimension(250, 30);
        add(label);
        setSize(size);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
    }

    public void update() {
        try {
            Map<String, JSONObject> releases = DragonBot.getInstance().getGitHubAPI().getReleases();

            List<JSONObject> release = new ArrayList<>();

            for (Map.Entry<String, JSONObject> entry : releases.entrySet()) {
                String version = (release.size() == 0 ? Bot.getInstance().getVersion() : release.get(release.size() - 1).getString("tag_name"));

                double newVersion = Double.valueOf(entry.getKey().substring(0, 4));
                double botVersion = Double.valueOf(version.substring(1, 5));

                char newPatch = entry.getKey().charAt(4);
                char botPatch = version.charAt(5);

                int newSnapshot = 0;
                int oldSnapshot = 0;

                if (Bot.getInstance().getConfiguration().getPreReleases()) {
                    if (entry.getKey().contains("SNAPSHOT")) {
                        newSnapshot = Integer.valueOf(entry.getKey().split("-")[1]);
                    }

                    if (version.contains("SNAPSHOT")) {
                        oldSnapshot = Integer.valueOf(version.split("-")[1]);
                    }
                }

                if (newVersion > botVersion) {
                    Bot.getInstance().getLogger().info("Detected newer version (v" + entry.getKey() + ")");
                    release.add(entry.getValue());
                } else if (newVersion == botVersion) {
                    if (newPatch > botPatch) {
                        Bot.getInstance().getLogger().info("Detected newer version (v" + entry.getKey() + ")");
                        release.add(entry.getValue());
                    } else if ((newSnapshot == 0 && oldSnapshot != 0) || (oldSnapshot != 0 && newSnapshot > oldSnapshot)) {
                        Bot.getInstance().getLogger().info("Detected newer version (v" + entry.getKey() + ")");
                        release.add(entry.getValue());
                    }
                }
            }

            if (release.size() > 0) {
                askForUpdate(release);
            } else {
                this.hasResponded = true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public boolean shouldStop() {
        while (!this.hasResponded) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }

        return this.stop;
    }

    private void askForUpdate(final List<JSONObject> releases) throws IOException {
        Dimension areaSize = new Dimension(480, 160);
        Dimension size = new Dimension(500, 200);

        setSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
        setPreferredSize(size);
        setLayout(new FlowLayout());

        removeAll();

        JTextPane area = new JTextPane();
        area.setSize(areaSize);
        area.setMaximumSize(areaSize);
        area.setMinimumSize(areaSize);
        area.setPreferredSize(areaSize);
        area.setContentType("text/html");
        area.setEditable(false);
        area.setBorder(null);
        area.setBackground(UIManager.getColor("InternalFrame.background"));

        StringBuilder builder = new StringBuilder();

        MarkdownProcessor processor = new MarkdownProcessor();
        processor.setFlavour(new GithubFlavour());

        for (int x = releases.size() - 1; x >= 0; x--) {
            builder.append("<font size=\"7\"><b>").append(releases.get(x).getString("tag_name")).append(" - ").append(releases.get(x).getString("name")).append("</b></font>");

            if (x == releases.size() - 1) {
                builder.append("<br>You are <b>").append(DragonBot.getInstance().getGitHubAPI().getNumCommitsBetween(Bot.getInstance().getVersion(), releases.get(x).getString("tag_name"))).append(" commits</b> behind!<br>");
            }

            builder.append(processor.process(releases.get(x).getString("body")));

            if (x != 0) {
                builder.append("<br><br>");
            }

        }

        area.setText(builder.toString());

        JScrollPane scroll = new JScrollPane(area);
        scroll.setSize(areaSize);
        scroll.setMaximumSize(areaSize);
        scroll.setMinimumSize(areaSize);
        scroll.setPreferredSize(areaSize);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setViewportBorder(null);
        scroll.setBorder(null);

        JLabel recommended = new JLabel("<html><b>Updating is always recommended!</b></html>");
        recommended.setForeground(Color.RED);

        JButton update = new JButton("Update!");
        JButton no = new JButton("Not now");

        update.addActionListener((ActionListener) -> {
            try {
                launchUpdater(releases.get(releases.size() - 1).getJSONArray("assets").getJSONObject(0).getString("browser_download_url"));
                this.stop = true;
                this.hasResponded = true;

                Bot.getInstance().getLogger().info("Updating now...");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        no.addActionListener((ActionListener) -> {
            this.stop = false;
            this.hasResponded = true;

            Bot.getInstance().getLogger().info("Not now chosen.");
        });

        add(scroll);
        add(recommended);
        add(no);
        add(update);

        MainWindow.getInstance().pack();
        MainWindow.getInstance().center();
    }

    private void launchUpdater(String url) throws Exception {
        String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";

        if (!FileLocations.updater.getName().endsWith(".jar")) {
            System.exit(0);
            return;
        }

        ArrayList<String> command = new ArrayList<>();
        command.add(javaBin);
        command.add("-jar");
        command.add(FileLocations.updater.getPath());
        command.add(DragonBotMain.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        command.add(url);

        ProcessBuilder builder = new ProcessBuilder(command);
        builder.start();
    }
}