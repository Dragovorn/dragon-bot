package com.dragovorn.dragonbot.log;

import com.dragovorn.dragonbot.DragonBot;
import com.dragovorn.dragonbot.api.bot.plugin.BotPlugin;

import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class PluginLogger extends Logger {

    private String prefix;

    public PluginLogger(BotPlugin plugin) {
        super(plugin.getClass().getCanonicalName(), null);

        this.prefix = "[" + plugin.getInfo().getName() + "] ";

        setParent(DragonBot.getInstance().getLogger());
    }

    @Override
    public void log(LogRecord record) {
        record.setMessage(this.prefix + record.getMessage());
        super.log(record);
    }
}