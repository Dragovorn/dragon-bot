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

import com.dragovorn.dragonbot.FileLocations;
import com.dragovorn.dragonbot.api.bot.configuration.Configuration;

import java.util.*;

public class BotConfiguration extends Configuration {

    public BotConfiguration() {
        super(FileLocations.config);
    }

    protected Map<String, Object> defaults = new HashMap<>();

    private void addDefaults() {
        defaults.clear();

        defaults.put("name", "");
        defaults.put("oauth", "");
        defaults.put("channel", "");
        defaults.put("console", false);
        defaults.put("auto connect", false);
        defaults.put("test versions", false);
        defaults.put("ask for update", true);
        defaults.put("check for updates", false);
        defaults.put("twitch-api key", ""); // This might get removed.
    }

    private void setDefaults() {
        addDefaults();
        entries.clear();
        entries.putAll(defaults);
    }

    public void update() {
        addDefaults();

        for (Map.Entry<String, Object> entry : defaults.entrySet()) {
            if (!entries.containsKey(entry.getKey())) {
                entries.put(entry.getKey(), entry.getValue());
            }
        }

        ArrayList<String> remove = new ArrayList<>();

        for (Map.Entry<String, Object> entry : entries.entrySet()) {
            if (!defaults.containsKey(entry.getKey())) {
                remove.add(entry.getKey());
            }
        }

        for (String str : remove) {
            entries.remove(str);
        }
    }

    public void generate() {
        setDefaults();
        save();
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

    public boolean getPreReleases() {
        return getBoolean("test versions");
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

    public void setPreReleases(boolean preReleases) {
        set("test versions", preReleases);
    }
}