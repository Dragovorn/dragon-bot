package com.dragovorn.ircbot.api.bot;

import com.dragovorn.ircbot.api.IAPIManager;
import com.dragovorn.ircbot.api.event.IEventBus;
import com.dragovorn.ircbot.api.gui.IGuiManager;
import com.dragovorn.ircbot.api.irc.IDispatcher;
import com.dragovorn.ircbot.api.irc.IIRCServer;
import com.dragovorn.ircbot.api.plugin.IPluginManager;
import com.dragovorn.ircbot.api.user.IUser;
import com.google.gson.Gson;
import org.apache.http.client.HttpClient;

import java.io.IOException;
import java.nio.file.Path;

public interface IIRCBot {

    void startup();
    void shutdown();
    void connect() throws IOException;
    void sendRaw(String line);

    boolean isRunning();
    boolean isLogRawLinesEnabled();

    String getVersion();
    String getName();

    IGuiManager getGuiManager();

    IAPIManager getAPIManager();

    IIRCServer getServer();

    IEventBus getEventBus();

    Thread getMainThread();

    Path getHomePath();

    IUser getAccount();

    IDispatcher getDispatcher();

    IPluginManager getPluginManager();

    HttpClient getHttpClient();

    Gson getGSON();
}
