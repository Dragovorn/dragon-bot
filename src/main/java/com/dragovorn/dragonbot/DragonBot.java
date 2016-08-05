package com.dragovorn.dragonbot;

import com.dragovorn.dragonbot.log.DragonLogger;
import com.dragovorn.dragonbot.log.LoggingOutputStream;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 3:27 PM.
 * as of 8/5/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class DragonBot extends Bot {

    public volatile boolean isRunning;

    private final Logger logger;

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public DragonBot() throws IOException {
        logger = new DragonLogger("Dragon Bot", format.format(new Date()) + "-%g.log");
        System.setErr(new PrintStream(new LoggingOutputStream(logger, Level.SEVERE), true));
        System.setOut(new PrintStream(new LoggingOutputStream(logger, Level.INFO), true));
    }

    public static DragonBot getInstance() {
        return (DragonBot) Bot.getInstance();
    }

    @Override
    public void start() throws Exception {
        getLogger().info("Initializing Dragon Bot v" + getVersion() + "!");

        DragonBot.this.isRunning = true;

        getLogger().info("Dragon Bot v" + getVersion() + " initialized!");
    }

    @Override
    public void stop() {
        new Thread("Shutdown Thread") {
            @Override
            public void run() {
                DragonBot.this.isRunning = false;

                getLogger().info("Thank you and goodbye!");

                for (Handler handler : getLogger().getHandlers()) {
                    handler.close();
                }

                System.exit(0);
            }
        }.start();
    }

    @Override
    public String getName() {
        return "Dragon Bot";
    }

    @Override
    public String getVersion() {
        return Version.PRETTY_VERSION;
    }

    @Override
    public String getAuthor() {
        return "Dragovorn";
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

    @Override
    public File getPluginsFolder() {
        return null;
    }

    @Override
    public String getChannel() {
        return null;
    }

    @Override
    public void connectTo(String channel) {

    }

    @Override
    public void sendMessage(String message) {

    }

    @Override
    public void sendMessage(String message, Object... objects) {

    }
}