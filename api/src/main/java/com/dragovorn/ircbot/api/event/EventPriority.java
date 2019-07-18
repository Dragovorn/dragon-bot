package com.dragovorn.ircbot.api.event;

public enum EventPriority {
    MONITOR(false),
    HIGHEST(true),
    HIGH(true),
    NORMAL(true),
    LOW(true),
    LOWEST(true);

    private boolean canModify;

    EventPriority(boolean canModify) {
        this.canModify = canModify;
    }

    public boolean canModify() {
        return this.canModify;
    }
}
