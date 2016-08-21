package com.dragovorn.dragonbot.bot;

import com.dragovorn.dragonbot.FileLocations;
import com.dragovorn.dragonbot.api.bot.configuration.Configuration;

import java.util.*;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 10:22 AM.
 * as of 8/6/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
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
        defaults.put("auto-connect", false);
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
        return getBoolean("auto-connect");
    }

    public List<String> getDisabledCommands() {
        return (List<String>) getList("disabled commands");
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
        set("auto-connect", autoConnect);
    }

    public void setDisabledCommands(List<String> disabledCommands) {
        set("disabled commands", disabledCommands);
    }

    public void addDisabledCommand(String disabledCommand) {
        List<String> commands = (List<String>) entries.get("disabled commands");

        if (commands.contains(disabledCommand)) {
            return;
        }

        commands.add(disabledCommand);

        setDisabledCommands(commands);
    }

    public void removeDisabledCommand(String disabledCommand) {
        List<String> commands = (List<String>) entries.get("disabled commands");

        if (!commands.contains(disabledCommand)) {
            return;
        }

        commands.remove(disabledCommand);

        setDisabledCommands(commands);
    }
}