package com.dragovorn.dragonbot.api.bot.plugin;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 8:08 AM.
 * as of 8/7/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class PluginInfo {

    private final String name;
    private final String version;
    private final String author;

    public PluginInfo(Plugin plugin) {
        this.name = plugin.name();
        this.version = plugin.version();
        this.author = plugin.author();
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getAuthor() {
        return author;
    }
}