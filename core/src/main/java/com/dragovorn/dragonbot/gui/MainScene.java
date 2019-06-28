package com.dragovorn.dragonbot.gui;

import com.dragovorn.dragonbot.api.gui.scene.AbstractScene;
import javafx.fxml.FXML;

public final class MainScene extends AbstractScene {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    public MainScene() {
        super("main", "main");
    }

    @FXML
    public void initialize() {
        System.out.println("Initialize!");
    }

    @Override
    public int getWidth() {
        return 500;
    }

    @Override
    public int getHeight() {
        return 500;
    }
}
