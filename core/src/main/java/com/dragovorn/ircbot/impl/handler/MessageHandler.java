package com.dragovorn.ircbot.impl.handler;

import com.dragovorn.ircbot.api.bot.IRCBot;
import com.dragovorn.ircbot.api.command.ICommandManager;
import com.dragovorn.ircbot.api.event.Listener;
import com.dragovorn.ircbot.api.event.irc.user.channel.message.UserMessageChannelEvent;
import com.dragovorn.ircbot.api.exception.command.CommandExecutionException;
import com.dragovorn.ircbot.api.exception.command.InvalidArgumentException;
import com.dragovorn.ircbot.api.exception.command.NotEnoughArgumentsException;
import com.dragovorn.ircbot.api.exception.command.UnregisteredCommandException;
import com.dragovorn.ircbot.api.irc.IChannel;
import com.dragovorn.ircbot.api.irc.IConnection;
import com.dragovorn.ircbot.api.user.IUser;

public class MessageHandler {

    @Listener
    public void onUserMessageChannel(UserMessageChannelEvent event) {
        String message = event.getMessage();
        IUser user = event.getUser();
        IChannel channel = event.getChannel();
        IConnection connection = event.getConnection();

        ICommandManager manager = IRCBot.getInstance().getCommandManager();

        if (message.startsWith(String.valueOf(manager.getPrefix())) && message.length() > 1) {
            try {
                manager.execute(channel, user, connection, message);
            } catch (UnregisteredCommandException e) {
                if (manager.isLogInvalidCommandEnabled()) {
                    channel.sendMessage(user.getUsername() + ", unknown command '" + message.split(" ")[0].substring(1) + "'!");
                }
            } catch (InvalidArgumentException | NotEnoughArgumentsException e) {
                if (manager.isLogInvalidArgumentEnabled()) {
                    channel.sendMessage(user.getUsername() + ", invalid arguments for '" + message.split(" ")[0].substring(1) + "'!");
                }
            } catch (CommandExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
