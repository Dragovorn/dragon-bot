package com.dragovorn.ircbot.impl.event;

import com.dragovorn.ircbot.api.event.IAsyncEvent;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventBus implements IEventBus {

    private Map<Class<? extends IEvent>, List<Listener>> listeners = Maps.newHashMap();

    private ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public void fireEvent(IEvent event) {
        List<Listener> target = Lists.newArrayList();

        this.listeners.forEach((clazz, list) -> {
            if (clazz.isAssignableFrom(event.getClass()) || clazz.equals(event.getClass())) {
                target.addAll(list);
            }
        });

        Collections.sort(target);

        target.forEach(listener -> {
            if (event instanceof ICancellable && ((ICancellable) event).isCancelled() && !listener.isIgnoreCancelled()) {
                return;
            }

            try {
                if (listener.isAsync() && !(event instanceof IAsyncEvent)) {
                    this.executor.submit(() -> listener.getMethod().invoke(listener.getParent(), event));
                } else {
                    listener.getMethod().invoke(listener.getParent(), event);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void registerListeners(Object object) {
        Arrays.stream(object.getClass().getMethods())
                .filter(m -> m.isAnnotationPresent(com.dragovorn.ircbot.api.event.Listener.class))
                .forEach(m -> {
                    com.dragovorn.ircbot.api.event.Listener listener = m.getAnnotation(com.dragovorn.ircbot.api.event.Listener.class);

                    Class<?>[] paramTypes = m.getParameterTypes();

                    if (paramTypes.length > 1) {
                        throw new IllegalStateException(convertMethodToString(m) + " a listener method can only have 1 parameter");
                    }

                    if (!IEvent.class.isAssignableFrom(paramTypes[0])) {
                        throw new IllegalStateException(convertMethodToString(m) + " a listener parameter needs to be a child of IEvent!");
                    }

                    Class<? extends IEvent> event = paramTypes[0].asSubclass(IEvent.class);

                    this.listeners.putIfAbsent(event, Lists.newArrayList());
                    List<Listener> listeners = this.listeners.get(paramTypes[0]);
                    listeners.add(new Listener(object, m, listener));
                });
    }

    private String convertMethodToString(Method method) {
        return method.getDeclaringClass().getName() + "#" + method.getName();
    }
}
