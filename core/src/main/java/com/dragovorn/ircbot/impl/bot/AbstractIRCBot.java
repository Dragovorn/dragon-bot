package com.dragovorn.ircbot.impl.bot;

import com.dragovorn.ircbot.api.IAPIManager;
import com.dragovorn.ircbot.api.bot.IIRCBot;
import com.dragovorn.ircbot.api.gui.IGuiManager;
import com.dragovorn.ircbot.api.gui.scene.IScene;
import com.dragovorn.ircbot.api.irc.IDispatcher;
import com.dragovorn.ircbot.api.irc.IIRCServer;
import com.dragovorn.ircbot.api.plugin.IPluginManager;
import com.dragovorn.ircbot.api.user.IUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.nio.file.Path;

public abstract class AbstractIRCBot implements IIRCBot {

    private boolean running;

    private final String version;
    private final String name;

    private static AbstractIRCBot instance;

    private IDispatcher dispatcher;

    private IPluginManager pluginManager;

    private HttpClient httpClient = HttpClientBuilder.create().build();

    private IUser user;

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
        instance = this;

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

    protected void setServer(IIRCServer server) {
        if (this.server != null && this.server.isConnected()) {
            throw new IllegalStateException("Cannot change bot server if the bot is already connected!");
        }

        this.server = server;
    }

    protected void setMainThread(Thread thread) {
        this.mainThread = thread;
    }

    protected void setHomePath(Path homePath) {
        this.homePath = homePath;
    }

    protected void setUser(IUser user) {
        this.user = user;
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

    public abstract Class<? extends IScene> getMainScene();

    protected void preShutdown() { }

    protected void postShutdown() { }

    @Override
    public final void startup() {
        if (this.running) {
            throw new IllegalStateException("IRCBot is already running!");
        }

        if (this.homePath == null) {
            throw new IllegalStateException("IRCBot requires a home path!");
        }

        if (this.guiManager == null) {
            throw new IllegalStateException("IRCBot requires a gui manager!");
        }

        if (this.apiManager == null) {
            throw new IllegalStateException("IRCBot requires an api manager!");
        }

        if (this.pluginManager == null) {
            throw new IllegalStateException("IRCBot requires a plugin manager!");
        }

        preStartup();

        System.out.println("Starting " + this.name + " v" + this.version);
        if (!this.homePath.toFile().exists()) {
            this.homePath.toFile().mkdirs();
        }

        postHomePathFileCreation();
        initializeScenes(this.guiManager);
        registerAPIs(this.apiManager);

        this.guiManager.useScene(getMainScene());

        this.pluginManager.loadPlugins(this.homePath.resolve("plugins"));
        this.pluginManager.enablePlugins();

        postPluginInitialize();

        this.guiManager.init();

        this.running = true;

        postStartup();
    }

    @Override
    public final void shutdown() {
        if (!this.running) {
            throw new IllegalStateException("IRCBot isn't running!");
        }

        preShutdown();

        this.pluginManager.disablePlugins();

        System.out.println("Shutting down!");

        postShutdown();
    }

    @Override
    public void connect() {
        if (this.server == null) {
            throw new IllegalStateException("IRCBot requires a server to connect to!");
        }

        this.server.getConnection(); // This creates a new connection, which does connection code.
    }

    @Override
    public boolean isRunning() {
        return this.running;
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
    public IIRCServer getServer() {
        return this.server;
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

    public static AbstractIRCBot getInstance() {
        return instance;
    }
}
