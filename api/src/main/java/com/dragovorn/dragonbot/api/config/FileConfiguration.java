package com.dragovorn.dragonbot.api.config;

import com.dragovorn.dragonbot.api.factory.FileWriterFactory;
import com.dragovorn.dragonbot.api.factory.IFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;

public class FileConfiguration extends Configuration {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final Path path;

    private IFactory<? extends Writer, File> writerFactory;

    /**
     * Creates a new configuration object with the given writer factory.
     *
     * @param path The path for configuration i/o.
     * @param writerFactory The writer factory to use to save the config.
     */
    public FileConfiguration(Path path, IFactory<? extends Writer, File> writerFactory) {
        this(path);
        this.writerFactory = writerFactory;
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

    public Path getFile() {
        return this.path;
    }
}
