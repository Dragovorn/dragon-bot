package com.dragovorn.ircbot.impl.event;

import com.dragovorn.ircbot.api.event.ICancellable;
import com.dragovorn.ircbot.api.event.IEvent;
import com.dragovorn.ircbot.api.event.IEventBus;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EventBus implements IEventBus {

    private Map<Class<? extends IEvent>, List<Listener>> listeners = Maps.newHashMap();

    @Override
    public void fireEvent(IEvent event) {
        this.listeners.computeIfPresent(event.getClass(), ((clazz, list) -> {
            list.forEach(listener -> {
                if (event instanceof ICancellable && ((ICancellable) event).isCancelled() && !listener.isIgnoreCancelled()) {
                    return;
                }

                try {
                    // TODO: check if async handling.

                    listener.getMethod().invoke(listener.getParent(), event);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });

            return null;
        }));
    }

    @Override
    public void registerListeners(Object object) {
        Arrays.stream(object.getClass().getMethods())
                .forEach(m -> {
                    if (!m.isAnnotationPresent(com.dragovorn.ircbot.api.event.Listener.class)) {
                        return;
                    }

                    com.dragovorn.ircbot.api.event.Listener listener = m.getAnnotation(com.dragovorn.ircbot.api.event.Listener.class);

                    Class<?>[] paramTypes = m.getParameterTypes();

                    if (paramTypes.length > 1) {
                        throw new IllegalStateException(convertMethodToString(m) + " a listener method can only have 1 parameter");
                    }

                    if (!paramTypes[0].isAssignableFrom(IEvent.class)) {
                        throw new IllegalStateException(convertMethodToString(m) + " a listener parameter needs to extend IEvent!");
                    }

                    Class<? extends IEvent> event = paramTypes[0].asSubclass(IEvent.class);

                    this.listeners.putIfAbsent(event, Lists.newArrayList());
                    List<Listener> listeners = this.listeners.get(paramTypes[0]);
                    listeners.add(new Listener(object, m, listener));

                    Collections.sort(listeners);
                });
    }

    private String convertMethodToString(Method method) {
        return method.getDeclaringClass().getName() + "#" + method.getName();
    }
}
