package com.dragovorn.dragonbot.gui.scene.account;

import com.dragovorn.dragonbot.api.gui.scene.AbstractScene;
import com.dragovorn.dragonbot.api.web.api.ITwitchAPI;

public final class CheckingAccountScene extends AbstractScene {

    void convertTokenToUsername(String token) {
        ITwitchAPI api = apiManager.getAPI(ITwitchAPI.class);
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
