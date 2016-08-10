package com.dragovorn.dragonbot.exceptions;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 8:02 AM.
 * as of 8/7/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class InvalidPluginException extends Exception {

    public InvalidPluginException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public InvalidPluginException(Throwable throwable) {
        super(throwable);
    }

    public InvalidPluginException(String message) {
        super(message);
    }
}
