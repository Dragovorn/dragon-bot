package com.dragovorn.dragonbot.manager;

import com.dragovorn.dragonbot.Main;
import com.dragovorn.dragonbot.api.gui.scene.IScene;
import com.dragovorn.dragonbot.gui.MainScene;
import com.google.common.collect.Maps;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Map;

public final class GuiManager {

    private static Stage stage;

    private static Scene current;

    private static final Map<String, IScene> scenes = Maps.newHashMap();

    private static IScene DEFAULT;

    public static void init(Stage stage) {
        if (GuiManager.stage != null) {
            throw new IllegalStateException("GuiManager has already been initialized!");
        }

        GuiManager.stage = stage;

        DEFAULT = registerScene(new MainScene());

        current = DEFAULT.toJFXScene();

        stage.setScene(current);
        stage.setTitle("Dragon Bot v" + Main.VERSION);
//        stage.getIcons().add(FileSystem.getResource("our/icon")) TODO: Commission/create an icon for the bot.
        stage.show();
    }

    public void setScene(String key) {
        setScene(scenes.get(key));
    }

    public void setScene(IScene scene) {
        stage.setScene(scene.toJFXScene());

    }

    public static IScene registerScene(IScene scene) {
        scenes.put(scene.getName(), scene);
        scene.init();

        return scene;
    }

    public static void setCurrentToDefault() {
        current = DEFAULT.toJFXScene();
    }

    public static IScene getDefaultScene() {
        return DEFAULT;
    }
}
