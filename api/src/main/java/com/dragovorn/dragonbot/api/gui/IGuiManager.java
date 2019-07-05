package com.dragovorn.dragonbot.api.gui;

import com.dragovorn.dragonbot.api.gui.scene.IScene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public interface IGuiManager {

    void init();
    void setToDefaultScene();
    void useScene(Class<? extends IScene> clazz);
    void useScene(IScene scene);
    void useScene(IScene scene, Stage stage);
    void registerScene(IScene scene, String fxmlPath);
    void useScene(Class<? extends IScene> clazz, Stage stage);

    IScene registerScene(String fxmlPath) throws IOException;
    IScene getDefaultScene();
    IScene getCurrentScene();
    IScene getScene(Class<? extends IScene> clazz);

    Stage getStage();
    Stage createSubStage(String title, IScene firstScene);
    Stage createSubStage(String title, Class<? extends IScene> firstScene);

    Image getIcon();
}
