package com.dragovorn.dragonbot.gui.scene.account;

import fi.iki.elonen.NanoHTTPD;

/**
 * This class exists to simply fulfill the request that the {@link javafx.scene.web.WebView} makes
 * so that we get a finish loading event in it that we can then use to parse the URL.
 */
public final class OAuthWebServer extends NanoHTTPD {

    OAuthWebServer(int port) {
        super(port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        return newFixedLengthResponse("<html></html>");
    }
}
