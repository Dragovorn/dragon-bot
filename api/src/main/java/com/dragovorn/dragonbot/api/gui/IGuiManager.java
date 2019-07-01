package com.dragovorn.dragonbot.api.gui;

import com.dragovorn.dragonbot.api.gui.scene.IScene;

import java.util.concurrent.Future;

public interface IGuiManager {

    void init();
    void setToDefaultScene();
    void useScene(String name);
    void useScene(IScene scene);
    void registerScene(String name);
    void registerScene(String name, String fxmlPath);
    void registerScene(IScene scene, String fxmlPath);

    IScene getDefaultScene();
    IScene getScene(String name);

    Future<IScene> getFutureScene(String name);
}
