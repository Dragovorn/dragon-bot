package com.dragovorn.dragonbot.api.gui;

import com.dragovorn.dragonbot.api.gui.scene.IScene;

import java.io.IOException;

public interface IGuiManager {

    void init();
    void setToDefaultScene();
    void useScene(Class<? extends IScene> clazz);
    void useScene(IScene scene);
    void registerScene(IScene scene, String fxmlPath);

    IScene registerScene(String fxmlPath) throws IOException;
    IScene getDefaultScene();
    IScene getCurrentScene();
    IScene getScene(Class<? extends IScene> clazz);
}
