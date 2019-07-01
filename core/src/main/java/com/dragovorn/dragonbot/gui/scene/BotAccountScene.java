package com.dragovorn.dragonbot.gui.scene;

import com.dragovorn.dragonbot.DragonBot;
import com.dragovorn.dragonbot.api.config.IConfiguration;
import com.dragovorn.dragonbot.api.gui.scene.AbstractScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public final class BotAccountScene extends AbstractScene {

    @FXML private Button button;

    private IConfiguration configuration = null;

    @Override
    public void onShow() {
        updateButtons();
    }

    @FXML
    private void handleLoginWithTwitch(ActionEvent actionEvent) {
        updateButtons();

        System.out.println("LOGIN BUTTON PRESS");

        this.configuration.set("account.username", "Testovorn");
        this.configuration.set("account.oauth", "Oauth");

        updateButtons();
    }

    private void handleLogout(ActionEvent actionEvent) {
        updateButtons();

        System.out.println("LOGOUT");

        this.configuration.set("account.username", "");
        this.configuration.set("account.oauth", "");

        updateButtons();
    }

    @FXML
    public void handleBackButton() {
        DragonBot.getInstance().getGuiManager().setToDefaultScene();
    }

    @Override
    public int getWidth() {
        return 200;
    }

    @Override
    public int getHeight() {
        return 100;
    }

    public void updateButtons() {
        if (this.configuration == null) {
            this.configuration = DragonBot.getInstance().getConfiguration();
        }

        String username = this.configuration.get("account.username");

        if (username.equals("") || this.configuration.get("account.oauth").equals("")) {
            this.button.setText("Log in with Twitch");
            this.button.setOnAction(this::handleLoginWithTwitch);

            return;
        }

        this.button.setText("Logout " + this.configuration.get("account.username"));
        this.button.setOnAction(this::handleLogout);
    }
}
