package com.dragovorn.dragonbot.updater;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.swing.*;
import java.awt.*;
import java.io.*;
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
        } else if (args.length > 2) {
            return;
        }

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException exception) { /* If this happens you happen to have a messed up system */ }

        new Updater();

        String path = args[0];

        file = new File(path);

        file.delete();
        try {
            file.createNewFile();

            HttpClient client = HttpClientBuilder.create().build();

            HttpGet request = new HttpGet(args[1]);
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                long size = entity.getContentLength();

                BufferedInputStream bufferedInputStream = new BufferedInputStream(entity.getContent());
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));

                int inByte;
                int sum = 0;

                while ((inByte = bufferedInputStream.read()) != -1) {
                    bufferedOutputStream.write(inByte);

                    sum += inByte;

                    progressBar.setValue(sum / (int) size * 100);
                }

                bufferedInputStream.close();
                bufferedOutputStream.close();

                complete();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
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