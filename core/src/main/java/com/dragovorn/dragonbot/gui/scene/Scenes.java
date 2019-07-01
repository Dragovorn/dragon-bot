package com.dragovorn.dragonbot.gui.scene;

import com.dragovorn.dragonbot.DragonBot;
import com.dragovorn.dragonbot.api.gui.scene.IScene;

import java.util.concurrent.ExecutionException;

public interface Scenes {

    IScene MAIN = register("main", "main");
    IScene ADVANCED_OPTIONS = register("advanced", "advanced");
    IScene BOT_ACCOUNT = register("botaccount", "botaccount");

    static IScene register(String name, String fxml) {
        DragonBot.getInstance().getGuiManager().registerScene(name, fxml);

        try {
            return DragonBot.getInstance().getGuiManager().getFutureScene(name).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
