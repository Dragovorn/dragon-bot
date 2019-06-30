package com.dragovorn.dragonbot.gui.scene;

import com.dragovorn.dragonbot.api.gui.scene.AbstractScene;

public final class BotAccountScene extends AbstractScene {

    public BotAccountScene() {
        super("botaccount", "botaccount");
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
