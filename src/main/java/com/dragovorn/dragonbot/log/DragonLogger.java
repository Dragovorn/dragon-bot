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

package com.dragovorn.dragonbot.log;

import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.gui.ConsoleWindow;

import java.io.IOException;
import java.util.logging.*;

public class DragonLogger extends Logger {

    private final Formatter FORMAT = new ConciseFormatter();
    private final LogDispatcher DISPATCHER = new LogDispatcher(this);

    public DragonLogger(String name, String filePattern) {
        super(name, null);
        setLevel(Level.ALL);

        try {
            FileHandler handler = new FileHandler(filePattern, 1 << 28, 8, true);
            handler.setFormatter(FORMAT);
            addHandler(handler);

            if (Bot.getInstance().getConfiguration().getConsole()) {
                ConsoleHandler console = new ConsoleHandler();
                console.setTextArea(new ConsoleWindow().getConsole());
                console.setFormatter(FORMAT);
                addHandler(console);
            }
        } catch (IOException exception) {
            System.err.println("Could not register logger!");
            exception.printStackTrace();
        }

        DISPATCHER.start();
    }

    @Override
    public void log(LogRecord record) {
        DISPATCHER.queue(record);
    }

    void doLog(LogRecord record) {
        super.log(record);
    }
}