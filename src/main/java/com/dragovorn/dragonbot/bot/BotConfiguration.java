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

package com.dragovorn.dragonbot.bot;

import com.dragovorn.dragonbot.api.bot.configuration.Configuration;
import com.dragovorn.dragonbot.api.bot.file.FileManager;

public class BotConfiguration extends Configuration {

    public BotConfiguration() {
        super(FileManager.getConfig());
    }

    @Override
    protected void addDefaults() {
        this.defaults.clear();

        this.defaults.put("name", "");
        this.defaults.put("oauth", "");
        this.defaults.put("channel", "");
        this.defaults.put("console", true);
        this.defaults.put("auto connect", false);
        this.defaults.put("ask for update", true);
        this.defaults.put("check for updates", true);
        this.defaults.put("twitch-api key", ""); // This might get removed.
    }

    public String getName() {
        return getString("name");
    }

    public String getAuth() {
        return getString("oauth");
    }

    public String getChannel() {
        return getString("channel");
    }

    public boolean getConsole() {
        return getBoolean("console");
    }

    public boolean getAutoConnect() {
        return getBoolean("auto connect");
    }

    public boolean getAskForUpdates() {
        return getBoolean("ask for update");
    }

    public boolean getCheckForUpdates() {
        return getBoolean("check for updates");
    }

    public void setName(String name) {
        set("name", name);
    }

    public void setAuth(String auth) {
        set("oauth", auth);
    }

    public void setChannel(String channel) {
        set("channel", channel);
    }

    public void setConsole(boolean console) {
        set("console", console);
    }

    public void setAutoConnect(boolean autoConnect) {
        set("auto connect", autoConnect);
    }

    public void setAskForUpdates(boolean askForUpdates) {
        set("ask for update", askForUpdates);
    }

    public void setCheckForUpdates(boolean checkForUpdates) {
        set("check for updates", checkForUpdates);
    }
}