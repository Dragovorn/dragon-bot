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
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE..
 */

package com.dragovorn.dragonbot.bot;

import java.io.BufferedWriter;
import java.io.IOException;

class OutputThread extends Thread {

    private Bot bot;

    private Queue queue;

    OutputThread(Bot bot, Queue outQueue) {
        this.bot = bot;
        this.queue = outQueue;

        setName("IRC Output Thread");
    }

    static void sendRawLine(Bot bot, BufferedWriter writer, String line) {
        if (line.length() > bot.getMaxLineLength()) {
            line = line.substring(0, bot.getMaxLineLength() - 2);
        }

        synchronized (writer) {
            try {
                writer.write(line + "\r\n");
                writer.flush();

                if (line.startsWith("PASS") || line.startsWith("PONG")) {
                    return;
                }

                if (line.startsWith("PRIVMSG")) {
                    bot.getLogger().info("CHAT [Bot] " + bot.getName() + ": " + line.substring(line.indexOf(" :") + 2));
                } else {
                    bot.getLogger().info(line);
                }
            } catch (IOException exception) {
                // Lose the line
            }
        }
    }

    @Override
    public void run() {
        try {
            boolean running = true;

            while (running) {
                Thread.sleep(bot.getMessageDelay()); // Prevent spamming

                String line = (String) queue.next();

                if (line != null) {
                    bot.sendRawLine(line);
                } else {
                    running = false;
                }
            }
        } catch (InterruptedException exception) {
            // Let method return naturally
        }
    }
}