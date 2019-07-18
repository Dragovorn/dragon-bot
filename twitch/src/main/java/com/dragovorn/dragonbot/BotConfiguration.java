package com.dragovorn.dragonbot;

import com.dragovorn.ircbot.impl.config.FileConfiguration;
import com.dragovorn.ircbot.impl.user.BotAccount;

public final class BotConfiguration extends FileConfiguration {

    BotConfiguration() {
        super(DragonBot.getInstance().getHomePath().resolve("config.json"));
    }

    @Override
    public void addDefaults() {
        this.defaults.put("console", false);
        this.defaults.put("custom_commands", false);
        this.defaults.put("account.username", "");
        this.defaults.put("account.access_token", "");
    }

    @Override
    public void load() {
        super.load();

        BotAccount botAccount = (BotAccount) DragonBot.getInstance().getAccount();

        botAccount.setUsername(get("account.username"));
        botAccount.setPassword(get("account.password"));
    }
}
