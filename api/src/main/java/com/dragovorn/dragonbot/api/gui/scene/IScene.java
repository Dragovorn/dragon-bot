package com.dragovorn.dragonbot.api.gui.scene;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public interface IScene {

    void update(Stage stage);

    int getWidth();
    int getHeight();

    String getName();
    String getFileName();

    Parent getParent();

    Scene toScene();
}
