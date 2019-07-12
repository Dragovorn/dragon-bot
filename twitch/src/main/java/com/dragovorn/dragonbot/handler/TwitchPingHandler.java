package com.dragovorn.dragonbot.handler;

import com.dragovorn.ircbot.api.event.EventPriority;
import com.dragovorn.ircbot.api.event.Listener;
import com.dragovorn.ircbot.api.event.irc.RawInputMessageEvent;

import java.io.IOException;

public class TwitchPingHandler {

    @Listener(priority = EventPriority.MONITOR)
    public void onRawInputMessage(RawInputMessageEvent event) throws IOException {
        String line = event.getMessage();

        if (line.startsWith("PING")) {
            event.getConnection().sendRawLine("PONG " + line.substring(5));
        }
    }
}
