package com.dragovorn.dragonbot.updater;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    private String[] args;

    public Main(String[] args) {
        this.args = args;
    }

    public void start() {
        boolean launchJar = this.args.length >= 4 ? Boolean.valueOf(args[3]) : true;

        UpdaterWindow window = new UpdaterWindow(this.args.length >= 3 ? args[2] : "Dragon Bot");

        File file = new File(this.args[0]);

        Downloader downloader = new Downloader(this.args[1]);
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

    public static void main(String[] args) {
        if (args.length < 2 || args.length > 4) {
            throw new IllegalArgumentException("Incorrect arguments provided! Check documentation for correct usage!");
        }

        new Main(args).start();
    }
}
