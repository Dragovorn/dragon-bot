package com.dragovorn.dragonbot.api.bot;

import com.dragovorn.dragonbot.api.IAPIManager;
import com.dragovorn.dragonbot.api.channel.IChannel;
import com.dragovorn.dragonbot.api.gui.IGuiManager;
import com.dragovorn.dragonbot.api.irc.IConnection;
import com.dragovorn.dragonbot.api.user.IUser;
import com.google.gson.Gson;
import org.apache.http.client.HttpClient;

import java.nio.file.Path;

public interface IIRCBot {

    void startup();
    void shutdown();

    String getVersion();

    IGuiManager getGuiManager();

    IAPIManager getAPIManager();

    IConnection getConnection();

    Thread getMainThread();

    Path getHomePath();

    IUser getAccount();

    HttpClient getClient();

    Gson getGSON();
}
