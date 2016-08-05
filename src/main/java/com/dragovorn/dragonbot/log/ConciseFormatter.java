package com.dragovorn.dragonbot.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 3:03 PM.
 * as of 8/5/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class ConciseFormatter extends Formatter {

    private final DateFormat DATE = new SimpleDateFormat(System.getProperty("com.dragovorn.dragonbot.log-date-format", "HH:mm"));

    @Override
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder();

        builder.append(DATE.format(record.getMillis()));
        builder.append(" [");
        builder.append(record.getLevel().getLocalizedName());
        builder.append("] ");
        builder.append(formatMessage(record));
        builder.append('\n');

        if (record.getThrown() != null) {
            StringWriter writer = new StringWriter();
            record.getThrown().printStackTrace(new PrintWriter(writer));
            builder.append(writer);
        }

        return builder.toString();
    }
}