package com.dragovorn.ircbot.api.exception.command;

public class UnregisteredCommandException extends CommandExecutionException {

    public UnregisteredCommandException(String label) {
        super(label + " isn't a registered command!");
    }
}
