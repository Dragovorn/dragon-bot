package com.dragovorn.ircbot.impl.command;

import com.dragovorn.ircbot.api.command.Executor;
import com.dragovorn.ircbot.api.command.ICommandManager;
import com.dragovorn.ircbot.api.command.ParameterType;
import com.dragovorn.ircbot.api.command.argument.IArgument;
import com.dragovorn.ircbot.api.command.argument.annotation.Argument;
import com.dragovorn.ircbot.api.exception.command.CommandExecutionException;
import com.dragovorn.ircbot.api.exception.command.InvalidArgumentException;
import com.dragovorn.ircbot.api.exception.command.NotEnoughArgumentsException;
import com.dragovorn.ircbot.api.exception.command.UnregisteredCommandException;
import com.dragovorn.ircbot.api.irc.IChannel;
import com.dragovorn.ircbot.api.irc.IConnection;
import com.dragovorn.ircbot.api.user.IUser;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CommandManager implements ICommandManager {

    private Map<String, ParsedCommand> commands = Maps.newHashMap();

    private char prefix;

    private boolean logInvalidCommand;
    private boolean logInvalidArgument;

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
                    // Make sure parameter annotation has a non-default name.
                    if (parameter.name().equals(com.dragovorn.ircbot.api.command.Parameter.DEFAULT_NAME)) {
                        throw new IllegalStateException(clazz.getName() + " an argument parameter requires a name field!");
                    }

                    // Find the argument that aligns with this parameter.
                    Optional<Argument> optionalArgument = Arrays.stream((Argument[]) clazz.getAnnotationsByType(Argument.class))
                            .filter(a -> a.key().equals(parameter.name()))
                            .findFirst();

                    // If we found the argument add it to parameters.
                    if (optionalArgument.isPresent()) {
                        Argument a = optionalArgument.get();

                        parameters.add(new ParsedArgument(param.getType(), a.clazz(), a.required(), a.overflow(), ParameterType.ARGUMENT));
                    } else {
                        throw new IllegalStateException(clazz.getName() + " a parameter doesn't have a valid argument to accompany it!");
                    }
                    break;
            }
        }

        // Create the parsed command object and add it to our map.
        this.commands.put(label, new ParsedCommand(label, obj, parameters, method));
    }

    @Override
    public void setLogInvalidCommand(boolean logInvalidCommand) {
        this.logInvalidCommand = logInvalidCommand;
    }

    @Override
    public void setLogInvalidArgument(boolean logInvalidArgument) {
        this.logInvalidArgument = logInvalidArgument;
    }

    @Override
    public void execute(IChannel channel, IUser user, IConnection connection, String line) throws CommandExecutionException {
        // Split the whole line on spaces
        String[] split = line.split(" ");

        String label = split[0].substring(1);

        // Get command, trim off the prefix though.
        ParsedCommand command = this.commands.get(label);

        // Make sure we've actually got a command.
        if (command == null) {
            throw new UnregisteredCommandException(label);
        }

        // Count the required arguments.
        int required = (int) command.getParameters().stream()
                .filter(p -> p.getParameterType() == ParameterType.ARGUMENT)
                .filter(ParsedArgument::isRequired)
                .count();

        // Make sure we have enough arguments for the required arguments.
        if (split.length - 1 < required) {
            throw new NotEnoughArgumentsException();
        }

        // Keep track of the parameter objects to convert it to method parameters.
        List<Object> parameters = Lists.newLinkedList();

        // Keep track of where we are in the split, want to make sure we're after the command.
        int current = 1;

        boolean overflow = false;

        // Iterate over the arguments of the command object to begin building our parameters.
        for (ParsedArgument argument : command.getParameters()) {
            switch (argument.getParameterType()) {
                case CONNECTION:
                    parameters.add(connection);
                    break;
                case CHANNEL:
                    parameters.add(channel);
                    break;
                case USER:
                    parameters.add(user);
                    break;
                case ARGUMENT:
                    // We shouldn't be processing any more arguments after an overflow argument.
                    if (overflow) {
                        continue;
                    }

                    // If we are out of split string don't process.
                    if (current > split.length) {
                        parameters.add(null);
                        continue;
                    }

                    try {
                        // Make a new instance of the argument parser class that this argument uses.
                        IArgument<?> argumentParser = argument.getArgument().newInstance();

                        String cur;

                        // If the argument is an overflow argument use the rest of the array as the param.
                        if (overflow = argument.isOverflow()) {
                            cur = compileSplit(split, current);
                        } else {
                            cur = split[current];
                        }

                        // Make sure that the argument is parsable from the current string.
                        if (argumentParser.is(cur)) {
                            // Parse the string.
                            argumentParser.parse(cur);
                            parameters.add(argumentParser.get());

                            // Increment current.
                            current++;
                        } else {
                            // Invalid argument, throw exception!
                            throw new InvalidArgumentException();
                        }
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }

        try {
            // Execute the command's method.
            command.getMethod().invoke(command.getParent(), parameters.toArray(new Object[0]));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isLogInvalidCommandEnabled() {
        return this.logInvalidCommand;
    }

    @Override
    public boolean isLogInvalidArgumentEnabled() {
        return this.logInvalidArgument;
    }

    private String compileSplit(String[] split, int index) {
        StringBuilder builder = new StringBuilder();

        // Simply iterate over the split args and make them into one string.
        for (; index < split.length; index++) {
            builder.append(split[index]).append(" ");
        }

        return builder.toString().trim();
    }

    @Override
    public char getPrefix() {
        return this.prefix;
    }
}
