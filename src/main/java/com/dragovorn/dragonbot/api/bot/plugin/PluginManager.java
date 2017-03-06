/*
 * Copyright (c) 2016. Andrew Burr
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 *  associated documentation files (the "Software"), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge, publish, distribute,
 *  sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or
 *  substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 *  INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 *  PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 *  COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 *  ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 *  THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.dragovorn.dragonbot.api.bot.plugin;

import com.dragovorn.dragonbot.api.bot.file.FileManager;
import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.exception.InvalidPluginException;
import com.google.common.collect.ImmutableMap;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.zip.ZipEntry;

public class PluginManager {

    private Map<String, PluginInfo> load;

    private Map<String, BotPlugin> plugins;

    public PluginManager() {
        this.load = new HashMap<>();
        this.plugins = new HashMap<>();
    }

    public void loadPlugins() {
        if (FileManager.getPlugins().listFiles() != null) {
            for (File file : FileManager.getPlugins().listFiles()) {
                if (!file.getName().matches("(.+).(jar)$")) {
                    continue;
                }

                try {
                    PluginInfo info = getPluginInfo(file);

                    this.load.put(info.getName().toLowerCase(), info);
                } catch (InvalidPluginException exception) {
                    exception.printStackTrace();
                }
            }
        }

        Map<PluginInfo, Boolean> pluginStatus = new HashMap<>();

        for (Map.Entry<String, PluginInfo> entry : this.load.entrySet()) {
            try {
                if (!loadPlugin(pluginStatus, new Stack<>(), entry.getValue())) {
                    Bot.getInstance().getLogger().log(Level.INFO, "Failed to enable {0}", entry.getKey());
                }
            } catch (InvalidPluginException exception) {
                exception.printStackTrace();
            }
        }

        this.load.clear();
        this.load = null;
    }

    public void enablePlugins() {
        for (BotPlugin plugin : this.plugins.values()) {
            try {
                plugin.onEnable();

                Bot.getInstance().getLogger().log(Level.INFO, "Enabled plugin {0} version {1} by {2}", new Object[] { plugin.getInfo().getName(), plugin.getInfo().getVersion(), plugin.getInfo().getName() });
            } catch (Throwable throwable) {
                Bot.getInstance().getLogger().log(Level.WARNING, "Encountered exception while enabling plugin: " + plugin.getInfo().getName(), throwable);
            }
        }
    }

    public void disablePlugins() {
        for (BotPlugin plugin : this.plugins.values()) {
            try {
                plugin.onDisable();

                Bot.getInstance().getLogger().log(Level.INFO, "Disabled plugin {0} version {1} by {2}", new Object[] { plugin.getInfo().getName(), plugin.getInfo().getVersion(), plugin.getInfo().getAuthor() });
            } catch (Throwable throwable) {
                Bot.getInstance().getLogger().log(Level.WARNING, "Encountered exception while disabling plugin: " + plugin.getInfo().getName(), throwable);
            }
        }
    }

    public boolean loadPlugin(Map<PluginInfo, Boolean> pluginStatus, Stack<PluginInfo> dependencies, PluginInfo info) throws InvalidPluginException {
        if (pluginStatus.containsKey(info)) {
            return pluginStatus.get(info);
        }

        Set<String> depends = new HashSet<>();

        if (!info.getDependencies()[0].equals("")) {
            depends.addAll(Arrays.asList(info.getDependencies()));
        }

        boolean status = true;

        for (String name : depends) {
            PluginInfo depend = this.load.get(name.toLowerCase());

            Boolean dependStatus = (depend != null) ? pluginStatus.get(depend) : Boolean.FALSE;

            if (dependStatus == null) {
                if (dependencies.contains(depend)) {
                    StringBuilder graph = new StringBuilder();

                    for (PluginInfo pluginInfo : dependencies) {
                        graph.append(pluginInfo.getName()).append(" -> ");
                    }

                    graph.append(info.getName()).append(" -> ").append(name);
                    Bot.getInstance().getLogger().log(Level.WARNING, "Circular dependency detected {0}", graph);
                    status = false;
                } else {
                    dependencies.push(info);
                    dependStatus = this.loadPlugin(pluginStatus, dependencies, depend);
                    dependencies.pop();
                }
            }

            if (dependStatus == Boolean.FALSE) {
                Bot.getInstance().getLogger().log(Level.WARNING, "{0} (required by {1}) is not available!", new Object[] { name, info.getName() });
                status = false;
            }

            if (!status) {
                break;
            }
        }

        if (status) {
            try {
                URLClassLoader loader = new PluginLoader(new URL[] {info.getFile().toURI().toURL()});

                Class<?> main = loader.loadClass(info.getMain());
                BotPlugin clazz = (BotPlugin) main.getDeclaredConstructor().newInstance();
                clazz.init(info);

                this.plugins.put(info.getName(), clazz);
                clazz.onLoad();

                Bot.getInstance().getLogger().log(Level.INFO, "Loaded {0} version {1} by {2}!", new Object[] { info.getName(), info.getVersion(), info.getAuthor() });
            } catch (Throwable throwable) {
                throw new InvalidPluginException(throwable);
            }
        }

        pluginStatus.put(info, status);

        return status;
    }

    public PluginInfo getPluginInfo(File file) throws InvalidPluginException {
        if (file.getName().matches("(.+).(jar)$")) {
            JarFile jar;

            try {
                jar = new JarFile(file);

                URLClassLoader loader = new URLClassLoader(new URL[] {file.toURI().toURL()}, getClass().getClassLoader());

                Plugin plugin = null;
                Class<?> main = null;

                for (ZipEntry entry : Collections.list(jar.entries())) {
                    if (entry.getName() != null && entry.getName().equals("__MACOSX")) {
                        continue;
                    }

                    if (!entry.getName().matches("(.+).(class)")) {
                        continue;
                    }

                    Class<?> clazz = Class.forName(entry.getName().replace(".class", "").replace("/", "."), true, loader);

                    Plugin pl = clazz.getAnnotation(Plugin.class);

                    if (plugin != null && pl != null) {
                        throw new InvalidPluginException("There are two classes with the Plugin annotation");
                    } else if (pl != null) {
                        if (!BotPlugin.class.isAssignableFrom(clazz)) {
                            throw new InvalidPluginException("The class with the Plugin annotation does not extend BotPlugin");
                        }

                        plugin = pl;
                        main = clazz;
                    }
                }

                if (plugin == null) {
                    throw new InvalidPluginException("There is not class with the Plugin annotation");
                }

                return new PluginInfo(plugin, main.getCanonicalName(), file);
            } catch (Exception exception) {
                throw new InvalidPluginException(exception);
            }
        }

        throw new InvalidPluginException();
    }

    public ImmutableMap<String, BotPlugin> getPlugins() {
        return new ImmutableMap.Builder<String, BotPlugin>().putAll(this.plugins).build();
    }
}