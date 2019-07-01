package com.dragovorn.dragonbot.manager;

import com.dragovorn.dragonbot.DragonBot;
import com.dragovorn.dragonbot.api.file.Resources;
import com.dragovorn.dragonbot.api.gui.IGuiManager;
import com.dragovorn.dragonbot.api.gui.scene.IScene;
import com.dragovorn.dragonbot.gui.scene.MainScene;
import com.google.common.collect.Maps;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public final class GuiManager implements IGuiManager {

    private Stage stage;

    private IScene current;

    private final Map<Class<? extends IScene>, IScene> scenes = Maps.newHashMap();
    private final Map<String, Class<? extends IScene>> recentlyLoaded = Maps.newHashMap();

    private boolean init = false;

    public GuiManager(Stage stage) {
        this.stage = stage;

        this.stage.setResizable(false);
        this.stage.setOnCloseRequest((event -> DragonBot.getInstance().shutdown()));

        try {
            this.stage.getIcons().add(new Image(Resources.getResource("icon.png").openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        if (this.init) {
            throw new IllegalStateException("GuiManager has already been initialized!");
        }

        this.stage.setTitle("Dragon Bot v" + DragonBot.getInstance().getVersion());
        this.stage.show();

        this.init = true;
    }

    @Override
    public void useScene(IScene scene) {
        this.current = scene;
        this.stage.setScene(scene.toJFXScene());
        scene.onShow();
    }

    @Override
    public void setToDefaultScene() {
        useScene(getDefaultScene());
    }

    @Override
    public void useScene(Class<? extends IScene> clazz) {
        useScene(getScene(clazz));
    }

    @Override
    public void registerScene(IScene scene, String fxmlPath) {
        this.scenes.put(scene.getClass(), scene);
        this.recentlyLoaded.put(fxmlPath, scene.getClass());
    }

    @Override
    public IScene getDefaultScene() {
        return getScene(MainScene.class);
    }

    @Override
    public IScene getCurrentScene() {
        return this.current;
    }

    @Override
    public IScene getScene(Class<? extends IScene> clazz) {
        return this.scenes.get(clazz);
    }

    @Override
    public IScene registerScene(String fxmlPath) throws IOException {
        // This NEEDS to be written like this because scenes and recentlyLoaded will return null otherwise.
        Parent parent = FXMLLoader.load(Resources.getResource("fxml/" + fxmlPath + ".fxml"));

        IScene result = this.scenes.get(this.recentlyLoaded.remove(fxmlPath));
        result.setParent(parent);

        return result;
    }
}
