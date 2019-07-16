package com.dragovorn.ircbot.api.plugin;

public interface IPlugin {

    default void onLoad() { }
    default void onEnable() { }
    default void onDisable() { }
}
