package com.dragovorn.ircbot.api.irc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.util.List;

public interface IConnection {

    void sendRawLine(String line);
    void sendMessageToAll(String line);
    void joinChannel(IChannel channel);
    void leaveChannel(IChannel channel);
    void sendMessage(IChannel channel, String line);

    IIRCServer getIRCServer();

    List<IChannel> getChannels();

    Socket getSocket();

    BufferedReader getReader();
    BufferedWriter getWriter();
}
