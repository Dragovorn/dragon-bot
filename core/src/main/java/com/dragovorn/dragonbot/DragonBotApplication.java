package com.dragovorn.dragonbot;

import com.dragovorn.dragonbot.util.FileSystem;
import com.google.common.collect.Maps;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class DragonBotApplication extends Application {

    public static void main(String... args) {
        launch();
    }

    private static DragonBotApplication instance;

    private static Map<String, Parent> parents = Maps.newHashMap();

    private static Parent DEFAULT_PARENT;

    private static Scene scene;

    private static Stage stage;

    public static final int DEFAULT_WIDTH = 500;
    public static final int DEFAULT_HEIGHT = 500;

    @Override
    public void start(Stage paramStage) {
        instance = this;

        registerParent("main", "main");

        DEFAULT_PARENT = getParent("main");

        scene = new Scene(DEFAULT_PARENT, 500, 500);
        stage.setScene(scene);
        stage.show();

        stage = paramStage;
    }

    public static Parent getParent(String name) {
        return parents.getOrDefault(name, DEFAULT_PARENT);
    }

    public static void resizeScene(int width, int height) {
        if (width <= 0) {
            width = DEFAULT_WIDTH;
        }

        if (height <= 0) {
            height = DEFAULT_HEIGHT;
        }

        stage.setScene(new Scene(scene.getRoot(), width, height));
    }

    public static void updateParent(String name) {
        scene.setRoot(getParent(name));
    }

    public static void registerParent(String name, String fxmlName) {
        try {
            parents.put(name, FXMLLoader.load(FileSystem.getResource("fxml/" + fxmlName + ".fxml")));
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
