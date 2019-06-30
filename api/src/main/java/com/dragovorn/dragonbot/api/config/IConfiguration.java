package com.dragovorn.dragonbot.api.config;

import com.google.common.collect.ImmutableMap;

import java.nio.file.Path;

public interface IConfiguration {

    void save();
    void load();
    void remove(String key);
    default void addDefaults() { }
    void set(String key, Object value);

    boolean has(String key);

    <T> T get(String key, Class<T> clazz);

    Path getFile();

    ImmutableMap<String, Object> getValues();
    ImmutableMap<String, Object> getDefaults();
}
