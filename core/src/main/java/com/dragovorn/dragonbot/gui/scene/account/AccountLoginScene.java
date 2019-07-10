package com.dragovorn.dragonbot.gui.scene.account;

import com.dragovorn.dragonbot.DragonBot;
import com.dragovorn.dragonbot.api.gui.scene.AbstractScene;
import fi.iki.elonen.NanoHTTPD;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;

public final class AccountLoginScene extends AbstractScene {

    private static final int PORT = 9502;

    private static final String LINK = "https://id.twitch.tv/oauth2/authorize" +
            "?client_id=" + DragonBot.CLIENT_ID +
            "&redirect_uri=http://localhost:" + PORT +
            "&response_type=token" +
            "&scope=chat:edit";

    private OAuthWebServer server;

    private BotAccountScene botAccountScene = guiManager.getScene(BotAccountScene.class);

    @FXML private WebView webView;

    @Override
    public void initialize() {
        this.server = new OAuthWebServer(PORT);

        WebEngine webEngine = this.webView.getEngine();

        webEngine.load(LINK);
        webEngine.setJavaScriptEnabled(true);
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (Worker.State.SUCCEEDED.equals(newValue)) {
                String location = webEngine.getLocation();

                if (location.startsWith("http://localhost")) {
                    parseURL(location);
                }
            }
        });
    }

    @Override
    public void onShow() {
        System.out.println("Launching OAuth listening web server.");

        try {
            this.server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onHide() {
        System.out.println("Stopping OAuth listening web server.");

        this.server.stop();
    }

    @Override
    public int getWidth() {
        return 450;
    }

    @Override
    public int getHeight() {
        return 500;
    }

    private void parseURL(String url) {
        url = url.substring(url.lastIndexOf("/") + 2);
        String[] split = url.split("&");

        String token = split[0].split("=")[1];

        System.out.println("Successfully got token!");

        CheckingAccountScene scene = guiManager.getScene(CheckingAccountScene.class);

        guiManager.useScene(scene, this.botAccountScene.getLogin());

        scene.convertTokenToUsername(token);
    }

    private final class OAuthWebServer extends NanoHTTPD {

        OAuthWebServer(int port) {
            super(port);
        }

        @Override
        public NanoHTTPD.Response serve(IHTTPSession session) {
            return newFixedLengthResponse("<html></html>");
        }
    }
}
