package com.dragovorn.ircbot.impl.irc;

import com.dragovorn.ircbot.api.irc.IChannel;
import com.dragovorn.ircbot.api.irc.IConnection;
import com.dragovorn.ircbot.api.irc.IIRCServer;
import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class Connection implements IConnection {

    private final List<IChannel> activeChannels = Lists.newArrayList();

    private final Socket socket;

    private final IIRCServer server;

    public Connection(IIRCServer server) throws IOException {
        this.server = server;
        this.socket = new Socket(server.getIP(), server.getPort());
    }

    @Override
    public void sendRawLine(String line) {

    }

    @Override
    public void sendMessageToAll(String line) {
        this.activeChannels.forEach((c -> c.sendMessage(line)));
    }

    @Override
    public void joinChannel(IChannel channel) {
        this.activeChannels.add(channel);
        channel.join();
    }

    @Override
    public void leaveChannel(IChannel channel) {
        this.activeChannels.remove(channel);
    }

    @Override
    public void sendMessage(IChannel channel, String line) {
        channel.sendMessage(line);
    }

    @Override
    public IIRCServer getIRCServer() {
        return this.server;
    }

    @Override
    public List<IChannel> getChannels() {
        return this.activeChannels;
    }

    @Override
    public Socket getSocket() {
        return this.socket;
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
