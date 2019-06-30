package com.dragovorn.dragonbot.api.gui.scene;

import com.dragovorn.dragonbot.api.file.Resources;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public abstract class AbstractScene implements IScene {

    protected Parent parent;

    protected final String name;
    protected final String fileName;

    private Scene scene;

    public AbstractScene(String name, String fileName) {
        this.name = name;
        this.fileName = fileName;
    }

    @Override
    public void init() {
        try {
            this.parent = FXMLLoader.load(Resources.getResource("fxml/" + this.fileName + ".fxml"));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
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
        return (this.scene == null ? this.scene = new Scene(getParent(), getWidth(), getHeight()) : this.scene);
    }
}
