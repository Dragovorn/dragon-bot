package com.dragovorn.ircbot.api.event.irc;

import com.dragovorn.ircbot.api.event.IAsyncEvent;
import com.dragovorn.ircbot.api.irc.IConnection;

public class RawInputMessageEvent extends RawMessageEvent implements IAsyncEvent {

    public RawInputMessageEvent(IConnection connection, String message) {
        super(connection, message);
    }

    @Override
    public boolean isAsync() {
        return true;
    }
}
