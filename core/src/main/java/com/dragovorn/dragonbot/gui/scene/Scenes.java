package com.dragovorn.dragonbot.gui.scene;

import com.dragovorn.dragonbot.DragonBot;
import com.dragovorn.dragonbot.api.gui.scene.IScene;

public interface Scenes {

    IScene MAIN = register(new MainScene());
    IScene ADVANCED_OPTIONS = register(new AdvancedScene());
    IScene BOT_ACCOUNT = register(new BotAccountScene());

    static IScene register(IScene scene) {
        return DragonBot.getInstance().getGuiManager().registerScene(scene);
    }
}
