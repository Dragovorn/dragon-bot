package com.dragovorn.dragonbot;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String... args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        new DragonBot(stage).startup();
    }
}
