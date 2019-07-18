package com.dragovorn.ircbot.impl.gui.fxml;

import com.dragovorn.ircbot.impl.bot.AbstractIRCBot;
import com.dragovorn.ircbot.api.file.Resources;
import com.dragovorn.ircbot.api.gui.IGuiManager;
import com.dragovorn.ircbot.api.gui.IScene;
import com.google.common.collect.Maps;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class FXMLGuiManager implements IGuiManager {

    private Stage stage;

    private final Map<Class<? extends AbstractFXMLScene>, IScene> scenes = Maps.newHashMap();
    private final Map<String, Class<? extends AbstractFXMLScene>> recentlyLoaded = Maps.newHashMap();

    private final Map<Stage, IScene> currentScenes = Maps.newHashMap();

    private Image icon;

    private boolean init = false;

    public FXMLGuiManager(Stage stage) {
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
            throw new IllegalStateException("FXMLGuiManager has already been initialized!");
        }

        this.stage.setTitle(AbstractIRCBot.getInstance().getName() + " v" + AbstractIRCBot.getInstance().getVersion());
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
        if (!(scene instanceof AbstractFXMLScene)) {
            throw new IllegalArgumentException("Cannot use non-AbstractFXMLScene scenes with FXMLGuiManager!");
        }

        IScene previous = this.currentScenes.get(stage);

        if (previous != null) {
            previous.onHide();
        }

        if (!this.stage.equals(stage)) { // If the stage isn't the main stage set the close event to onHide
            stage.setOnCloseRequest((event -> scene.onHide()));
        }

        stage.setScene(((AbstractFXMLScene) scene).toJFXScene());
        scene.onShow();

        this.currentScenes.put(stage, scene);
    }

    @Override
    public void useScene(Class<? extends IScene> clazz) {
        useScene(getScene(clazz));
    }

    @Override
    public void registerScene(IScene scene, String fxmlPath) {
        if (!(scene instanceof AbstractFXMLScene)) {
            throw new IllegalArgumentException("Scene has to extend AbstractFXMLScene!");
        }

        this.scenes.put(((AbstractFXMLScene) scene).getClass(), scene);
        this.recentlyLoaded.put(fxmlPath, ((AbstractFXMLScene) scene).getClass());
    }

    @Override
    public void useScene(Class<? extends IScene> clazz, Stage stage) {
        useScene(getScene(clazz), stage);
    }

    @Override
    public void alert(String alert) {
        new Alert(Alert.AlertType.ERROR, alert).show();
    }

    @Override
    public boolean isInitialized() {
        return this.init;
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
