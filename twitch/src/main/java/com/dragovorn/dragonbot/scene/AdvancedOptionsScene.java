package com.dragovorn.dragonbot.scene;

import com.dragovorn.ircbot.impl.gui.fxml.AbstractFXMLScene;
import javafx.fxml.FXML;

public final class AdvancedOptionsScene extends AbstractFXMLScene {

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
