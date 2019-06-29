package com.dragovorn.dragonbot.gui;

import com.dragovorn.dragonbot.DragonBot;
import com.dragovorn.dragonbot.api.gui.scene.AbstractScene;
import javafx.fxml.FXML;

public final class OptionsScene extends AbstractScene {

    public OptionsScene() {
        super("options", "options");
    }

    @FXML
    public void initialize() {
        System.out.println("Initialize options!");
    }

    @FXML
    public void handleBackButton() {
        DragonBot.getInstance().getGuiManager().setToDefaultScene();
    }

    @Override
    public int getWidth() {
        return 100;
    }

    @Override
    public int getHeight() {
        return 100;
    }
}
