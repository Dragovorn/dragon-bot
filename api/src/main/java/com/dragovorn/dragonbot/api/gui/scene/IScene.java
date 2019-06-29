package com.dragovorn.dragonbot.api.gui.scene;

import javafx.scene.Parent;
import javafx.scene.Scene;

public interface IScene {

    /**
     * This method is a way to avoid running the parent loading logic in the constructor
     * this is a clever way to prevent 'recursion' of the constructor because FXML
     * constructs a new object of a controller when the parent is created.
     *
     * Meaning that there will be constructor 'recursion' if the IScene
     * loads the parent in it's constructor and it's also a controller.
     */
    void init();

    int getWidth();
    int getHeight();

    String getName();
    String getFileName();

    Parent getParent();

    Scene toJFXScene();
}
