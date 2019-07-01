package com.dragovorn.dragonbot.manager;

import com.dragovorn.dragonbot.DragonBot;
import com.dragovorn.dragonbot.api.file.Resources;
import com.dragovorn.dragonbot.api.gui.IGuiManager;
import com.dragovorn.dragonbot.api.gui.scene.IScene;
import com.dragovorn.dragonbot.gui.scene.Scenes;
import com.google.common.collect.Maps;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public final class GuiManager implements IGuiManager {

    private Stage stage;

    private IScene current;

    private final Map<String, IScene> scenes = Maps.newHashMap();

    private final Map<String, String> expectingFXML = Maps.newHashMap();
    private final Map<String, String> expectingName = Maps.newHashMap();
    private final Map<String, Parent> expectingParent = Maps.newHashMap();

    private boolean init = false;

    public GuiManager(Stage stage) {
        this.stage = stage;

        stage.setResizable(false);
        stage.setOnCloseRequest((event -> DragonBot.getInstance().shutdown()));

        try {
            stage.getIcons().add(new Image(Resources.getResource("icon.png").openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        if (this.init) {
            throw new IllegalStateException("GuiManager has already been initialized!");
        }

        this.current = Scenes.MAIN;

        this.scenes.forEach((key, value) -> value.setParent(this.expectingParent.remove(key)));

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
        scene.onShow();
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
    public Future<IScene> getFutureScene(String name) {
        if (!this.expectingFXML.containsValue(name) && !this.scenes.containsKey(name)) {
            throw new IllegalStateException(name + " hasn't even been registered!");
        }

        return CompletableFuture.supplyAsync(() -> {
            if (this.expectingFXML.containsValue(name)) {
                try {
                    this.expectingName.get(name).wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return this.scenes.get(name);
        });
    }

    @Override
    public void registerScene(String name, String fxmlPath) {
        try {
            this.expectingFXML.put(fxmlPath, name);
            this.expectingName.put(name, fxmlPath);
            this.expectingParent.put(name, FXMLLoader.load(Resources.getResource("fxml/" + fxmlPath + ".fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerScene(IScene scene, String fxmlPath) {
        String name = this.expectingFXML.get(fxmlPath);

        this.scenes.put(name, scene);
        this.expectingFXML.remove(fxmlPath);

        String lockedName = this.expectingName.remove(name);

        synchronized (lockedName) {
            lockedName.notifyAll();
        }
    }

    @Override
    public void registerScene(String name) {
        registerScene(name, name);
    }
}
