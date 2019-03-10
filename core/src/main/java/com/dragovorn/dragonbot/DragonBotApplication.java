package com.dragovorn.dragonbot;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DragonBotApplication extends Application {

    public static void main(String... args) {
        launch();
    }

    private static DragonBotApplication instance;

    private Stage stage;

    @Override
    public void start(Stage stage) {
        instance = this;

        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        Scene scene = new Scene(new StackPane(l), 640, 480);
        stage.setScene(scene);
        stage.show();

        this.stage = stage;
    }

    public static DragonBotApplication getInstance() {
        return instance;
    }

    public Stage getStage() {
        return this.stage;
    }
}
