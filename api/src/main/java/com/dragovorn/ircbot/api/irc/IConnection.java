package com.dragovorn.ircbot.api.irc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public interface IConnection {

    void connect() throws IOException;
    void disconnect() throws IOException;
    void sendRawLine(String line) throws IOException;
    void sendMessageToAll(String line);
    void joinChannel(String name);
    void joinChannel(IChannel channel);
    void leaveChannel(String name);
    void leaveChannel(IChannel channel);
    void sendMessage(IChannel channel, String line);
    void sendMessage(String name, String line);

    IChannel getChannel(String name);

    IIRCServer getIRCServer();

    List<IChannel> getChannels();

    Socket getSocket();

    BufferedReader getReader();
    BufferedWriter getWriter();
}
