package com.dragovorn.dragonbot.log;

import com.dragovorn.dragonbot.gui.ConsoleWindow;

import java.io.IOException;
import java.util.logging.*;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 2:55 PM.
 * as of 8/5/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
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

            ConsoleHandler console = new ConsoleHandler();
            console.setTextArea(ConsoleWindow.make().getConsole());
            console.setFormatter(FORMAT);
            addHandler(console);
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