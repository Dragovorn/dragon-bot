package com.dragovorn.dragonbot.gui.scene.account;

import com.dragovorn.dragonbot.DragonBot;
import com.dragovorn.dragonbot.api.gui.scene.AbstractScene;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;

public final class AccountLoginScene extends AbstractScene {

    private static final String LINK = "https://id.twitch.tv/oauth2/authorize" +
            "?client_id=" + DragonBot.CLIENT_ID +
            "&redirect_uri=http://localhost" +
            "&response_type=token" +
            "&scope=chat:edit";

    @FXML private WebView webView;

    @Override
    public void initialize() {
        this.webView.getEngine().load(LINK);
    }

    @Override
    public void onShow() {
        System.out.println("Showing AccountLoginScene");
        // TODO: Create the web-server listening for the response from the login.
    }

    @Override
    public void onHide() {
        System.out.println("Hiding AccountLoginScene");
        // TODO: Turn off the web-server listening for response from twitch login.
    }

    @Override
    public int getWidth() {
        return 450;
    }

    @Override
    public int getHeight() {
        return 500;
    }
}
