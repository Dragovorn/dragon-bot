package com.dragovorn.dragonbot.gui.scene;

import com.dragovorn.dragonbot.DragonBot;
import com.dragovorn.dragonbot.api.config.IConfiguration;
import com.dragovorn.dragonbot.api.gui.scene.AbstractScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public final class BotAccountScene extends AbstractScene {

    private static final String LOGIN = "Login with Twitch";
    private static final String LOGOUT = "Logout %s";

    @FXML private Button button;

    private IConfiguration configuration = null;

    @Override
    public void onShow() {
        updateButtons();
    }

    @FXML
    private void handleLoginWithTwitch(ActionEvent actionEvent) {
        Stage login = new Stage();
        login.initModality(Modality.APPLICATION_MODAL);
        login.initOwner(DragonBot.getInstance().getGuiManager().getStage());
        VBox loginVBox = new VBox(20);
        loginVBox.getChildren().add(new Text("This is a Dialog"));
        Scene loginScene = new Scene(loginVBox, 300, 200);
        login.setScene(loginScene);
        login.show();

        // TODO: Block until the dialog box is closed.

        this.configuration.set("account.username", "Testovorn");
        this.configuration.set("account.oauth", "Oauth");

        updateButtons();
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

    public void updateButtons() {
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
