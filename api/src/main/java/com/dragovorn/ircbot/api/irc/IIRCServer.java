package com.dragovorn.ircbot.api.irc;

import java.io.BufferedReader;
import java.io.IOException;

public interface IIRCServer {

    void connect() throws IOException;
    void disconnect() throws IOException;

    int getPort();

    boolean isConnected();
    boolean handleConnection(BufferedReader reader) throws IOException;

    String getIP();

    IConnection getConnection();
}
