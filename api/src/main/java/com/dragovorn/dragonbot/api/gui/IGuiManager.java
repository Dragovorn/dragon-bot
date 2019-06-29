package com.dragovorn.dragonbot.api.gui;

import com.dragovorn.dragonbot.api.gui.scene.IScene;

public interface IGuiManager {

    void init();
    void useScene(String name);
    void useScene(IScene scene);
    void setToDefaultScene();

    IScene getDefaultScene();
    IScene getScene(String name);
    IScene registerScene(IScene scene);
}
