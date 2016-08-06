package com.dragovorn.dragonbot.bot;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 4:18 AM.
 * as of 8/6/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
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
                    bot.getLogger().info("CHAT " + bot.getName() + ": " + line.substring(10 + bot.getChannel().length()));
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