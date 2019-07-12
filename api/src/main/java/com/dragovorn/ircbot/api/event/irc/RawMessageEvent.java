package com.dragovorn.ircbot.api.event.irc;

import com.dragovorn.ircbot.api.event.IEvent;

public class RawMessageEvent implements IEvent {

    private String message;

    RawMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
