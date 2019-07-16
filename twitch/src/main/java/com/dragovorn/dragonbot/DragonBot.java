package com.dragovorn.dragonbot;

import com.dragovorn.dragonbot.api.TwitchAPI;
import com.dragovorn.dragonbot.command.GithubCommand;
import com.dragovorn.dragonbot.handler.ServerConnectHandler;
import com.dragovorn.dragonbot.handler.UserChannelHandler;
import com.dragovorn.dragonbot.irc.TwitchBotAccount;
import com.dragovorn.dragonbot.irc.TwitchIRCServer;
import com.dragovorn.dragonbot.user.TwitchUserFactory;
import com.dragovorn.ircbot.impl.bot.SimpleIRCBot;
import com.dragovorn.ircbot.api.IAPIManager;
import com.dragovorn.ircbot.api.file.Resources;
import com.dragovorn.ircbot.api.gui.IGuiManager;
import com.dragovorn.ircbot.api.gui.IScene;
import com.dragovorn.dragonbot.api.web.ITwitchAPI;
import com.dragovorn.dragonbot.scene.MainScene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public final class DragonBot extends SimpleIRCBot {

    public static final String CLIENT_ID = "2z1ry6ofmarrs5te7s1f209myewzbf"; // This isn't the secret so pls dont try anything

    private BotConfiguration configuration;

    private static final String VERSION;

    static {
        Properties properties = new Properties();

        try {
            properties.load(Resources.getResource("project.properties").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        VERSION = properties.getProperty("version");
    }

    protected DragonBot(Stage stage) {
        super(stage, "Dragon Bot", VERSION);

        setServer(new TwitchIRCServer());
        setBotAccount(new TwitchBotAccount());
        setUserFactory(new TwitchUserFactory());
        setLogRawLines(true);
        setHomePath(Paths.get(System.getProperty("user.home") + File.separator + ".dragonbot"));
    }

    @Override
    protected void postHomePathFileCreation() {
        this.configuration = new BotConfiguration();
        this.configuration.load();

        getEventBus().registerListeners(new ServerConnectHandler());
        getEventBus().registerListeners(new UserChannelHandler());

        getCommandManager().setLogInvalidArgument(true);
        getCommandManager().setLogInvalidCommand(true);

        getCommandManager().setPrefix('!');
        getCommandManager().register(new GithubCommand());

        if (getAccount().isValid()) {
            System.out.println("Valid account, connecting to IRC.");
            try {
                connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid account, not connecting to IRC.");
        }
    }

    @Override
    protected void initializeScenes(IGuiManager guiManager) {
        try {
            guiManager.registerScene("main");
            guiManager.registerScene("advanced_options");
            guiManager.registerScene("bot_account");
            guiManager.registerScene("sub/login");
            guiManager.registerScene("sub/checking");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Class<? extends IScene> getMainScene() {
        return MainScene.class;
    }

    @Override
    protected void registerAPIs(IAPIManager apiManager) {
        // Use a method like this so that plugins can override our twitch api to add more functionality to it.
        apiManager.registerAPI(new TwitchAPI(), ITwitchAPI.class);
    }

    @Override
    public void preShutdown() {
        this.configuration.save();
    }

    public BotConfiguration getConfiguration() {
        return this.configuration;
    }
}
