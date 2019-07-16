package com.dragovorn.ircbot.api.exception.command;

public class CommandExecutionException extends Exception {

    public CommandExecutionException() {
        super();
    }

    public CommandExecutionException(String message) {
        super(message);
    }
}
