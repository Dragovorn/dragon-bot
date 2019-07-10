package com.dragovorn.ircbot.api.event;

public interface ICancellable {

    void cancel();
    void uncancel();
    void setCancelled(boolean cancelled);

    boolean isCancelled();
}
