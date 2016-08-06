package com.dragovorn.dragonbot.exceptions;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 3:51 AM.
 * as of 8/6/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class ConnectionException extends Exception {

    public ConnectionException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ConnectionException(Throwable throwable) {
        super(throwable);
    }

    public ConnectionException(String message) {
        super(message);
    }
}