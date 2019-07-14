package com.dragovorn.dragonbot.command;

import com.dragovorn.ircbot.api.command.Command;
import com.dragovorn.ircbot.api.command.Executor;
import com.dragovorn.ircbot.api.command.Parameter;
import com.dragovorn.ircbot.api.command.ParameterType;
import com.dragovorn.ircbot.api.command.argument.annotation.Argument;
import com.dragovorn.ircbot.api.irc.IChannel;
import com.dragovorn.ircbot.api.user.IUser;
import com.dragovorn.ircbot.impl.command.argument.StringArgument;

@Command("github")
@Argument(key = "more", clazz = StringArgument.class)
@Argument(key = "test", clazz = StringArgument.class, overflow = true)
public class GithubCommand {

    @Executor
    public void execute(@Parameter(ParameterType.USER) IUser executor, @Parameter(ParameterType.CHANNEL) IChannel executed, @Parameter(ParameterType.ARGUMENT) String more, @Parameter(ParameterType.ARGUMENT) String test) {
        executed.sendMessage(executor.getUsername() + ", " + more);
        executed.sendMessage(executor.getUsername() + " overflow, " + test);
    }
}
