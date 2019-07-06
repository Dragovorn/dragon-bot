package com.dragovorn.dragonbot;

import com.dragovorn.dragonbot.api.IAPIManager;
import com.dragovorn.dragonbot.api.bot.AbstractIRCBot;
import com.dragovorn.dragonbot.api.bot.channel.IChannel;
import com.dragovorn.dragonbot.api.config.IConfiguration;
import com.dragovorn.dragonbot.api.gui.IGuiManager;
import com.dragovorn.dragonbot.api.file.Resources;
import com.dragovorn.dragonbot.api.web.api.ITwitchAPI;
import com.dragovorn.dragonbot.gui.scene.MainScene;
import com.dragovorn.dragonbot.manager.APIManager;
import com.dragovorn.dragonbot.manager.GuiManager;
import com.dragovorn.dragonbot.web.api.TwitchAPI;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public final class DragonBot extends AbstractIRCBot {

    public static final String CLIENT_ID = "2z1ry6ofmarrs5te7s1f209myewzbf"; // This isn't the secret so pls dont try anything

    private final Path home = Paths.get(System.getProperty("user.home") + File.separator + ".dragonbot");

    private BotConfiguration configuration;

    private final GuiManager guiManager;

    private final APIManager apiManager;

    private final String version;

    private boolean running;

    private Thread mainThread;

    DragonBot(Stage stage) {
        this.guiManager = new GuiManager(stage);
        this.apiManager = new APIManager();
        this.mainThread = Thread.currentThread();

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));

        Properties properties = new Properties();

        try {
            properties.load(Resources.getResource("project.properties").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.version = properties.getProperty("version");
    }

    @Override
    public void startup() {
        if (this.running) {
            throw new IllegalStateException("Dragon bot is already running!");
        }

        System.out.println("Starting Dragon Bot v" + this.version);
        if (!this.home.toFile().exists()) {
            this.home.toFile().mkdirs();
        }

        this.configuration = new BotConfiguration();
        this.configuration.load();

        try {
            this.guiManager.registerScene("main");
            this.guiManager.registerScene("advanced_options");
            this.guiManager.registerScene("bot_account");
            this.guiManager.registerScene("sub/login");
            this.guiManager.registerScene("sub/checking");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Use a method like this so that plugins can override our twitch api to add more functionality to it.
        this.apiManager.registerAPI(new TwitchAPI(), ITwitchAPI.class);

        this.guiManager.useScene(MainScene.class);

        // TODO: ENABLE PLUGINS HERE

        this.guiManager.init();

        this.running = true;
    }

    @Override
    public void shutdown() {
        if (!this.running) {
            throw new IllegalStateException("Dragon bot isn't running!");
        }

        // TODO DISABLE PLUGINS HERE

        this.configuration.save();

        System.out.println("Shutting down!");
    }

    @Override
    public void joinChannel(IChannel channel) {

    }

    @Override
    public void sendMessage(IChannel channel, String name) {

    }

    @Override
    public String getVersion() {
        return this.version;
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
    public Thread getMainThread() {
        return this.mainThread;
    }

    @Override
    public Path getHomePath() {
        return this.home;
    }

    @Override
    public IConfiguration getConfiguration() {
        return this.configuration;
    }
}
