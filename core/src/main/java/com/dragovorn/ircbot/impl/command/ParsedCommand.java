package com.dragovorn.ircbot.impl.command;

import com.google.common.collect.ImmutableList;

import java.lang.reflect.Method;
import java.util.List;

public class ParsedCommand {

    private final String label;

    private final Object parent;

    private final Method method;

    private final List<ParsedArgument> parameters;

    public ParsedCommand(String label, Object parent, List<ParsedArgument> parameters, Method method) {
        this.label = label;
        this.parent = parent;
        this.method = method;
        this.parameters = parameters;
    }

    public String getLabel() {
        return this.label;
    }

    public Object getParent() {
        return this.parent;
    }

    public Method getMethod() {
        return this.method;
    }

    public List<ParsedArgument> getParameters() {
        return ImmutableList.copyOf(this.parameters);
    }
}
