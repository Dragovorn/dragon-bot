package com.dragovorn.dragonbot.gui.scene;

import com.dragovorn.dragonbot.DragonBot;
import com.dragovorn.dragonbot.api.gui.scene.AbstractScene;
import javafx.fxml.FXML;

public final class AdvancedScene extends AbstractScene {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    @FXML
    public void handleBackButton() {
        DragonBot.getInstance().getGuiManager().setToDefaultScene();
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
