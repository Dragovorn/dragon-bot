package com.dragovorn.ircbot.impl.event.test;

import com.dragovorn.ircbot.api.event.Listener;

public class TestListener {

    @Listener
    public void testListener(TestEvent event) {
        event.setTest("pass");
    }
}
