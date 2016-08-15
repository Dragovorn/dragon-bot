package com.dragovorn.dragonbot.gui.panel;

import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.dragovorn.dragonbot.Core;
import com.dragovorn.dragonbot.FileLocations;
import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.gui.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 3:52 PM.
 * as of 8/13/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class UpdatePanel extends JPanel {

    private TransferManager manager;

    public UpdatePanel(TransferManager manager) {
        this.manager = manager;

        JLabel label = new JLabel("Checking for updates...");

        Dimension size = new Dimension(250, 30);
        add(label);
        setSize(size);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
    }

    public boolean update() {
        S3Object object = manager.getAmazonS3Client().getObject(new GetObjectRequest("dl.dragovorn.com", "DragonBot/DragonBot.jar"));

        try {
            if (object.getObjectMetadata().getLastModified().getTime() > new File(Core.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).lastModified()) {
                Bot.getInstance().getLogger().info("Found an update! Starting the updater...");

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

                Bot.getInstance().getLogger().info(FileLocations.updater.getPath());
                Bot.getInstance().getLogger().info(new File(Core.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getPath());

                final ProcessBuilder builder = new ProcessBuilder(command);
                builder.start();

                Bot.getInstance().stop();

                return true;
            } else {
                Bot.getInstance().getLogger().info("No update found.");
                MainWindow.getInstance().setContentPane(MainWindow.getInstance().getPanel());
                MainWindow.getInstance().pack();
                MainWindow.getInstance().center();
            }
        } catch (URISyntaxException | IOException exception) {
            exception.printStackTrace();
        }

        return false;
    }
}