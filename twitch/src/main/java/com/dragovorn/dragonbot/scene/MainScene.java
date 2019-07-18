package com.dragovorn.dragonbot.scene;

import com.dragovorn.dragonbot.scene.account.BotAccountScene;
import com.dragovorn.ircbot.impl.gui.fxml.AbstractFXMLScene;
import javafx.fxml.FXML;

public final class MainScene extends AbstractFXMLScene {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

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
        guiManager.useScene(BotAccountScene.class);
    }

    @FXML
    public void handleCustomCommandButton() {
//        DragonBot.getInstance().getGuiManager().useScene(Scenes.ADVANCED_OPTIONS);
    }

    @FXML
    public void handleExitButton() {
        System.exit(0);
    }

    @FXML
    public void handleAdvancedButton() {
        guiManager.useScene(AdvancedOptionsScene.class);
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
