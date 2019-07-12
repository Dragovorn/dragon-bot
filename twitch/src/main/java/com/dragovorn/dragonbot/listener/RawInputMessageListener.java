package com.dragovorn.dragonbot.listener;

import com.dragovorn.ircbot.api.event.Listener;
import com.dragovorn.ircbot.api.event.irc.RawInputMessageEvent;

public class RawInputMessageListener {

    @Listener
    public void onRawInputMessage(RawInputMessageEvent event) {
        System.out.println(event.getMessage());
    }
}
