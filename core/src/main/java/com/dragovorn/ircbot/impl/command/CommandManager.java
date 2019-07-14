package com.dragovorn.ircbot.impl.command;

import com.dragovorn.ircbot.api.command.Executor;
import com.dragovorn.ircbot.api.command.ICommandManager;
import com.dragovorn.ircbot.api.command.ParameterType;
import com.dragovorn.ircbot.api.command.argument.annotation.Argument;
import com.dragovorn.ircbot.api.irc.IChannel;
import com.dragovorn.ircbot.api.irc.IConnection;
import com.dragovorn.ircbot.api.user.IUser;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CommandManager implements ICommandManager {

    private Map<String, ParsedCommand> commands = Maps.newHashMap();

    private char prefix;

    @Override
    public void setPrefix(char prefix) {
        this.prefix = prefix;
    }

    @Override
    public void register(Object obj) {
        Class clazz = obj.getClass();

        // Make sure that we have a command annotation
        if (!clazz.isAnnotationPresent(com.dragovorn.ircbot.api.command.Command.class)) {
            throw new IllegalStateException(clazz.getName() + " a command requires a command annotation!");
        }

        String label = ((com.dragovorn.ircbot.api.command.Command) clazz.getAnnotation(com.dragovorn.ircbot.api.command.Command.class)).value();

        // Make sure that the label doesn't contain a space.
        if (label.contains(" ")) {
            throw new IllegalStateException(clazz.getName() + " a command cannot have a label with a space in it!");
        }

        List<ParsedArgument> parameters = Lists.newLinkedList();

        // Find the method with the Executor annotation.
        Optional<Method> optionalMethod = Arrays.stream(clazz.getMethods())
                .filter(m -> m.isAnnotationPresent(Executor.class))
                .findFirst();

        // Make sure we actually have a method marked as Executor.
        if (!optionalMethod.isPresent()) {
            throw new IllegalStateException(clazz.getName() + " a command requires a executor method!");
        }

        Method method = optionalMethod.get();

        // Parse method parameters.
        for (Parameter param : method.getParameters()) {

            // If one parameter doesn't have the annotation it's a malformed command.
            if (!param.isAnnotationPresent(com.dragovorn.ircbot.api.command.Parameter.class)) {
                throw new IllegalStateException(clazz.getName() + " all parameters in a executor method need to be annotated with @Parameter!");
            }

            com.dragovorn.ircbot.api.command.Parameter parameter = param.getAnnotation(com.dragovorn.ircbot.api.command.Parameter.class);

            ParameterType parameterType = parameter.value();

            // Sort out the parameter types and cover special cases.
            switch (parameterType) {
                case CONNECTION:
                    parameters.add(new ParsedArgument(IConnection.class, true, false, parameterType));
                    break;
                case USER:
                    parameters.add(new ParsedArgument(IUser.class, true, false, parameterType));
                    break;
                case CHANNEL:
                    parameters.add(new ParsedArgument(IChannel.class, true, false, parameterType));
                    break;
                case ARGUMENT:
                    // Find the argument that aligns with this parameter.
                    Optional<Argument> optionalArgument = Arrays.stream((Argument[]) clazz.getAnnotationsByType(Argument.class))
                            .filter(a -> a.key().equals(param.getName()))
                            .findFirst();

                    // If we found the argument add it to parameters.
                    optionalArgument.ifPresent(a ->
                            parameters.add(new ParsedArgument(param.getType(), a.clazz(), a.required(), a.overflow(), ParameterType.ARGUMENT)));
            }
        }

        // Create the parsed command object and add it to our map.
        this.commands.put(label, new ParsedCommand(label, obj, parameters));
    }

    @Override
    public void execute(String line) {
        String[] split = line.split(" ");

        ParsedCommand command = this.commands.get(split[0]);

        List<Object> parameters = Lists.newLinkedList();
    }

    @Override
    public char getPrefix() {
        return this.prefix;
    }
}
