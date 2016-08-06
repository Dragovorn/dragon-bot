package com.dragovorn.dragonbot.bot;

import java.io.*;
import java.net.Socket;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 3:56 AM.
 * as of 8/6/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
class InputThread extends Thread {

    private Bot bot;

    private Socket socket;

    private BufferedReader reader;

    private BufferedWriter writer;

    private boolean connected = true;
    private boolean dispose = false;

    public static final int MAX_LINE_LENGTH = 512;

    InputThread(Bot bot, Socket socket, BufferedReader reader, BufferedWriter writer) {
        this.bot = bot;
        this.socket = socket;
        this.reader = reader;
        this.writer = writer;

        setName("IRC Input Thread");
    }

    @Override
    public void run() {
        try {
            boolean running = true;

            while (running) {
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        try {
                            bot.handleLine(line);
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }

                    if (line == null) { // Server disconnected on us.
                        running = false;
                    }
                } catch (InterruptedIOException iioe) {
                    this.sendRawLine("PING " + (System.currentTimeMillis() / 1000));
                }
            }
        } catch (IOException exception) { /* Nothing */ }

        try {
            socket.close();
        } catch (IOException exception) {
            // Just assume the socket was already closed.
        }

        if (!dispose) {
            bot.getLogger().info("Disconnected!");
            connected = false;
        }
    }

    public void dispose() {
        try {
            dispose = true;
            socket.close();
        } catch (IOException exception) {
            // Do nothing
        }
    }

    void sendRawLine(String line) {
        OutputThread.sendRawLine(bot, writer, line);
    }

    boolean isConnected() {
        return connected;
    }
}