package com.dragovorn.dragonbot.gui.scene;

import com.dragovorn.dragonbot.api.gui.scene.AbstractScene;
import javafx.fxml.FXML;

public final class AdvancedOptionsScene extends AbstractScene {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    @FXML
    public void handleBackButton() {
        guiManager.setToDefaultScene();
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
