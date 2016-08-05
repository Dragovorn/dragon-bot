package com.dragovorn.dragonbot.log;

import javax.swing.*;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 4:38 PM.
 * as of 8/5/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class ConsoleHandler extends StreamHandler {

    JTextArea textArea = null;

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void publish(LogRecord record) {
        super.publish(record);
        flush();

        if (textArea != null) {
            textArea.append(getFormatter().format(record));
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }
}