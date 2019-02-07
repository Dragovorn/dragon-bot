package com.dragovorn.dragonbot.updater;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        if (args.length != 4) {
            return;
        }

        boolean launchJar = Boolean.valueOf(args[3]);

        UpdaterWindow window = new UpdaterWindow(args[2]);

        File file = new File(args[0]);

        Downloader downloader = new Downloader(args[1]);
        try {
            downloader.download(file, window.getProgressBar());

            if (launchJar) {
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
