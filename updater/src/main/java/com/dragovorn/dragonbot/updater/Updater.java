package com.dragovorn.dragonbot.updater;

import com.amazonaws.AmazonClientException;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.TransferManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 12:10 AM.
 * as of 8/15/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class Updater {

    private static JProgressBar progressBar;

    private static File file;

    private static Download download;

    private Updater() {
        JPanel panel = new JPanel();

        Dimension size = new Dimension(250, 30);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        panel.setSize(size);
        panel.setPreferredSize(size);
        panel.setMinimumSize(size);
        panel.setMaximumSize(size);

        progressBar = new JProgressBar(0, 100);
        progressBar.setString("Updating...");
        progressBar.setStringPainted(true);

        panel.add(progressBar);

        JFrame frame = new JFrame("Dragon Bot Updater");
        frame.setResizable(false);
        frame.setLocation(screen.width / 2 - panel.getSize().width / 2, screen.height / 2 - panel.getSize().height / 2);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            return;
        } else if (args.length > 1) {
            return;
        }

        new Updater();

        String path = args[0];

        file = new File(path);

        AmazonS3 client = new AmazonS3Client();
        TransferManager manager = new TransferManager(client);

        file.delete();

        ProgressListener listener = (ProgressEvent event) -> {
            if (download == null) {
                return;
            }

            progressBar.setValue((int) download.getProgress().getPercentTransferred());

            switch (event.getEventType()) {
                case TRANSFER_COMPLETED_EVENT: {
                    progressBar.setValue(100);
                    complete();
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

        GetObjectRequest request = new GetObjectRequest("dl.dragovorn.com", "DragonBot/DragonBot.jar");
        request.setGeneralProgressListener(listener);

        download = manager.download(request, file);
    }

    private static void complete() {
        try {
            final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";

            if (!file.getName().endsWith(".jar")) {
                System.exit(0);
                return;
            }

            final ArrayList<String> command = new ArrayList<>();
            command.add(javaBin);
            command.add("-jar");
            command.add(file.getPath());

            final ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();

            System.exit(0);
        } catch (IOException exception) { /* Shouldn't happen */ }
    }
}