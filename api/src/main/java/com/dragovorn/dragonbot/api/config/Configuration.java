package com.dragovorn.dragonbot.api.config;

import com.dragovorn.dragonbot.api.factory.FileWriterFactory;
import com.dragovorn.dragonbot.api.factory.IFactory;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.Map;

public class Configuration implements IConfiguration {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final Map<String, Object> defaults;
    private final Map<String, Object> values;

    private final Path path;

    private IFactory<? extends Writer, File> writerFactory;

    /**
     * Creates a new configuration object with the given writer factory.
     *
     * @param path The file to load the config from.
     * @param writerFactory The writer factory to use to save the config.
     */
    public Configuration(Path path, IFactory<? extends Writer, File> writerFactory) {
        this(path);
        this.writerFactory = writerFactory;
    }

    public Configuration(Path path) {
        this.path = path;
        this.defaults = Maps.newHashMap();
        this.values = Maps.newHashMap();
        this.writerFactory = new FileWriterFactory();

        addDefaults();

        this.values.putAll(this.defaults);
    }

    @Override
    public void save() {
        try {
            Writer writer = this.writerFactory.create(this.path.toFile());

            JsonObject object = new JsonObject();

            this.values.forEach((key, value) -> {
                JsonElement element = gson.toJsonTree(value);

                object.add(key, element);
            });

            gson.toJson(object, writer);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
