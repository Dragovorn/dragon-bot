package com.dragovorn.dragonbot.bot;

import com.dragovorn.dragonbot.DragonBot;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class BotDispatcher {

    private final DragonBot bot;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public BotDispatcher() {
        this.bot = (DragonBot) DragonBot.getInstance();
    }

    public void add(String line) {
        this.executorService.submit(() -> this.bot.getConnection().sendRawLine(line));
    }
}
