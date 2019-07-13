package com.dragovorn.ircbot.api.event.irc.user.channel.message;

import com.dragovorn.ircbot.api.event.irc.user.channel.UserChannelEvent;
import com.dragovorn.ircbot.api.irc.IChannel;
import com.dragovorn.ircbot.api.irc.IConnection;
import com.dragovorn.ircbot.api.user.IUser;

public class UserMessageChannelEvent extends UserChannelEvent {

    private final String message;

    public UserMessageChannelEvent(IConnection connection, IUser user, IChannel channel, String message) {
        super(connection, user, channel);

        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
