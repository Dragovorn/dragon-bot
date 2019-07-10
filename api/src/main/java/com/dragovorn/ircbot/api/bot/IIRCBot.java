package com.dragovorn.ircbot.api.bot;

import com.dragovorn.ircbot.api.IAPIManager;
import com.dragovorn.ircbot.api.gui.IGuiManager;
import com.dragovorn.ircbot.api.irc.IDispatcher;
import com.dragovorn.ircbot.api.irc.IIRCServer;
import com.dragovorn.ircbot.api.plugin.IPluginManager;
import com.dragovorn.ircbot.api.user.IUser;
import com.google.gson.Gson;
import org.apache.http.client.HttpClient;

import java.nio.file.Path;

public interface IIRCBot {

    void startup();
    void shutdown();
    void connect();

    boolean isRunning();

    String getVersion();
    String getName();

    IGuiManager getGuiManager();

    IAPIManager getAPIManager();

    IIRCServer getServer();

    Thread getMainThread();

    Path getHomePath();

    IUser getAccount();

    IDispatcher getDispatcher();

    IPluginManager getPluginManager();

    HttpClient getHttpClient();

    Gson getGSON();
}
