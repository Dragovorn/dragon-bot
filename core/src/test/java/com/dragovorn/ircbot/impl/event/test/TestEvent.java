package com.dragovorn.ircbot.impl.event.test;

import com.dragovorn.ircbot.api.event.IEvent;

public class TestEvent implements IEvent {

    private String test;

    public TestEvent(String test) {
        this.test = test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getTest() {
        return this.test;
    }
}
