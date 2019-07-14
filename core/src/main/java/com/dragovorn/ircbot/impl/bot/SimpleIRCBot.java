package com.dragovorn.ircbot.impl.bot;

import com.dragovorn.ircbot.impl.command.CommandManager;
import com.dragovorn.ircbot.impl.event.EventBus;
import com.dragovorn.ircbot.impl.gui.fxml.FXMLGuiManager;
import com.dragovorn.ircbot.impl.irc.Dispatcher;
import com.dragovorn.ircbot.impl.manager.APIManager;
import com.dragovorn.ircbot.impl.manager.PluginManager;
import com.dragovorn.ircbot.impl.user.BotAccount;
import javafx.stage.Stage;

public abstract class SimpleIRCBot extends AbstractIRCBot {

    protected SimpleIRCBot(Stage stage, String name, String version) {
        super(name, version);

        setGuiManager(new FXMLGuiManager(stage)); // TODO: Make a console GUI manager later to make this even more simple.
        setPluginManager(new PluginManager());
        setAPIManager(new APIManager());
        setBotAccount(new BotAccount());
        setDispatcher(new Dispatcher());
        setEventBus(new EventBus());
        setCommandManager(new CommandManager());
    }
}
