package com.dragovorn.ircbot.api.command.argument;

public class StringArgument implements IArgument<String> {

    private String data;

    @Override
    public void parse(String data) {
        this.data = data;
    }

    @Override
    public boolean is(String data) {
        return true;
    }

    @Override
    public String get() {
        return this.data;
    }
}
