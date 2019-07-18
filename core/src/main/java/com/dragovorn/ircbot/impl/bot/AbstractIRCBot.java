package com.dragovorn.ircbot.impl.bot;

import com.dragovorn.ircbot.api.IAPIManager;
import com.dragovorn.ircbot.api.bot.IIRCBot;
import com.dragovorn.ircbot.api.bot.IRCBot;
import com.dragovorn.ircbot.api.command.ICommandManager;
import com.dragovorn.ircbot.api.event.IEventBus;
import com.dragovorn.ircbot.api.factory.IFactory;
import com.dragovorn.ircbot.api.gui.IGuiManager;
import com.dragovorn.ircbot.api.gui.IScene;
import com.dragovorn.ircbot.api.irc.IDispatcher;
import com.dragovorn.ircbot.api.irc.IIRCServer;
import com.dragovorn.ircbot.api.plugin.IPluginManager;
import com.dragovorn.ircbot.api.user.IUser;
import com.dragovorn.ircbot.api.user.UserInfo;
import com.dragovorn.ircbot.impl.handler.MessageHandler;
import com.dragovorn.ircbot.impl.handler.RawLineHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.nio.file.Path;

public abstract class AbstractIRCBot extends IRCBot {

    private boolean running;
    private boolean logRaw;

    private final String version;
    private final String name;

    private IDispatcher dispatcher;

    private IPluginManager pluginManager;

    private HttpClient httpClient = HttpClientBuilder.create().build();

    private IUser user;

    private IEventBus eventBus;

    private ICommandManager commandManager;

    private IFactory<? extends IUser, UserInfo> userFactory;

    private Path homePath;

    private IGuiManager guiManager;

    private IAPIManager apiManager;

    private IIRCServer server;

    private Thread mainThread;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public AbstractIRCBot(String version) {
        this("IRCBot", version);
    }

    public AbstractIRCBot(String name, String version) {
        this.name = name;
        this.version = version;
        this.mainThread = Thread.currentThread();

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    protected void setGuiManager(IGuiManager guiManager) {
        if (this.guiManager != null && this.guiManager.isInitialized()) {
            throw new IllegalStateException("Cannot change bot gui manager if the gui manager is already initialized!");
        }

        this.guiManager = guiManager;
    }

    protected void setAPIManager(IAPIManager apiManager) {
        this.apiManager = apiManager;
    }

    protected void setCommandManager(ICommandManager commandManager) {
        this.commandManager = commandManager;
    }

    protected void setServer(IIRCServer server) {
        if (this.server != null && this.server.isConnected()) {
            throw new IllegalStateException("Cannot change bot server if the bot is already connected!");
        }

        this.server = server;
    }

    protected void setLogRawLines(boolean logRaw) {
        this.logRaw = logRaw;
    }

    protected void setEventBus(IEventBus eventBus) {
        this.eventBus = eventBus;
    }

    protected void setMainThread(Thread thread) {
        this.mainThread = thread;
    }

    protected void setHomePath(Path homePath) {
        this.homePath = homePath;
    }

    protected void setBotAccount(IUser user) {
        this.user = user;
    }

    protected void setUserFactory(IFactory<? extends IUser, UserInfo> factory) {
        this.userFactory = factory;
    }

    protected void setDispatcher(IDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    protected void setPluginManager(IPluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    protected void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    protected void setGson(Gson gson) {
        this.gson = gson;
    }

    protected void postHomePathFileCreation() { }

    protected void registerAPIs(IAPIManager apiManager) { }

    protected void postPluginInitialize() { }

    protected void preStartup() { }

    protected void postStartup() { }

    protected abstract void initializeScenes(IGuiManager guiManager);

    protected void preShutdown() { }

    protected void postShutdown() { }

    @Override
    public final void startup() {
        if (isRunning()) {
            throw new IllegalStateException("IRCBot is already running!");
        }

        if (getHomePath() == null) {
            throw new IllegalStateException("IRCBot requires a home path!");
        }

        if (getGuiManager() == null) {
            throw new IllegalStateException("IRCBot requires a gui manager!");
        }

        if (getAPIManager() == null) {
            throw new IllegalStateException("IRCBot requires an api manager!");
        }

        if (getPluginManager() == null) {
            throw new IllegalStateException("IRCBot requires a plugin manager!");
        }

        if (getEventBus() == null) {
            throw new IllegalStateException("IRCBot requires an event bus!");
        }

        if (getUserFactory() == null) {
            throw new IllegalStateException("IRCBot requires a user factory!");
        }

        if (getCommandManager() == null) {
            throw new IllegalStateException("IRCBot requires a command manager!");
        }

        preStartup();

        System.out.println("Starting " + getName() + " v" + getVersion());
        if (!getHomePath().toFile().exists()) {
            getHomePath().toFile().mkdirs();
        }

        Path plugins = getHomePath().resolve("plugins");

        if (!plugins.toFile().exists()) {
            plugins.toFile().mkdirs();
        }

        getEventBus().registerListeners(new RawLineHandler());
        getEventBus().registerListeners(new MessageHandler());

        postHomePathFileCreation();
        initializeScenes(getGuiManager());
        registerAPIs(getAPIManager());
        getPluginManager().loadPlugins(plugins);
        getPluginManager().enablePlugins();

        postPluginInitialize();

        getGuiManager().init();

        this.running = true;

        postStartup();
    }

    @Override
    public final void shutdown() {
        if (!isRunning()) {
            throw new IllegalStateException("IRCBot isn't running!");
        }

        System.out.println("Shutting down...");

        this.running = false;

        preShutdown();

        getPluginManager().disablePlugins();

        if (getServer().isConnected()) {
            try {
                System.out.println("Disconnecting from IRC Server...");
                getServer().getConnection().disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        postShutdown();

        System.out.println("Goodbye!");
    }

    @Override
    public void connect() throws IOException {
        if (this.server == null) {
            throw new IllegalStateException("IRCBot requires a server to connect to!");
        }

        this.server.connect(); // Time to connect to server!
    }

    @Override
    public void sendRaw(String line) {
        this.dispatcher.dispatch(line);
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    @Override
    public boolean isLogRawLinesEnabled() {
        return this.logRaw;
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public IGuiManager getGuiManager() {
        return this.guiManager;
    }

    @Override
    public IAPIManager getAPIManager() {
        return this.apiManager;
    }

    @Override
    public ICommandManager getCommandManager() {
        return this.commandManager;
    }

    @Override
    public IIRCServer getServer() {
        return this.server;
    }

    @Override
    public IEventBus getEventBus() {
        return this.eventBus;
    }

    @Override
    public Thread getMainThread() {
        return this.mainThread;
    }

    @Override
    public Path getHomePath() {
        return this.homePath;
    }

    @Override
    public IUser getAccount() {
        return this.user;
    }

    @Override
    public IFactory<? extends IUser, UserInfo> getUserFactory() {
        return this.userFactory;
    }

    @Override
    public IDispatcher getDispatcher() {
        return this.dispatcher;
    }

    @Override
    public IPluginManager getPluginManager() {
        return this.pluginManager;
    }

    @Override
    public HttpClient getHttpClient() {
        return this.httpClient;
    }

    @Override
    public Gson getGSON() {
        return this.gson;
    }
}
