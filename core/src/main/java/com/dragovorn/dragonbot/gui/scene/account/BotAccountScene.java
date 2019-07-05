package com.dragovorn.dragonbot.gui.scene.account;

import com.dragovorn.dragonbot.DragonBot;
import com.dragovorn.dragonbot.api.config.IConfiguration;
import com.dragovorn.dragonbot.api.gui.scene.AbstractScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public final class BotAccountScene extends AbstractScene {

    private static final String LOGIN = "Login with Twitch";
    private static final String LOGOUT = "Logout %s";

    @FXML private Button button;

    private Stage login;

    private IConfiguration configuration = null;

    @Override
    public void onShow() {
        updateButtons();
    }

    @FXML
    private void handleLoginWithTwitch(ActionEvent actionEvent) {
        this.login = DragonBot.getInstance().getGuiManager().createSubStage("Login to your Twitch bot account!", AccountLoginScene.class);
    }

    private void handleLogout(ActionEvent actionEvent) {
        String username = this.configuration.get("account.username");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to logout " + username + "?");

        alert.showAndWait()
                .filter(r -> r == ButtonType.OK)
                .ifPresent(r -> {
                    System.out.println("Logout " + username);
                    this.configuration.set("account.username", "");
                    this.configuration.set("account.oauth", "");
                });

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

    public Stage getLogin() {
        return this.login;
    }

    private void updateButtons() {
        if (this.configuration == null) {
            this.configuration = DragonBot.getInstance().getConfiguration();
        }

        String username = this.configuration.get("account.username");

        if (username.equals("") || this.configuration.get("account.oauth").equals("")) {
            this.button.setText(LOGIN);
            this.button.setOnAction(this::handleLoginWithTwitch);

            return;
        }

        this.button.setText(String.format(LOGOUT, username));
        this.button.setOnAction(this::handleLogout);
    }
}
