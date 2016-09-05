/*
 * Copyright (c) 2016. Andrew Burr
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 *  associated documentation files (the "Software"), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge, publish, distribute,
 *  sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.dragovorn.dragonbot.api.bot.plugin;

import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.exceptions.InvalidPluginException;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

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