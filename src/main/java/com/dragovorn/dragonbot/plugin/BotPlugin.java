package com.dragovorn.dragonbot.plugin;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 8:34 AM.
 * as of 8/7/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public abstract class BotPlugin {

    private PluginInfo info;

    public abstract void onEnable();

    public abstract void onDisable();

    void setInfo(PluginInfo info) {
        this.info = info;
    }

    public PluginInfo getInfo() {
        return this.info;
    }
}