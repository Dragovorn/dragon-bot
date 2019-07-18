package com.dragovorn.ircbot.api.command.argument;

public interface IArgument<T> {

    void parse(String data);

    boolean is(String data);

    T get();
}
