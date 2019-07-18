package com.dragovorn.ircbot.impl.event;

import com.dragovorn.ircbot.api.event.EventPriority;

import java.lang.reflect.Method;

public class Listener implements Comparable<Listener> {

    private final boolean async;
    private final boolean ignoreCancelled;

    private final Object parent;

    private final Method method;

    private final EventPriority priority;

    public Listener(Object object, Method method, com.dragovorn.ircbot.api.event.Listener listener) {
        this.parent = object;
        this.async = listener.async();
        this.ignoreCancelled = listener.ignoreCancelled();
        this.method = method;
        this.priority = listener.priority();
    }

    public boolean isAsync() {
        return this.async;
    }

    public boolean isIgnoreCancelled() {
        return this.ignoreCancelled;
    }

    public Object getParent() {
        return this.parent;
    }

    public Method getMethod() {
        return this.method;
    }

    public EventPriority getPriority() {
        return this.priority;
    }

    @Override
    public int compareTo(Listener listener) {
        return this.priority.ordinal() - listener.priority.ordinal();
    }
}
