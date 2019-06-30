package com.dragovorn.dragonbot.api.config;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.io.FileWriter;
import java.nio.file.Path;
import java.util.Map;

public class Configuration implements IConfiguration {

    private final Map<String, Object> defaults;
    private final Map<String, Object> values;

    private final Path path;

    public Configuration(Path path) {
        this.path = path;
        this.defaults = Maps.newHashMap();
        this.values = Maps.newHashMap();

        addDefaults();

        this.values.putAll(this.defaults);
    }

    @Override
    public void save() {
//        FileWriter writer = new FileWriter(this.path);
    }

    @Override
    public void load() {

    }

    @Override
    public void remove(String key) {
        this.values.remove(key);
    }

    @Override
    public void set(String key, Object value) {
        this.values.put(key, value);
    }

    @Override
    public boolean has(String key) {
        return this.values.containsKey(key);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return (T) this.values.get(key);
    }

    @Override
    public Path getFile() {
        return this.path;
    }

    @Override
    public ImmutableMap<String, Object> getValues() {
        return ImmutableMap.copyOf(this.values);
    }

    @Override
    public ImmutableMap<String, Object> getDefaults() {
        return ImmutableMap.copyOf(this.defaults);
    }
}
