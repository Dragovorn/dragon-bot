package com.dragovorn.dragonbot;

import com.dragovorn.dragonbot.api.config.FileConfiguration;

final class BotConfiguration extends FileConfiguration {

    BotConfiguration() {
        super(DragonBot.getInstance().getHomePath().resolve("config.json"));
    }

    @Override
    public void addDefaults() {
        this.defaults.put("console", false);
        this.defaults.put("custom_commands", false);
        this.defaults.put("account.username", "");
        this.defaults.put("account.oauth", "");
    }
}
