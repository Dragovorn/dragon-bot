package com.dragovorn.ircbot.api.plugin;

import java.nio.file.Path;

public interface IPluginManager {

    void enablePlugins();
    void disablePlugins();
    void loadPlugins(Path plugins);

    <T extends IPlugin> T getPluginClass(Class<T> clazz);
}
