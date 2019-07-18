package com.dragovorn.ircbot.impl.handler;

import com.dragovorn.ircbot.api.event.EventPriority;
import com.dragovorn.ircbot.api.event.Listener;
import com.dragovorn.ircbot.api.event.irc.RawInputMessageEvent;
import com.dragovorn.ircbot.api.event.irc.user.channel.UserJoinChannelEvent;
import com.dragovorn.ircbot.api.event.irc.user.channel.UserPartChannelEvent;
import com.dragovorn.ircbot.api.event.irc.user.channel.message.UserMessageChannelEvent;
import com.dragovorn.ircbot.api.irc.IConnection;
import com.dragovorn.ircbot.api.user.IUser;
import com.dragovorn.ircbot.api.user.UserInfo;
import com.dragovorn.ircbot.impl.bot.AbstractIRCBot;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class RawLineHandler {

    @Listener(priority = EventPriority.MONITOR)
    public void onRawInputMessage(RawInputMessageEvent event) throws IOException {
        String line = event.getMessage();

        // Log the raw input lines.
        if (AbstractIRCBot.getInstance().isLogRawLinesEnabled()) {
            System.out.println("GET: " + line);
        }

        // Handle a ping message from irc servers.
        if (line.startsWith("PING")) {
            event.getConnection().sendRawLine("PONG " + line.substring(5));
            return;
        }

        ImmutableMap.Builder<String, String> tags = ImmutableMap.builder();

        // Check if the line has irc v3 tags.
        if (line.startsWith("@")) {
            // This gives us a string of just the irc v3 tags.
            String v3Tags = line.substring(1, line.indexOf(" "));

            // Cut the tags out of the line, makes parsing it later easier.
            line = line.substring(line.indexOf(" ") + 1);

            StringTokenizer tokenizer = new StringTokenizer(v3Tags);

            // Parse through the tags and add them to our map.
            while (tokenizer.hasMoreTokens()) {
                String tag = tokenizer.nextToken(";");

                // Check if the current tag is just a marker or holds actual data.
                if (tag.contains("=")) {
                    String[] parts = tag.split("=");
                    tags.put(parts[0], (parts.length == 2 ? parts[1] : ""));
                } else {
                    tags.put(tag, "");
                }
            }
        }

        // Split the raw line into usable information.
        Queue<String> parts = new LinkedList<>();

        // Some helper values to keep track of where in the string we are.
        int pos = 0;
        int end;

        boolean broken = false;

        // Parse through all of the spaces on the
        while ((end = line.indexOf(' ', pos)) >= 0 && !broken) {
            parts.add(line.substring(pos, end));
            pos = end + 1;

            // When we get to the : telling us that there's something important here we break.
            if (line.charAt(pos) == ':') {
                parts.add(line.substring(pos + 1));
                broken = true;
            }
        }

        // Only want to add the rest of the string if there was no colon.
        if (!broken) {
            parts.add(line.substring(pos));
        }

        String senderInfo = parts.remove();
        String command = parts.remove();
        String nick = "";
        String hostname = "";
        String login = "";
        String target = null;

        // Parse user info
        if (senderInfo.startsWith(":")) {
            // Figure out where some splitting characters are.
            int exclamation = senderInfo.indexOf("!");
            int at = senderInfo.indexOf("@");

            // If it's a properly formatted sender info string, parse it.
            if (exclamation > 0 && at > 0 && exclamation < at) {
                nick = senderInfo.substring(1, exclamation);
                login = senderInfo.substring(exclamation + 1, at);
                hostname = senderInfo.substring(at + 1);
            } else {
                // Otherwise it's probably something special, like a command.
                if (!parts.isEmpty()) {
                    int code = -1;

                    try {
                        // If there's a code it's definitely a special command.
                        code = Integer.parseInt(command);
                    } catch (NumberFormatException exception) {
                        // Leave it at -1
                    }

                    if (code == -1) {
                        // No code, it's something weird, return.
                        return;
                    } else {
                        // Has a code, which means sender info is a nick and there's a target.
                        nick = senderInfo;
                        target = command;
                    }
                }
            }
        }

        command = command.toUpperCase();

        // Clean up the nick.
        if (nick.startsWith(":")) {
            nick = nick.substring(1);
        }

        // If target is null then we need to get ourselves a target.
        if (target == null) {
            target = parts.remove();
        }

        // Clean up the target.
        if (target.startsWith(":")) {
            target = target.substring(1);
        }

        // Turn all of our info into a user object.
        IUser user = AbstractIRCBot.getInstance().getUserFactory().create(new UserInfo(login, hostname, nick, tags.build()));

        // Clean up default target and name it channel.
        String channel = target.substring(1);

        IConnection connection = event.getConnection();

        // Special handling for each command.
        switch (command) {
            case "PRIVMSG":
                String message = parts.remove();
                AbstractIRCBot.getInstance().getEventBus().fireEvent(new UserMessageChannelEvent(connection, user, connection.getChannel(channel), message));
                break;
            case "JOIN":
                AbstractIRCBot.getInstance().getEventBus().fireEvent(new UserJoinChannelEvent(connection, user, connection.getChannel(channel)));
                break;
            case "PART":
                AbstractIRCBot.getInstance().getEventBus().fireEvent(new UserPartChannelEvent(connection, user, connection.getChannel(channel)));
                break;
        }
    }
}
