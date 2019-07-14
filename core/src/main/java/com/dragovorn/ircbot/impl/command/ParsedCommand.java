package com.dragovorn.ircbot.impl.command;

import com.google.common.collect.ImmutableMap;

import java.util.List;

public class ParsedCommand {

    private final String label;

    private final Object parent;

    private final List<ParsedArgument> parameters;

    public ParsedCommand(String label, Object parent, List<ParsedArgument> parameters) {
        this.label = label;
        this.parent = parent;
        this.parameters = parameters;
    }

    public String getLabel() {
        return this.label;
    }

    public Object getParent() {
        return this.parent;
    }

    public List<ParsedArgument> getParameters() {
        return ImmutableMap.copyOf(this.parameters);
    }
}
