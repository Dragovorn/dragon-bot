package com.dragovorn.dragonbot.log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.LogRecord;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 3:10 PM.
 * as of 8/5/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class LogDispatcher extends Thread {

    private final DragonLogger logger;

    private final BlockingQueue<LogRecord> queue = new LinkedBlockingQueue<>();

    public LogDispatcher(DragonLogger logger) {
        super("Dragon Bot Logger Thread");
        this.logger = logger;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            LogRecord record;

            try {
                record = queue.take();
            } catch (InterruptedException exception) {
                continue;
            }

            logger.doLog(record);
        }

        for (LogRecord record : queue) {
            logger.doLog(record);
        }
    }

    public void queue(LogRecord record) {
        if (!isInterrupted()) {
            queue.add(record);
        }
    }
}