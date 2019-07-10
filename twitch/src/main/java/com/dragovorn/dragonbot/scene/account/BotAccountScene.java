package com.dragovorn.dragonbot.scene.account;

import com.dragovorn.dragonbot.DragonBot;
import com.dragovorn.ircbot.impl.gui.fxml.AbstractFXMLScene;
import com.dragovorn.dragonbot.api.web.ITwitchAPI;
import com.dragovorn.ircbot.impl.user.BotAccount;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public final class BotAccountScene extends AbstractFXMLScene {

    private static final String LOGIN = "Login with Twitch";
    private static final String LOGOUT = "Logout %s";

    @FXML private Button button;

    private Stage login;

    private BotAccount account = null;

    @Override
    public void onShow() {
        updateButtons();
    }

    @FXML
    private void handleLoginWithTwitch(ActionEvent actionEvent) {
        this.login = guiManager.createSubStage("Login to your Twitch bot account!", AccountLoginScene.class);
    }

    private void handleLogout(ActionEvent actionEvent) {
        String username = this.account.getUsername();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to logout " + username + "?");

        alert.showAndWait()
                .filter(r -> r == ButtonType.OK)
                .ifPresent(r -> {
                    try {
                        apiManager.getAPI(ITwitchAPI.class).invalidateToken(this.account.getPassword());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Logout " + username);
                    this.account.setUsername("");
                    this.account.setPassword("");
                    ((DragonBot) DragonBot.getInstance()).getConfiguration().set(this.account);
                });

        updateButtons();
    }

    @FXML
    public void handleBackButton() {
        guiManager.setToDefaultScene();
    }

    @Override
    public int getWidth() {
        return 200;
    }

    @Override
    public int getHeight() {
        return 100;
    }

    Stage getLogin() {
        return this.login;
    }

    void updateButtons() {
        if (this.account == null) {
            this.account = (BotAccount) DragonBot.getInstance().getAccount();
        }

        String username = this.account.getUsername();

        if (username.equals("") || this.account.getPassword().equals("")) {
            this.button.setText(LOGIN);
            this.button.setOnAction(this::handleLoginWithTwitch);

            return;
        }

        this.button.setText(String.format(LOGOUT, username));
        this.button.setOnAction(this::handleLogout);
    }
}
