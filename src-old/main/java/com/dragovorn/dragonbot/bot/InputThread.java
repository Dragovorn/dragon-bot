/*
 * Copyright (c) 2016. Andrew Burr
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 *  associated documentation files (the "Software"), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge, publish, distribute,
 *  sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.dragovorn.dragonbot.bot;

import java.io.*;
import java.net.Socket;

public class InputThread extends Thread {

    private Bot bot;

    private Socket socket;

    private BufferedReader reader;

    private BufferedWriter writer;

    private boolean connected = true;
    private boolean dispose = false;

    public static final int MAX_LINE_LENGTH = 512;

    public InputThread(Bot bot, Socket socket, BufferedReader reader, BufferedWriter writer) {
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
                    while ((line = this.reader.readLine()) != null) {
                        try {
                            this.bot.handleLine(line);
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }

                    if (line == null) { // Server disconnected on us.
                        running = false;
                    }
                } catch (InterruptedIOException exception) {
                    this.sendRawLine("PING " + (System.currentTimeMillis() / 1000));
                }
            }
        } catch (IOException exception) { /* Nothing */ }

        try {
            this.socket.close();
        } catch (IOException exception) {
            // Just assume the socket was already closed.
        }

        if (!this.dispose) {
            this.bot.getLogger().info("Disconnected!");
            this.connected = false;
        }
    }

    public void dispose() {
        try {
            this.dispose = true;
            this.socket.close();
        } catch (IOException exception) {
            // Do nothing
        }
    }

    public void sendRawLine(String line) {
        OutputThread.sendRawLine(this.bot, this.writer, line);
    }

    public boolean isConnected() {
        return this.connected;
    }
}