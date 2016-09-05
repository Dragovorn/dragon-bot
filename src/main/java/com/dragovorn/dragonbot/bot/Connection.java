/*
 * Copyright (c) 2016. Andrew Burr
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 *  associated documentation files (the "Software"), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge, publish, distribute,
 *  sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.dragovorn.dragonbot.bot;

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