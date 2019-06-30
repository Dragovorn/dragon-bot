package com.dragovorn.dragonbot.api.bot;

import com.dragovorn.dragonbot.api.bot.channel.IChannel;
import com.dragovorn.dragonbot.api.gui.IGuiManager;

public interface IIRCBot {

    void startup();
    void shutdown();
    void joinChannel(IChannel channel);
    void sendMessage(IChannel channel, String name);

    String getVersion();

    IGuiManager getGuiManager();

    Thread getMainThread();
}
