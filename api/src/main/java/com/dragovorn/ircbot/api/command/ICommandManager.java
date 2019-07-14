package com.dragovorn.ircbot.api.command;

public interface ICommandManager {

    void setPrefix(char prefix);
    void register(Object obj);
    void execute(String line);

    char getPrefix();
}
