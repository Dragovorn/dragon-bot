package com.dragovorn.dragonbot.manager;

import com.dragovorn.dragonbot.DragonBot;
import com.dragovorn.dragonbot.api.gui.IGuiManager;
import com.dragovorn.dragonbot.api.gui.scene.IScene;
import com.dragovorn.dragonbot.gui.scene.Scenes;
import com.google.common.collect.Maps;
import javafx.stage.Stage;

import java.util.Map;

public final class GuiManager implements IGuiManager {

    private Stage stage;

    private IScene current;

    private final Map<String, IScene> scenes = Maps.newHashMap();

    private boolean init = false;

    public GuiManager(Stage stage) {
        this.stage = stage;

        stage.setResizable(false);
        stage.setOnCloseRequest((event -> DragonBot.getInstance().shutdown()));
//        stage.getIcons().add(FileSystem.getResource("our/icon")) TODO: Commission/create an icon for the bot.
    }

    @Override
    public void init() {
        if (this.init) {
            throw new IllegalStateException("GuiManager has already been initialized!");
        }

        this.current = Scenes.MAIN;

        this.stage.setScene(this.current.toJFXScene());
        this.stage.setTitle("Dragon Bot v" + DragonBot.getInstance().getVersion());
        this.stage.show();

        this.init = true;
    }

    @Override
    public void useScene(String key) {
        IScene scene = getScene(key);

        if (scene == null) {
            System.err.println(key + " isn't a registered scene!");
            return;
        }

        useScene(scene);
    }

    @Override
    public void useScene(IScene scene) {
        this.current = scene;
        this.stage.setScene(scene.toJFXScene());
    }

    @Override
    public void setToDefaultScene() {
        useScene(getDefaultScene());
    }

    @Override
    public IScene getDefaultScene() {
        return Scenes.MAIN;
    }

    @Override
    public IScene getScene(String name) {
        return this.scenes.get(name);
    }

    @Override
    public IScene registerScene(IScene scene) {
        this.scenes.put(scene.getName(), scene);
        scene.init();

        return scene;
    }
}
