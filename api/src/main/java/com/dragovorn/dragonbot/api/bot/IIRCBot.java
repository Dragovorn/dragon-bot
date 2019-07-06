package com.dragovorn.dragonbot.api.bot;

import com.dragovorn.dragonbot.api.IAPIManager;
import com.dragovorn.dragonbot.api.bot.channel.IChannel;
import com.dragovorn.dragonbot.api.config.IConfiguration;
import com.dragovorn.dragonbot.api.gui.IGuiManager;

import java.nio.file.Path;

public interface IIRCBot {

    void startup();
    void shutdown();
    void joinChannel(IChannel channel);
    void sendMessage(IChannel channel, String name);

    String getVersion();

    IGuiManager getGuiManager();

    IAPIManager getAPIManager();

    Thread getMainThread();

    Path getHomePath();

    IConfiguration getConfiguration();
}
