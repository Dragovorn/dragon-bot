package com.dragovorn.dragonbot.configuration;

import com.dragovorn.dragonbot.FileLocations;

import java.util.ArrayList;
import java.util.List;

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

    private void setDefaults() {
        entries.clear();

        set("name", "");
        set("oauth", "");
        set("channel", "");
        set("console", false);
        set("auto-connect", false);
        set("disabled commands", new ArrayList<String>());
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