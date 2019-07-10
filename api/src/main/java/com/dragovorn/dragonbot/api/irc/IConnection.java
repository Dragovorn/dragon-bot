package com.dragovorn.dragonbot.api.irc;

import com.dragovorn.dragonbot.api.channel.IChannel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.util.List;

public interface IConnection {

    void sendRawLine(String line);
    void joinChannel(IChannel channel);
    void leaveChannel(IChannel channel);

    List<IChannel> getChannels();

    Socket getSocket();

    BufferedReader getReader();
    BufferedWriter getWriter();
}
