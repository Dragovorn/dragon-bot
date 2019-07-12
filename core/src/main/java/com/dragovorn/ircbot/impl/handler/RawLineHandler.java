package com.dragovorn.ircbot.impl.handler;

import com.dragovorn.ircbot.api.event.EventPriority;
import com.dragovorn.ircbot.api.event.Listener;
import com.dragovorn.ircbot.api.event.irc.RawInputMessageEvent;
import com.dragovorn.ircbot.impl.bot.AbstractIRCBot;

import java.io.IOException;

public class RawLineHandler {

    @Listener(priority = EventPriority.LOWEST)
    public void onRawInputMessage(RawInputMessageEvent event) throws IOException {
        String line = event.getMessage();

        if (AbstractIRCBot.getInstance().isLogRawLinesEnabled()) {
            System.out.println("GET: " + line);
        }
    }
}
