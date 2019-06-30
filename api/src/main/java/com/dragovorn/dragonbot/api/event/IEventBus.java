package com.dragovorn.dragonbot.api.event;

public interface IEventBus {

    void fireEvent(IEvent event);
    void registerListeners(Object object);
}
