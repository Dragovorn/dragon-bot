package com.dragovorn.ircbot.api.reflection;

import com.google.common.collect.Lists;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public final class Reflection {

    public static Class<?>[] toClasses(Object[] objects) {
        List<Class<?>> result = Lists.newLinkedList();

        Arrays.stream(objects).forEachOrdered(o -> result.add(o.getClass()));

        return result.toArray(new Class<?>[0]);
    }

    public static <T> T construct(Class<T> clazz, Object[] params) throws NoSuchMethodException {
        Constructor<T> constructor = clazz.getDeclaredConstructor(toClasses(params));

        return computeAccessible(constructor, clazz, ((c, p) -> {
            try {
                return c.newInstance(p);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }), params);
    }

    public static <T, A extends AccessibleObject>T computeAccessible(A object, Class<T> target, BiFunction<A, Object[], T> function, Object[] params) {
        boolean accessible = object.isAccessible();

        if (!accessible) {
            object.setAccessible(true);
        }

        T result = function.apply(object, params);

        if (!accessible) {
            object.setAccessible(false);
        }

        return result;
    }
}
