package com.dragovorn.ircbot.impl.irc;

import com.dragovorn.ircbot.api.irc.IChannel;
import com.dragovorn.ircbot.api.irc.IConnection;
import com.dragovorn.ircbot.api.irc.IIRCServer;
import com.dragovorn.ircbot.impl.bot.AbstractIRCBot;
import com.dragovorn.ircbot.impl.user.BotAccount;
import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

public class Connection implements IConnection {

    private static final AbstractIRCBot BOT = AbstractIRCBot.getInstance();

    private final List<IChannel> activeChannels = Lists.newArrayList();

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
            String line;
            try {
                while (((line = this.reader.readLine()) != null)) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                this.inputThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        this.inputThread.setName("Input Thread");
        this.inputThread.start();

        BotAccount account = (BotAccount) BOT.getAccount();

        String username = account.getUsername();

        sendRawLine("PASS " + account.getPassword());
        sendRawLine("NICK " + username);
        sendRawLine("USER " + username + " 8 * :" + username);
    }

    @Override
    public void sendRawLine(String line) throws IOException {
        this.writer.write(line + "\r\n");
        this.writer.flush();

        System.out.println(line);
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
        return this.reader;
    }

    @Override
    public BufferedWriter getWriter() {
        return this.writer;
    }
}
