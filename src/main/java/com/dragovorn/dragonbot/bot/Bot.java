package com.dragovorn.dragonbot.bot;

import com.dragovorn.dragonbot.api.bot.command.CommandManager;
import com.dragovorn.dragonbot.exceptions.ConnectionException;
import com.google.common.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.logging.Logger;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 1:01 PM.
 * as of 8/5/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */

public abstract class Bot {

    private BotState state;

    private EventBus eventBus = new EventBus();

    private static Bot instance;

    /**
     * Sets the bot instance. This method may only be called once per an
     * application
     *
     * @param instance the new instance set
     */
    protected static void setInstance(Bot instance) {
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
     * Sets the current bot state.
     *
     * @param state the new bot state
     */
    protected final void setState(BotState state) {
        this.state = state;
    }

    /**
     * Gets the current bot state.
     *
     * @return the bot's state
     */
    public final BotState getState() {
        return state;
    }

    /**
     * Gets the bot's event bus
     *
     * @return the EventBus of the bot
     */
    public final EventBus getEventBus() {
        return this.eventBus;
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
     * Gets the name of the irc bot.
     *
     * @return the name of irc bot
     */
    public abstract String getName();

    /**
     * Sets the name of the irc bot.
     *
     * @param name the new name of the bot
     */
    public abstract void setName(String name);

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

    /**
     * Return the configuration in use by the bot
     *
     * @return the configuration in use by the bot
     */
    public abstract BotConfiguration getConfiguration();

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
     * Leaves the current channel.
     */
    public abstract void leaveChannel();

    /**
     * Connects the irc bot to the specified ip and port.
     *
     * @param ip the IP address of the irc server
     * @param port the port of the irc server
     * @param password the password for the account the bot is using
     */
    public abstract void connect(String ip, int port, String password) throws ConnectionException, IOException;

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

    /**
     * Checks if the irc bot is connected to a server.
     *
     * @return if the irc bot is connected to a server
     */
    public abstract boolean isConnected();

    /**
     * Handles a single line of response from the irc server.
     *
     * @param rawLine the raw line to handle
     */
    protected abstract void handleLine(String rawLine);

    /**
     * Sends the raw line of data to the irc server
     *
     * @param line the line of data
     */
    public abstract void sendRawLine(String line);

    /**
     * Adds a raw line of data to the queue of data
     *
     * @param line the line of data
     */
    public abstract void sendRawLineViaQueue(String line);

    /**
     * Gets the max line length of the bot
     *
     * @return the max line length of the bot
     */
    public abstract int getMaxLineLength();

    /**
     * Gets the message delay of the bot
     *
     * @return the message delay of the bot
     */
    public abstract int getMessageDelay();

    /**
     * Sets the encoding charset.
     *
     * @param charset the new encoding charset
     */
    public abstract void setEncoding(String charset) throws UnsupportedEncodingException;

    /**
     * Gets the encoding charset
     *
     * @return the encoding charset used by the bot
     */
    public abstract String getEncoding();

    /**
     * Gets the InetAddress of the server the bot is connected to
     *
     * @return The InetAddress of the server
     */
    public abstract InetAddress getAddress();

    /**
     * Gets the command manager in use by the bot
     *
     * @return the command manager in use by the bot
     */
    public abstract CommandManager getCommandManager();
}