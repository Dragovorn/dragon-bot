package com.dragovorn.dragonbot.handler;

import com.dragovorn.dragonbot.user.TwitchUser;
import com.dragovorn.ircbot.api.event.EventPriority;
import com.dragovorn.ircbot.api.event.Listener;
import com.dragovorn.ircbot.api.event.irc.user.channel.UserJoinChannelEvent;
import com.dragovorn.ircbot.api.event.irc.user.channel.UserPartChannelEvent;
import com.dragovorn.ircbot.api.event.irc.user.channel.message.UserMessageChannelEvent;
import com.dragovorn.ircbot.api.user.IUser;
import com.dragovorn.ircbot.impl.bot.AbstractIRCBot;

public class UserChannelHandler {

    @Listener(priority = EventPriority.MONITOR)
    public void onUserMessageChannel(UserMessageChannelEvent event) {
        TwitchUser user = (TwitchUser) event.getUser();

        String prefix = "";

        if (user.isBroadcaster()) {
            prefix = "[B]";
        } else if (user.isModerator()) {
            prefix = "[M]";
        } else if (user.isSubscriber()) {
            prefix = "[S]";
        }

        System.out.println("[" + event.getChannel().getName() + "] [CHAT]: " + prefix + " " + user.getDisplayName() + ": " + event.getMessage());
    }

    @Listener(priority = EventPriority.MONITOR)
    public void onUserJoinChannel(UserJoinChannelEvent event) {
        IUser user = event.getUser();

        if (!user.getUsername().equals(AbstractIRCBot.getInstance().getAccount().getUsername())) {
            event.getChannel().sendMessage("Hello, " + user.getUsername());
        }

        System.out.println("[" + event.getChannel().getName() + "] [JOIN]: " + user.getUsername());
    }

    @Listener(priority = EventPriority.MONITOR)
    public void onUserLeaveChannel(UserPartChannelEvent event) {
        IUser user = event.getUser();

        System.out.println("[" + event.getChannel().getName() + "] [PART]: " + user.getUsername());
    }
}
