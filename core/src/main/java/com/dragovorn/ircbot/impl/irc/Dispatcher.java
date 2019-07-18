package com.dragovorn.ircbot.impl.irc;

import com.dragovorn.ircbot.api.bot.IIRCBot;
import com.dragovorn.ircbot.api.bot.IRCBot;
import com.dragovorn.ircbot.api.irc.IDispatcher;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Dispatcher implements IDispatcher {

    private final IIRCBot bot;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public Dispatcher() {
        this.bot = IRCBot.getInstance();
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
