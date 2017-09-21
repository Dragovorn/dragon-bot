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

import com.dragovorn.dragonbot.Main;
import com.dragovorn.dragonbot.api.bot.file.FileManager;
import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.DragonBot;
import com.dragovorn.dragonbot.Version;
import com.dragovorn.dragonbot.gui.MainWindow;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
        JSONObject release;
        try {
            release = DragonBot.getInstance().getGitHubAPI().getLatestRelease();

            boolean shouldUpdate = Version.shouldUpdate(DragonBot.getInstance().getVersion(), release.getString("tag_name"));

            if (shouldUpdate) {
                askForUpdate(release);
            }

            this.stop = false;
            this.hasResponded = true;
        } catch (IOException e) {
            DragonBot.getInstance().getLogger().severe("Unable to retrieve version information!");
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

    private void askForUpdate(final JSONObject release) throws IOException {
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

        // TODO move over to flexmark-java markdown
        Parser parser = Parser.builder().build();

        builder.append("<font size=\"7\"><b>").append(release.getString("tag_name")).append(" - ").append(release.getString("name")).append("</b></font>");

        int commits = DragonBot.getInstance().getGitHubAPI().getNumCommitsBetween(Bot.getInstance().getVersion(), release.getString("tag_name"));

        if (commits != 0) {
            builder.append("<br>You are <b>").append(commits).append(" commits</b> behind!<br>");
        }

        builder.append(HtmlRenderer.builder().build().render(parser.parse(release.getString("body"))));

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
                launchUpdater(release.getJSONArray("assets").getJSONObject(0).getString("browser_download_url"));

                this.stop = true;
                this.hasResponded = true;

                Bot.getInstance().getLogger().info("Updating now...");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        no.addActionListener((ActionListener) -> {
            String buttons[] = { "No", "Yes, Update to a Pre-Release" };

            int proceed = JOptionPane.showOptionDialog(null, "Are you sure you don't want to update?", "Update", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, buttons, buttons[0]);

            if (proceed == 1) {
                Bot.getInstance().getLogger().info("Not now chosen.");

                this.stop = false;
                this.hasResponded = true;
            } else {
                try {
                    launchUpdater(release.getJSONArray("assets").getJSONObject(0).getString("browser_download_url"));

                    this.stop = true;
                    this.hasResponded = true;

                    Bot.getInstance().getLogger().info("Updating now...");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
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

        if (!FileManager.getUpdater().getName().endsWith(".jar")) {
            System.exit(0);
            return;
        }

        ArrayList<String> command = new ArrayList<>();
        command.add(javaBin);
        command.add("-jar");
        command.add(FileManager.getUpdater().getPath());
        command.add(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        command.add(url);

        ProcessBuilder builder = new ProcessBuilder(command);
        builder.start();
    }
}