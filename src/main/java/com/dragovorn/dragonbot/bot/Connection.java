package com.dragovorn.dragonbot.bot;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 3:26 AM.
 * as of 8/6/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class Connection {

    private String server;
    private String password;
    private String channel;

    private boolean SSL = false;
    private boolean verifySSL = false;

    private int port;

    public Connection(String server) {
        this(server, 6667, "", "", false, false);
    }

    public Connection(String server, int port, String password) {
        this(server, port, password, "", false, false);
    }

    public Connection(String server, int port, String password, String channel, boolean SSL, boolean verifySSL) {
        this.server = server;
        this.port = port;
        this.password = password;
        this.channel = channel;
        this.SSL = SSL;
        this.verifySSL = verifySSL;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setSSL(boolean SSL) {
        this.SSL = SSL;
    }

    public void setVerifySSL(boolean verifySSL) {
        this.verifySSL = verifySSL;
    }

    public String getServer() {
        return this.server;
    }

    public String getPassword() {
        return this.password;
    }

    public String getChannel() {
        return this.channel;
    }

    public boolean useSSL() {
        return this.SSL;
    }

    public boolean verifySSL() {
        return this.verifySSL;
    }

    public int getPort() {
        return this.port;
    }
}