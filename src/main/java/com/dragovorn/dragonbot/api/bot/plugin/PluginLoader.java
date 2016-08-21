package com.dragovorn.dragonbot.api.bot.plugin;

import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.exceptions.InvalidPluginException;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 6:50 PM.
 * as of 8/7/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class PluginLoader {

    public BotPlugin loadPlugin(File file) throws InvalidPluginException {
        if (file.getName().matches("(.+).(jar)$")) {
            JarFile jar;

            try {
                jar = new JarFile(file);

                URLClassLoader loader = new URLClassLoader(new URL[] {file.toURI().toURL()}, getClass().getClassLoader());

                Plugin plugin = null;
                BotPlugin botPlugin = null;

                for (ZipEntry entry : Collections.list(jar.entries())) {
                    if (entry.getName() != null && entry.getName().startsWith("__MACOSX")) {
                        continue;
                    }

                    if (!entry.getName().matches("(.+).(class)$")) {
                        continue;
                    }

                    Class<?> clazz = Class.forName(entry.getName().replace(".class", "").replace("/", "."), true, loader);

                    Plugin pl = clazz.getAnnotation(Plugin.class);

                    if (plugin != null && pl != null) {
                        throw new InvalidPluginException("There are two classes with @Plugin annotations " + entry.getName());
                    } else if (pl != null) {
                        if (!BotPlugin.class.isAssignableFrom(clazz)) {
                            throw new InvalidPluginException("The class with the @Plugin annotation does not extend BotPlugin " + entry.getName());
                        }

                        plugin = pl;
                        botPlugin = (BotPlugin) clazz.newInstance();
                    }
                }

                if (plugin == null) {
                    throw new InvalidPluginException("There is no class with the @Plugin annotation " + file.getPath());
                }

                botPlugin.setInfo(new PluginInfo(plugin));

                Bot.getInstance().getLogger().info("Loading " + plugin.name() + " v" + plugin.version() + "!");
                botPlugin.onEnable();
                Bot.getInstance().getLogger().info(plugin.name() + " v" + plugin.version() + " loaded!");

                return botPlugin;
            } catch (Exception exception) {
                throw new InvalidPluginException(exception);
            }
        }

        return null; // Shouldn't happen
    }
}