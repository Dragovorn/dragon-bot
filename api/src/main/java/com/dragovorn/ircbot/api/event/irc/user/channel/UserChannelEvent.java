package com.dragovorn.ircbot.api.event.irc.user.channel;

import com.dragovorn.ircbot.api.event.irc.user.UserEvent;
import com.dragovorn.ircbot.api.irc.IChannel;
import com.dragovorn.ircbot.api.irc.IConnection;
import com.dragovorn.ircbot.api.user.IUser;

public abstract class UserChannelEvent extends UserEvent {

    private final IChannel channel;

    public UserChannelEvent(IConnection connection, IUser user, IChannel channel) {
        super(connection, user);

        this.channel = channel;
    }

    public IChannel getChannel() {
        return this.channel;
    }
}
