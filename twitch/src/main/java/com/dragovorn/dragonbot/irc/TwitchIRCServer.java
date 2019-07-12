package com.dragovorn.dragonbot.irc;

import com.dragovorn.ircbot.api.irc.IConnection;
import com.dragovorn.ircbot.api.irc.IIRCServer;
import com.dragovorn.ircbot.impl.bot.AbstractIRCBot;
import com.dragovorn.ircbot.impl.irc.Connection;

import java.io.BufferedReader;
import java.io.IOException;

public final class TwitchIRCServer implements IIRCServer {

    private static final String IP_ADDRESS = "irc.twitch.tv";

    private IConnection connection;

    @Override
    public void connect() throws IOException {
        this.connection = new Connection(this);
        this.connection.connect();
    }

    @Override
    public void disconnect() throws IOException {
        this.connection.disconnect();
        this.connection = null;
    }

    @Override
    public int getPort() {
        return 6667;
    }

    @Override
    public boolean isConnected() {
        return this.connection != null;
    }

    @Override
    public boolean handleConnection(BufferedReader reader) throws IOException {
        String line;

        // Use our own reading for initial connection.
        while (((line = reader.readLine()) != null)) {
            System.out.println("GET: " + line);

            if (line.equals(":tmi.twitch.tv NOTICE * :Improperly formatted auth")) {
                fail("Improperly formatted auth");

                return false;
            } else if (line.equals(":tmi.twitch.tv NOTICE * :Login unsuccessful")) {
                fail("Login unsuccessful");

                return false;
            } else {
                // Use these to get the error codes.
                int firstSpace = line.indexOf(" ");
                int secondSpace = line.indexOf(" ", firstSpace + 1);

                // The code is between the first two spaces of a message.
                if (secondSpace >= 0) {
                    String code = line.substring(firstSpace + 1, secondSpace);

                    if (code.equals("004")) {
                        // We've connected.
                        System.out.println("Successfully connected to twitch servers!");

                        return true;
                    } else if (code.startsWith("5") || code.startsWith("4")) {
                        this.connection.disconnect();

                        AbstractIRCBot.getInstance().getGuiManager().alert("Failed to connect to twitch! (Fail code)");

                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void fail(String message) throws IOException {
        this.connection.disconnect();
        AbstractIRCBot.getInstance().getGuiManager().alert("Failed to connect to twitch! (" + message + ")");
    }

    @Override
    public String getIP() {
        return IP_ADDRESS;
    }

    @Override
    public IConnection getConnection() {
        if (this.connection == null) {
            throw new IllegalStateException("Server isn't connected!");
        }

        return this.connection;
    }
}
