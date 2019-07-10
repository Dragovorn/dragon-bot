package com.dragovorn.ircbot.api.gui;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public interface IGuiManager {

    void init();
    void close(Stage stage);
    void setToDefaultScene();
    void useScene(Class<? extends IScene> clazz);
    void useScene(IScene scene);
    void useScene(IScene scene, Stage stage);
    void registerScene(IScene scene, String fxmlPath);
    void useScene(Class<? extends IScene> clazz, Stage stage);

    boolean isInitialized();

    IScene registerScene(String fxmlPath) throws IOException;
    IScene getDefaultScene();
    IScene getCurrentScene();
    IScene getCurrentScene(Stage stage);
    <T extends IScene> T getScene(Class<T> clazz);

    Stage getStage();
    Stage createSubStage(String title, IScene firstScene);
    Stage createSubStage(String title, Class<? extends IScene> firstScene);

    Image getIcon();
}
