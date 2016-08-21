package com.dragovorn.dragonbot.api.bot.plugin;

import java.util.logging.Logger;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 8:34 AM.
 * as of 8/7/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public abstract class BotPlugin {

    private PluginInfo info;

    public void onEnable() { }

    public void onDisable() { }

    void setInfo(PluginInfo info) {
        this.info = info;
    }

    public PluginInfo getInfo() {
        return this.info;
    }

    public Logger getLogger() {
        return null;
    }
}