package com.dragovorn.dragonbot.manager;

import com.dragovorn.dragonbot.DragonBotApplication;
import com.dragovorn.dragonbot.api.gui.scene.IScene;
import com.dragovorn.dragonbot.gui.MainScene;
import com.google.common.collect.Maps;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Map;

public final class GuiManager {

    private static Stage stage;

    private static Scene current;

    private static final IScene DEFAULT = new MainScene();

    private static Map<String, IScene> scenes = Maps.newHashMap();

    public static void init(Stage stage) {
        if (GuiManager.stage != null) {
            throw new IllegalStateException("GuiManager has already been initialized!");
        }

        stage.setTitle("Dragon Bot v" + DragonBotApplication.VERSION);

        GuiManager.stage = stage;
    }


}
