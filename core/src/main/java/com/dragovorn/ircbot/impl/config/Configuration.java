package com.dragovorn.ircbot.impl.config;

import com.dragovorn.ircbot.api.config.IConfiguration;
import com.dragovorn.ircbot.api.config.IConfigurationSerializable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Map;

public abstract class Configuration implements IConfiguration {

    protected final Map<String, Object> defaults;
    protected final Map<String, Object> values;

    /**
     * Creates a simple configuration that handles all it's values.
     */
    public Configuration() {
        this.defaults = Maps.newHashMap();
        this.values = Maps.newHashMap();

        addDefaults();

        this.values.putAll(this.defaults);
    }

    @Override
    public void reset() {
        this.values.clear();
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
    public void set(IConfigurationSerializable serializable) {
        serializable.save(this);
    }

    @Override
    public boolean has(String key) {
        return this.values.containsKey(key);
    }

    @Override
    public <T> T get(String key) {
        return (T) this.values.get(key);
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
