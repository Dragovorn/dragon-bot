package com.dragovorn.ircbot.impl.event.test;

import com.dragovorn.ircbot.impl.event.EventBus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestEventBus {

    private EventBus eventBus;

    @Before
    public void before() {
        this.eventBus = new EventBus();
    }

    @Test
    public void testRegisterListener() {
        this.eventBus.registerListeners(new TestListener());

        TestEvent event = new TestEvent("fail");

        this.eventBus.fireEvent(event);

        assertEquals("pass", event.getTest());
    }
}
