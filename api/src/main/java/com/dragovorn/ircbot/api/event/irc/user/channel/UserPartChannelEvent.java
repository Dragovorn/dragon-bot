package com.dragovorn.ircbot.api.event.irc.user.channel;

import com.dragovorn.ircbot.api.irc.IChannel;
import com.dragovorn.ircbot.api.irc.IConnection;
import com.dragovorn.ircbot.api.user.IUser;

public class UserPartChannelEvent extends UserChannelEvent {

    public UserPartChannelEvent(IConnection connection, IUser user, IChannel channel) {
        super(connection, user, channel);
    }
}
