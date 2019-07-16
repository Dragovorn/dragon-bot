package com.dragovorn.dragonbot.command;

import com.dragovorn.ircbot.api.command.Command;
import com.dragovorn.ircbot.api.command.Executor;
import com.dragovorn.ircbot.api.command.Parameter;
import com.dragovorn.ircbot.api.command.ParameterType;
import com.dragovorn.ircbot.api.irc.IChannel;
import com.dragovorn.ircbot.api.user.IUser;

@Command("github")
public class GithubCommand {

    @Executor
    public void execute(@Parameter(ParameterType.USER) IUser user, @Parameter(ParameterType.CHANNEL) IChannel channel) {
        channel.sendMessage(user.getUsername() + " -> Check out my github: https://github.com/Dragovorn/dragon-bot");
    }
}
