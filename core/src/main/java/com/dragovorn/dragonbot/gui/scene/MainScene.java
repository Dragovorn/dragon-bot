package com.dragovorn.dragonbot.gui.scene;

import com.dragovorn.dragonbot.DragonBot;
import com.dragovorn.dragonbot.api.gui.scene.AbstractScene;
import javafx.fxml.FXML;

public final class MainScene extends AbstractScene {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    // This needs to be public because FXML needs to be able to invoke it
    public MainScene() {
        super("main", "main");
    }

    @FXML
    public void handleChannelsButton() {
//        DragonBot.getInstance().getGuiManager().useScene(Scenes.ADVANCED_OPTIONS);
    }

    @FXML
    public void handlePluginsButton() {
//        DragonBot.getInstance().getGuiManager().useScene(Scenes.ADVANCED_OPTIONS);
    }

    @FXML
    public void handleBotAccountButton() {
        DragonBot.getInstance().getGuiManager().useScene(Scenes.BOT_ACCOUNT);
    }

    @FXML
    public void handleCustomCommandButton() {
//        DragonBot.getInstance().getGuiManager().useScene(Scenes.ADVANCED_OPTIONS);
    }

    @FXML
    public void handleExitButton() {
        DragonBot.getInstance().shutdown();
    }

    @FXML
    public void handleAdvancedButton() {
        DragonBot.getInstance().getGuiManager().useScene(Scenes.ADVANCED_OPTIONS);
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }
}
