package com.dragovorn.dragonbot.bot;

import com.dragovorn.dragonbot.api.channel.IChannel;
import com.dragovorn.dragonbot.api.irc.IConnection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.util.List;

public class Connection implements IConnection {

    @Override
    public void sendRawLine(String line) {

    }

    @Override
    public void joinChannel(IChannel channel) {

    }

    @Override
    public void leaveChannel(IChannel channel) {

    }

    @Override
    public List<IChannel> getChannels() {
        return null;
    }

    @Override
    public Socket getSocket() {
        return null;
    }

    @Override
    public BufferedReader getReader() {
        return null;
    }

    @Override
    public BufferedWriter getWriter() {
        return null;
    }
}
