package com.dragovorn.dragonbot.api.gui.scene;

import com.dragovorn.dragonbot.api.util.FileSystem;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class AbstractScene implements IScene {

    protected Parent parent;

    protected final String name;
    protected final String fileName;

    public AbstractScene(String name, String fileName) {
        this.name = name;
        this.fileName = fileName;
    }

    @Override
    public void init() {
        try {
            this.parent = FXMLLoader.load(FileSystem.getResource("fxml/" + this.fileName + ".fxml"));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void update(Stage stage) {
        stage.setScene(this.toJFXScene()); // Forces an update of the scene
    }

    @Override
    public Parent getParent() {
        return this.parent;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public Scene toJFXScene() {
        return new Scene(getParent(), getWidth(), getHeight());
    }
}
