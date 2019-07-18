package com.dragovorn.dragonbot.handler;

import com.dragovorn.dragonbot.user.TwitchUser;
import com.dragovorn.ircbot.api.event.EventPriority;
import com.dragovorn.ircbot.api.event.Listener;
import com.dragovorn.ircbot.api.event.irc.user.channel.message.UserMessageChannelEvent;

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
}
