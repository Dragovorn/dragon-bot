package com.dragovorn.dragonbot;

import com.dragovorn.dragonbot.api.gui.scene.IScene;
import com.dragovorn.dragonbot.manager.GuiManager;
import com.dragovorn.dragonbot.api.util.FileSystem;
import com.google.common.collect.Maps;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class DragonBotApplication extends Application {

    public static void main(String... args) {
        launch();
    }

    private static DragonBotApplication instance;

    private static Map<String, Parent> parents = Maps.newHashMap();

    private static Parent DEFAULT_PARENT;

    private static Scene scene;

    private static Stage stage;

    public static final String VERSION;

    static {
        Properties properties = new Properties();

        try {
            properties.load(FileSystem.getResource("project.properties").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        VERSION = properties.getProperty("version");
    }

    @Override
    public void start(Stage stage) {
        instance = this;

        GuiManager.init(stage);
    }

    public static Parent getParent(String name) {
        return parents.getOrDefault(name, DEFAULT_PARENT);
    }

    public static void updateParent(String name) {
        scene.setRoot(getParent(name));
    }

    public static void registerScene(IScene scene) {
        try {
            parents.put(scene.getName(), FXMLLoader.load(FileSystem.getResource("fxml/" + scene.getFileName() + ".fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DragonBotApplication getInstance() {
        return instance;
    }

    public Scene getScene() {
        return scene;
    }
}
