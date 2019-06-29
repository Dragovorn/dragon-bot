package com.dragovorn.dragonbot;

import com.dragovorn.dragonbot.api.bot.AbstractIRCBot;
import com.dragovorn.dragonbot.api.bot.channel.IChannel;
import com.dragovorn.dragonbot.api.gui.IGuiManager;
import com.dragovorn.dragonbot.api.util.FileSystem;
import com.dragovorn.dragonbot.manager.GuiManager;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;

public final class DragonBot extends AbstractIRCBot {

    private final GuiManager guiManager;

    private final String version;

    private boolean running;

    DragonBot(Stage stage) {
        this.guiManager = new GuiManager(stage);

        Properties properties = new Properties();

        try {
            properties.load(FileSystem.getResource("project.properties").openStream());
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
        this.guiManager.init();

        this.running = true;
    }

    @Override
    public void shutdown() {
        if (!this.running) {
            throw new IllegalStateException("Dragon bot isn't running!");
        }

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
}
