package com.dragovorn.ircbot.api.command;

import com.dragovorn.ircbot.api.exception.command.CommandExecutionException;
import com.dragovorn.ircbot.api.irc.IChannel;
import com.dragovorn.ircbot.api.irc.IConnection;
import com.dragovorn.ircbot.api.user.IUser;

public interface ICommandManager {

    void setPrefix(char prefix);
    void register(Object obj);
    void setLogInvalidCommand(boolean logInvalidCommand);
    void setLogInvalidArgument(boolean logInvalidArgument);
    void execute(IChannel channel, IUser user, IConnection connection, String line) throws CommandExecutionException;

    boolean isLogInvalidCommandEnabled();
    boolean isLogInvalidArgumentEnabled();

    char getPrefix();
}
