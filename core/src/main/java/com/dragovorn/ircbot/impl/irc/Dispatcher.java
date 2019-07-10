package com.dragovorn.ircbot.impl.irc;

import com.dragovorn.ircbot.impl.bot.AbstractIRCBot;
import com.dragovorn.ircbot.api.irc.IDispatcher;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Dispatcher implements IDispatcher {

    private final AbstractIRCBot bot;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public Dispatcher() {
        this.bot = AbstractIRCBot.getInstance();
    }

    public void dispatch(String line) {
        this.executorService.submit(() -> {
            try {
                this.bot.getServer().getConnection().sendRawLine(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
