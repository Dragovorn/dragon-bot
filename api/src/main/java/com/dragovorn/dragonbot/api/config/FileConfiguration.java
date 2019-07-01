package com.dragovorn.dragonbot.api.config;

import com.dragovorn.dragonbot.api.factory.FileWriterFactory;
import com.dragovorn.dragonbot.api.factory.IFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;

public class FileConfiguration extends Configuration {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final Path path;

    private IFactory<? extends Writer, File> writerFactory;
    private IFactory<? extends Reader, File> readerFactory;

    /**
     * Creates a new configuration object with the given writer factory.
     *
     * @param path The path for configuration i/o.
     * @param writerFactory The writer factory to use to save the config.
     */
    public FileConfiguration(Path path, IFactory<? extends Writer, File> writerFactory, IFactory<? extends Reader, File> readerFactory) {
        this(path);
        this.writerFactory = writerFactory;
        this.readerFactory = readerFactory;
    }

    /**
     * Creates a new configuration object that saves to the given path.
     *
     * @param path The path for configuration i/o.
     */
    public FileConfiguration(Path path) {
        this.path = path;
        this.writerFactory = new FileWriterFactory();
    }

    @Override
    public void save() {
        try {
            Writer writer = this.writerFactory.create(this.path.toFile());

            JsonObject object = new JsonObject();

            this.values.forEach((key, value) -> {
                JsonElement element = GSON.toJsonTree(value);

                object.add(key, element);
            });

            GSON.toJson(object, writer);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load() {
        try {
            Reader reader = this.readerFactory.create(this.path.toFile());

            JsonObject object = GSON.fromJson(reader, JsonObject.class);

            object.entrySet().forEach(entry -> {
                String key = entry.getKey();
                JsonElement element = entry.getValue();

                if (element.getAsJsonPrimitive().isBoolean()) {
                    this.values.put(key, element.getAsBoolean());
                } else if (element.getAsJsonPrimitive().isNumber()) {
                    this.values.put(key, element.getAsNumber());
                } else {
                    this.values.put(key, element.getAsString());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Path getFile() {
        return this.path;
    }
}
