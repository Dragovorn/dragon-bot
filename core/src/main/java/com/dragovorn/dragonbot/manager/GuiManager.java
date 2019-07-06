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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public final class GuiManager implements IGuiManager {

    private Stage stage;

    private final Map<Class<? extends IScene>, IScene> scenes = Maps.newHashMap();
    private final Map<String, Class<? extends IScene>> recentlyLoaded = Maps.newHashMap();

    private final Map<Stage, IScene> currentScenes = Maps.newHashMap();

    private Image icon;

    private boolean init = false;

    public GuiManager(Stage stage) {
        this.stage = stage;
        try {
            this.icon = new Image(Resources.getResource("icon.png").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.stage.setResizable(false);
        this.stage.setOnCloseRequest((event -> System.exit(0)));
        this.stage.getIcons().add(this.icon);
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
    public void close(Stage stage) {
        stage.getOnCloseRequest().handle(null); // We need this because close bypasses the close event.
        stage.close();
    }

    @Override
    public void useScene(IScene scene) {
        useScene(scene, this.stage);
    }

    @Override
    public void useScene(IScene scene, Stage stage) {
        IScene previous = this.currentScenes.get(stage);

        if (previous != null) {
            previous.onHide();
        }

        if (!this.stage.equals(stage)) { // If the stage isn't the main stage set the close event to onHide
            stage.setOnCloseRequest((event -> scene.onHide()));
        }

        stage.setScene(scene.toJFXScene());
        scene.onShow();

        this.currentScenes.put(stage, scene);
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
    public void useScene(Class<? extends IScene> clazz, Stage stage) {
        useScene(getScene(clazz), stage);
    }

    @Override
    public IScene getDefaultScene() {
        return getScene(MainScene.class);
    }

    @Override
    public IScene getCurrentScene() {
        return getCurrentScene(this.stage);
    }

    @Override
    public IScene getCurrentScene(Stage stage) {
        return this.currentScenes.get(stage);
    }

    @Override
    public <T extends IScene> T getScene(Class<T> clazz) {
        return (T) this.scenes.get(clazz);
    }

    @Override
    public Stage getStage() {
        return this.stage;
    }

    @Override
    public Stage createSubStage(String title, IScene firstScene) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.stage);
        stage.getIcons().add(this.icon);
        stage.setTitle(title);
        stage.setResizable(false);
        useScene(firstScene, stage);
        stage.show();

        return stage;
    }

    @Override
    public Stage createSubStage(String title, Class<? extends IScene> firstScene) {
        return createSubStage(title, getScene(firstScene));
    }

    @Override
    public Image getIcon() {
        return this.icon;
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
