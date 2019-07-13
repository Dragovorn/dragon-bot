package com.dragovorn.dragonbot.scene.account;

import com.dragovorn.dragonbot.DragonBot;
import com.dragovorn.ircbot.impl.gui.fxml.AbstractFXMLScene;
import com.dragovorn.dragonbot.api.web.ITwitchAPI;
import com.dragovorn.ircbot.impl.user.BotAccount;

import java.io.IOException;

public final class CheckingAccountScene extends AbstractFXMLScene {

    void convertTokenToUsername(String token) {
        ITwitchAPI api = apiManager.getAPI(ITwitchAPI.class);

        String username = null;

        try {
            username = api.getUsernameFromAccessToken(token);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BotAccount botAccount = (BotAccount) DragonBot.getInstance().getAccount();

        botAccount.setUsername(username);
        botAccount.setPassword(token);

        DragonBot bot = (DragonBot) DragonBot.getInstance();

        bot.getConfiguration().set(botAccount);

        BotAccountScene scene = guiManager.getScene(BotAccountScene.class);

        guiManager.close(scene.getLogin());

        try {
            bot.getServer().connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        scene.updateButtons();
    }

    @Override
    public int getWidth() {
        return 200;
    }

    @Override
    public int getHeight() {
        return 100;
    }
}
