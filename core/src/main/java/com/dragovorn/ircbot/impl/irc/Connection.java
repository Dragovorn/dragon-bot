package com.dragovorn.ircbot.impl.irc;

import com.dragovorn.ircbot.api.event.irc.RawInputMessageEvent;
import com.dragovorn.ircbot.api.event.irc.server.ServerConnectEvent;
import com.dragovorn.ircbot.api.irc.IChannel;
import com.dragovorn.ircbot.api.irc.IConnection;
import com.dragovorn.ircbot.api.irc.IIRCServer;
import com.dragovorn.ircbot.impl.bot.AbstractIRCBot;
import com.dragovorn.ircbot.impl.user.BotAccount;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class Connection implements IConnection {

    private static final AbstractIRCBot BOT = AbstractIRCBot.getInstance();

    private final Map<String, IChannel> channels = Maps.newHashMap();

    private final Socket socket;

    private final IIRCServer server;

    private Thread inputThread;

    private final BufferedReader reader;
    private final BufferedWriter writer;

    public Connection(IIRCServer server) throws IOException {
        this.server = server;
        this.socket = new Socket(server.getIP(), server.getPort());
        this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        this.inputThread = new Thread(() -> {
            System.out.println("Input thread started!");

            try {
                boolean running = true;

                while (running) {
                    try {
                        String line;
                        while ((line = this.reader.readLine()) != null) {
                            AbstractIRCBot.getInstance().getEventBus().fireEvent(new RawInputMessageEvent(this, line));
                        }

                        running = false; // If we get here the server has quit on us.
                    } catch (InterruptedIOException e) {
                        // Ask the server if it's still there.
                        sendRawLine("PING " + (System.currentTimeMillis() / 1000));
                    }
                }
            } catch (IOException e) { /* DO NOTHING */ }

            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        this.inputThread.setName("Input Thread");

        this.socket.setSoTimeout(5 * 60 * 1000);
    }

    @Override
    public void connect() throws IOException {
        BotAccount account = (BotAccount) BOT.getAccount();

        String username = account.getUsername();

        sendRawLine("PASS " + account.getPassword());
        sendRawLine("NICK " + username);
        sendRawLine("USER " + username + " 8 * :" + username);

        if (this.server.handleConnection(this.reader)) {
            AbstractIRCBot.getInstance().getEventBus().fireEvent(new ServerConnectEvent(this));
            this.inputThread.start();
        }
    }

    @Override
    public void disconnect() throws IOException {
        this.channels.values().forEach(IChannel::part);
        this.channels.clear();

        sendRawLine("QUIT : Disconnected");
    }

    @Override
    public void sendRawLine(String line) throws IOException {
        if (this.socket.isClosed()) {
            return;
        }

        this.writer.write(line + "\r\n");
        this.writer.flush();

        if (AbstractIRCBot.getInstance().isLogRawLinesEnabled()) {
            System.out.println("SEND: " + line);
        }
    }

    @Override
    public void sendMessageToAll(String line) {
        this.channels.values().forEach((c -> c.sendMessage(line)));
    }

    @Override
    public void joinChannel(String name) {
        joinChannel(new Channel(name));
    }

    @Override
    public void joinChannel(IChannel channel) {
        this.channels.put(channel.getName(), channel);
        channel.join();
    }

    @Override
    public void leaveChannel(String name) {
        leaveChannel(this.channels.get(name));
    }

    @Override
    public void leaveChannel(IChannel channel) {
        this.channels.remove(channel.getName());
        channel.part();
    }

    @Override
    public void sendMessage(IChannel channel, String line) {
        channel.sendMessage(line);
    }

    @Override
    public void sendMessage(String name, String line) {
        sendMessage(this.channels.get(name), line);
    }

    @Override
    public IChannel getChannel(String name) {
        return this.channels.get(name);
    }

    @Override
    public IIRCServer getIRCServer() {
        return this.server;
    }

    @Override
    public List<IChannel> getChannels() {
        return ImmutableList.copyOf(this.channels.values());
    }

    @Override
    public Socket getSocket() {
        return this.socket;
    }

    @Override
    public BufferedReader getReader() {
        return this.reader;
    }

    @Override
    public BufferedWriter getWriter() {
        return this.writer;
    }
}
