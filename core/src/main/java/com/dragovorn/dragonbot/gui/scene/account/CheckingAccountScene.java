package com.dragovorn.dragonbot.gui.scene.account;

import com.dragovorn.dragonbot.DragonBot;
import com.dragovorn.dragonbot.api.gui.scene.AbstractScene;
import com.dragovorn.dragonbot.api.web.api.ITwitchAPI;
import com.dragovorn.dragonbot.user.BotAccount;

import java.io.IOException;

public final class CheckingAccountScene extends AbstractScene {

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
        botAccount.setAccessToken(token);

        DragonBot bot = (DragonBot) DragonBot.getInstance();

        bot.getConfiguration().set(botAccount);

        BotAccountScene scene = guiManager.getScene(BotAccountScene.class);

        guiManager.close(scene.getLogin());

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
