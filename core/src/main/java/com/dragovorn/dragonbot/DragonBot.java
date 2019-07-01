package com.dragovorn.dragonbot;

import com.dragovorn.dragonbot.api.bot.AbstractIRCBot;
import com.dragovorn.dragonbot.api.bot.channel.IChannel;
import com.dragovorn.dragonbot.api.config.IConfiguration;
import com.dragovorn.dragonbot.api.gui.IGuiManager;
import com.dragovorn.dragonbot.api.file.Resources;
import com.dragovorn.dragonbot.manager.GuiManager;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public final class DragonBot extends AbstractIRCBot {

    private final Path home = Paths.get(System.getProperty("user.home") + File.separator + ".dragonbot");

    private BotConfiguration configuration;

    private final GuiManager guiManager;

    private final String version;

    private boolean running;

    private Thread mainThread;

    DragonBot(Stage stage) {
        this.guiManager = new GuiManager(stage);
        this.mainThread = Thread.currentThread();

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
        this.guiManager.init();

        this.running = true;
    }

    @Override
    public void shutdown() {
        if (!this.running) {
            throw new IllegalStateException("Dragon bot isn't running!");
        }

        this.configuration.save();

        System.out.println("Shutting down!");
        System.exit(0);
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
