package com.dragovorn.dragonbot.gui.panel;

import com.amazonaws.AmazonClientException;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.dragovorn.dragonbot.Core;
import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.gui.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URISyntaxException;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 3:52 PM.
 * as of 8/13/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class UpdatePanel extends JPanel {

    private JProgressBar progressBar;

    private Download download;

     private TransferManager manager;

    public UpdatePanel(TransferManager manager) {
        this.manager = manager;

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setString("Updating...");

        JLabel label = new JLabel("Checking for updates...");

        Dimension size = new Dimension(250, 30);
        add(label);
        setSize(size);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
    }

    public void update() {
        S3Object object = manager.getAmazonS3Client().getObject(new GetObjectRequest("dl.dragovorn.com", "DragonBot/DragonBot.jar"));

        try {
            if (object.getObjectMetadata().getLastModified().getTime() > new File(Core.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).lastModified()) {
                Bot.getInstance().getLogger().info("Found an update! Downloading now...");

                removeAll();
                add(progressBar);
                MainWindow.getInstance().pack();

                ProgressListener listener = (ProgressEvent event) -> {

                    if (download == null) {
                        return;
                    }

                    progressBar.setValue((int) download.getProgress().getPercentTransferred());
                    Bot.getInstance().getLogger().info("Downloaded " + download.getProgress().getBytesTransferred() + " of " + download.getProgress().getTotalBytesToTransfer() + " bytes!");

                    switch (event.getEventType()) {
                        case TRANSFER_COMPLETED_EVENT: {
                            progressBar.setValue(100);
                            Bot.getInstance().getLogger().info("Download complete! Restarting...");
                            Bot.getInstance().restart();
                            break;
                        } case TRANSFER_FAILED_EVENT: {
                            try {
                                AmazonClientException exception = download.waitForException();

                                exception.printStackTrace();
                            } catch(InterruptedException exception) { /* Won't happen */ }
                            break;
                        }
                    }
                };

                File file = new File(Core.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());

                Bot.getInstance().getLogger().info("Deleting old jar: " + file.getPath());
                Bot.getInstance().getLogger().info((file.delete() ? "Successfully" : "Unsuccessfully") + " deleted " + file.getPath());

                GetObjectRequest request = new GetObjectRequest("dl.dragovorn.com", "DragonBot/DragonBot.jar").withGeneralProgressListener(listener);

                download = manager.download(request, new File(Core.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()));
            } else {
                Bot.getInstance().getLogger().info("No update found.");
                MainWindow.getInstance().setContentPane(MainWindow.getInstance().getPanel());
                MainWindow.getInstance().pack();
                MainWindow.getInstance().center();
            }
        } catch (URISyntaxException exception) {
            exception.printStackTrace();
        }
    }
}