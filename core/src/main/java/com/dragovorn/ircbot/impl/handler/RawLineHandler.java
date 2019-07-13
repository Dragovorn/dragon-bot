package com.dragovorn.ircbot.impl.handler;

import com.dragovorn.ircbot.api.event.EventPriority;
import com.dragovorn.ircbot.api.event.Listener;
import com.dragovorn.ircbot.api.event.irc.RawInputMessageEvent;
import com.dragovorn.ircbot.impl.bot.AbstractIRCBot;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class RawLineHandler {

    @Listener(priority = EventPriority.LOWEST)
    public void onRawInputMessage(RawInputMessageEvent event) throws IOException {
        String line = event.getMessage();

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

            // Split the raw line into usable information.
            Queue<String> parts = new LinkedList<>();

            // Some helper values to keep track of where in the string we are.
            int pos = 0;
            int end;

            // Parse through all of the spaces on the
            while ((end = line.indexOf(' ', pos)) >= 0) {
                parts.add(line.substring(pos, end));
                pos = end + 1;

                // When we get to the : telling us that there's something important here we break.
                if (line.charAt(pos) == ':') {
                    parts.add(line.substring(pos + 1));
                    break;
                }
            }

            // Add the last of it to the stack.
            parts.add(line.substring(pos));

            // TODO: test to see if our parts algorithm is working properly!
            while (!parts.isEmpty()) {
                System.out.println(parts.remove());
            }
        }
    }
}
