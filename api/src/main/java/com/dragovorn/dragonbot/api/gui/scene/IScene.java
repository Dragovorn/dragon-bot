package com.dragovorn.dragonbot.api.gui.scene;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

public interface IScene extends Initializable {

    default void initialize() { }

    /**
     * Executes when the scene is shown.
     */
    default void onShow() { }

    /**
     * Executes when the scene is hidden.
     */
    default void onHide() { }

    void setParent(Parent parent);

    int getWidth();
    int getHeight();

    Parent getParent();

    Scene toJFXScene();
}
