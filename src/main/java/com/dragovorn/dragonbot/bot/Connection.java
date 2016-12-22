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

    // TODO make this builder based.

    private String server;
    private String password;
    private String channel;

    private boolean SSL = false;
    private boolean verifySSL = false;

    private int port;

    public static class Builder {
        private String server;
        private String password;
        private String channel;

        private boolean SSL;
        private boolean verifySSL;

        private int port;

        public Builder setServer(String server) {
            this.server = server;

            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;

            return this;
        }

        public Builder setChannel(String channel) {
            this.channel = channel;

            return this;
        }

        public Builder setSSL(boolean SSL) {
            this.SSL = SSL;

            return this;
        }

        public Builder setVerifySSL(boolean verifySSL) {
            this.verifySSL = verifySSL;

            return this;
        }

        public Builder setPort(int port) {
            this.port = port;

            return this;
        }

        public Connection build() {
            return new Connection(this);
        }
    }

    private Connection(Builder builder) {
        this.server = builder.server;
        this.password = builder.password;
        this.channel = builder.channel;
        this.SSL = builder.SSL;
        this.verifySSL = builder.verifySSL;
        this.port = builder.port;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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

    public boolean getSSL() {
        return this.SSL;
    }

    public boolean getVerifySSL() {
        return this.verifySSL;
    }

    public int getPort() {
        return this.port;
    }
}