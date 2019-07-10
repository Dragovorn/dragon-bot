package com.dragovorn.ircbot.api;

public interface IAPIManager {

    void registerAPI(IAPI api, Class<? extends IAPI> clazz);
    void registerAPI(Class<? extends IAPI> clazz, Object... params);

    <T extends IAPI> T getAPI(Class<T> clazz);
}
