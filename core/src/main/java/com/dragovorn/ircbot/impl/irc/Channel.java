package com.dragovorn.ircbot.impl.irc;

import com.dragovorn.ircbot.api.irc.IChannel;
import com.dragovorn.ircbot.impl.bot.AbstractIRCBot;

public class Channel implements IChannel {

    private static final AbstractIRCBot BOT = AbstractIRCBot.getInstance();

    private final String name;

    private boolean joined;

    public Channel(String name) {
        this.name = name;
    }

    @Override
    public void join() {
        if (this.joined) {
            throw new IllegalStateException("Already joined #" + this.name + "!");
        }

        BOT.sendRaw("JOIN #" + this.name);

        this.joined = true;
    }

    @Override
    public void part() {
        if (!this.joined) {
            throw new IllegalStateException("Haven't joined #" + this.name + " yet!");
        }

        BOT.sendRaw("PART #" + this.name);

        this.joined = false;
    }

    @Override
    public void sendMessage(String message) {
        BOT.sendRaw("PRIVMSG #" + this.name + " :" + message);
    }

    @Override
    public String getName() {
        return this.name;
    }
}
