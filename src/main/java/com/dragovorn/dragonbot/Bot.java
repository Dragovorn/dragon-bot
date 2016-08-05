package com.dragovorn.dragonbot;

import java.io.File;
import java.util.logging.Logger;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 1:01 PM.
 * as of 8/5/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */

public abstract class Bot {

    private static Bot instance;

    /**
     * Sets the bot instance. This method may only be called once per an
     * application
     *
     * @param instance the new instance set
     */
    public static void setInstance(Bot instance) {
        if (instance == null) {
            return; // Log a severe when I make loggers
        } else if (Bot.instance != null) {
            return; // Lob a severe when I make loggers
        }

        Bot.instance = instance;
    }

    /**
     * Gets the currently running bot instance.
     *
     * @return the currently running bot instance
     */
    public static Bot getInstance() {
        return Bot.instance;
    }

    /**
     * Gracefully start the irc bot.
     */
    public abstract void start() throws Exception;

    /**
     * Gracefully stop the irc bot.
     */
    public abstract void stop();

    /**
     * Gets the name of the currently running irc bot.
     *
     * @return the name of the instance
     */
    public abstract String getName();

    /**
     * Gets the version of the currently running irc bot.
     *
     * @return the version of the instance
     */
    public abstract String getVersion();

    /**
     * Gets the author of the currently running irc bot.
     *
     * @return the author of the instance
     */
    public abstract String getAuthor();

    /**
     * Gets the main logger which can be used as a suitable replacement for
     * {@link System#out} and {@link System#err}.
     *
     * @return the {@link Logger} instance
     */
    public abstract Logger getLogger();

    // get plugin manager

    /**
     * Return the folder used to load plugins from.
     *
     * @return the folder used to load plugins
     */
    public abstract File getPluginsFolder();

    // get config

    /**
     * Return the connected channel.
     *
     * @return the channel the irc bot it connected to
     */
    public abstract String getChannel();

    /**
     * Connects to the specified irc channel.
     *
     * @param channel the channel to be connected to
     */
    public abstract void connectTo(String channel);

    /**
     * Send the specified message to the connected channel.
     *
     * @param message the message to be sent
     */
    public abstract void sendMessage(String message);

    /**
     * Send the specified message to the connected channel, formatted with objects.
     *
     * @param message the message to be sent
     * @param objects the objects for the message to be formatted with
     */
    public abstract void sendMessage(String message, Object... objects);
}