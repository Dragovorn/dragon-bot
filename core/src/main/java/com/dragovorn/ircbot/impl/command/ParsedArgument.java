package com.dragovorn.ircbot.impl.command;

import com.dragovorn.ircbot.api.command.ParameterType;
import com.dragovorn.ircbot.api.command.argument.IArgument;

public class ParsedArgument {

    private final ParameterType parameterType;

    private final Class<?> type;

    private Class<? extends IArgument> argument;

    private final boolean required;
    private final boolean overflow;

    public ParsedArgument(Class<?> type, Class<? extends IArgument> argument, boolean required, boolean overflow, ParameterType parameterType) {
        this(type, required, overflow, parameterType);
        this.argument = argument;
    }

    public ParsedArgument(Class<?> type, boolean required, boolean overflow, ParameterType parameterType) {
        this.parameterType = parameterType;
        this.type = type;
        this.required = required;
        this.overflow = overflow;
    }

    public ParameterType getParameterType() {
        return this.parameterType;
    }

    public Class<?> getType() {
        return this.type;
    }

    public Class<? extends IArgument> getArgument() {
        return this.argument;
    }

    public boolean isRequired() {
        return this.required;
    }

    public boolean isOverflow() {
        return this.overflow;
    }

    @Override
    public String toString() {
        return "ParsedArgument[type=" + this.parameterType.name() + ",type=" + this.type.getName() + "]";
    }
}
