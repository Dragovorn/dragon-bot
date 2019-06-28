package com.dragovorn.dragonbot;

import com.dragovorn.dragonbot.api.util.FileSystem;
import com.dragovorn.dragonbot.manager.GuiManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;

public class Main extends Application {

    public static void main(String... args) {
        launch();
    }

    // TODO: Move this to another class later
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
        GuiManager.init(stage);
    }
}
