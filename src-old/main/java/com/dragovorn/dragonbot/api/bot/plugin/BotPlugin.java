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

import com.dragovorn.dragonbot.api.bot.file.FileManager;
import com.dragovorn.dragonbot.api.bot.scheduler.GroupedThreadFactory;
import com.dragovorn.dragonbot.log.PluginLogger;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public abstract class BotPlugin {

    private PluginInfo info;

    private Logger logger;

    private ExecutorService executorService;

    private File pluginFolder;

    public void onLoad() { }

    public void onEnable() { }

    public void onDisable() { }

    public File getPluginFolder() {
        return this.pluginFolder;
    }

    final void init(PluginInfo info) {
        this.info = info;
        this.pluginFolder = new File(FileManager.getPlugins(), info.getName());
        this.logger = new PluginLogger(this);
    }

    public final PluginInfo getInfo() {
        return this.info;
    }

    public final Logger getLogger() {
        return this.logger;
    }

    public final File registerFile(String file) {
        if (!this.pluginFolder.exists()) {
            this.pluginFolder.mkdirs();
        }

        return FileManager.addFile(new File(this.pluginFolder, file));
    }

    public ExecutorService getExecutorService() {
        if (this.executorService == null) {
            String name = (this.info == null) ? "unknown" : this.info.getName();
            this.executorService = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat(name + " Pool Thread #%1$d").setThreadFactory(new GroupedThreadFactory(this, name)).build());
        }

        return this.executorService;
    }
}