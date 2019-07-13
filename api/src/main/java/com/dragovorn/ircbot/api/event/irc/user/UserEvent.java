package com.dragovorn.ircbot.api.event.irc.user;

import com.dragovorn.ircbot.api.event.irc.IRCEvent;
import com.dragovorn.ircbot.api.irc.IConnection;
import com.dragovorn.ircbot.api.user.IUser;

public abstract class UserEvent extends IRCEvent {

    private final IUser user;

    public UserEvent(IConnection connection, IUser user) {
        super(connection);

        this.user = user;
    }

    public IUser getUser() {
        return this.user;
    }
}
