package com.dragovorn.dragonbot.gui.scene.account;

import fi.iki.elonen.NanoHTTPD;

public final class OAuthWebServer extends NanoHTTPD {

    OAuthWebServer(int port) {
        super(port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        System.out.println("Got response!");

        return newFixedLengthResponse("<html></html>");
    }
}
