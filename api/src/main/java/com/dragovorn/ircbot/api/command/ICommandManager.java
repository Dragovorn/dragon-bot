package com.dragovorn.ircbot.api.command;

import com.dragovorn.ircbot.api.irc.IChannel;
import com.dragovorn.ircbot.api.user.IUser;

public interface ICommandManager {

    void setPrefix(char prefix);
    void register(Object obj);
    void execute(IChannel channel, IUser user, String line);

    char getPrefix();
}
