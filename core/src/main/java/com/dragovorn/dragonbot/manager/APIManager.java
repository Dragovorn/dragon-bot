package com.dragovorn.dragonbot.manager;

import com.dragovorn.dragonbot.api.IAPI;
import com.dragovorn.dragonbot.api.IAPIManager;
import com.dragovorn.dragonbot.api.reflection.Reflection;
import com.google.common.collect.Maps;

import java.util.Map;

public final class APIManager implements IAPIManager {

    private final Map<Class<? extends IAPI>, IAPI> APIs = Maps.newHashMap();

    @Override
    public void registerAPI(IAPI api, Class<? extends IAPI> clazz) {
        if (!clazz.isAssignableFrom(api.getClass())) {
            throw new UnsupportedOperationException(api.getClass().getName() + " is not assignable to " + clazz.getName() + "!");
        }

        this.APIs.put(clazz, api);
    }

    @Override
    public void registerAPI(Class<? extends IAPI> clazz, Object... params) {
        try {
            this.APIs.put(clazz, Reflection.construct(clazz, params));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T extends IAPI> T getAPI(Class<T> clazz) {
        return (T) this.APIs.get(clazz);
    }
}