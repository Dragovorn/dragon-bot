package com.dragovorn.dragonbot.event;

import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.bot.BotState;

import java.util.ArrayList;
import java.util.List;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 4:48 PM.
 * as of 8/10/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class EventManager {

    private List<Listener> listeners;

    public EventManager() {
        listeners = new ArrayList<>();
    }

    public void registerListener(Listener listener) {
        if (Bot.getInstance().getState() != BotState.STARTING) {
            return;
        }

        if (listeners.contains(listener)) {
            return;
        }

        listeners.add(listener);
    }
}