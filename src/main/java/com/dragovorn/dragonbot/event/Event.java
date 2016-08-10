package com.dragovorn.dragonbot.event;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 4:40 PM.
 * as of 8/10/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public abstract class Event {

    private final boolean async;

    private String name;

    public Event() {
        this(false);
    }

    public Event(boolean async) {
        this.async = async;
    }

    public String getEventName() {
        if (name == null) {
            name = getClass().getSimpleName();
        }

        return name;
    }

    public final boolean isAsynchronous() {
        return async;
    }
}