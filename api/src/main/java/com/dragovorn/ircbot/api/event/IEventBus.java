package com.dragovorn.ircbot.api.event;

public interface IEventBus {

    void fireEvent(IEvent event);
    void registerListeners(Object object);
}
