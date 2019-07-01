package com.dragovorn.dragonbot.api.config;

import com.dragovorn.dragonbot.api.factory.FileReaderFactory;
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
        if (!path.toFile().exists()) {
            try {
                path.toFile().createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.path = path;
        this.writerFactory = new FileWriterFactory();
        this.readerFactory = new FileReaderFactory();
    }

    @Override
    public void save() {
        try {
            Writer writer = this.writerFactory.create(this.path.toFile());

            JsonObject object = new JsonObject();

            this.values.forEach((key, value) -> {
                String[] keys = key.split("\\.");

                writeToJsonObject(keys, 0, object, value);
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

            if (object == null) {
                return;
            }

            loadJsonIntoValues(object, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A helper method to convert a dot notated key to a JsonObject.
     *
     * @param keys The result of splitting the string on the dots.
     * @param index The index in the array to check.
     * @param previous The previous JsonObject, used when finally putting the value in.
     * @param value The actual value to put into the final JsonObject.
     */
    private void writeToJsonObject(String[] keys, int index, JsonObject previous, Object value) {
        if (index + 1 >= keys.length) {
            previous.add(keys[index], toJsonElement(value));

            return;
        }

        JsonObject current;

        if (previous.has(keys[index])) {
            current = previous.get(keys[index]).getAsJsonObject();
        } else {
            current = new JsonObject();
        }

        previous.add(keys[index], current);

        writeToJsonObject(keys, ++index, current, value);
    }

    /**
     * A helper method to convert an object to a JsonElement.
     *
     * @param object The object to convert.
     * @return The JsonElement of the passed object.
     */
    private JsonElement toJsonElement(Object object) {
        return GSON.toJsonTree(object);
    }

    /**
     * A helper method to make loading a little more streamlined, will support nested objects in dot notation.
     * <pre>{@code example.dot.notation}</pre> would be represented by:
     * <pre>{@code
     * "example": {
     *     "dot": {
     *         "notation": "somevalue"
     *     }
     * }
     * }</pre>
     *
     * @param object The json object to iterate
     * @param namespace The current operating namespace.
     */
    private void loadJsonIntoValues(JsonObject object, String namespace) {
        object.entrySet().forEach(entry -> {
            String key = namespace + entry.getKey();
            JsonElement element = entry.getValue();

            if (element.isJsonObject()) {
                loadJsonIntoValues(element.getAsJsonObject(), key + ".");
            } else if (element.getAsJsonPrimitive().isBoolean()) {
                this.values.put(key, element.getAsBoolean());
            } else if (element.getAsJsonPrimitive().isNumber()) {
                this.values.put(key, element.getAsNumber());
            } else {
                this.values.put(key, element.getAsString());
            }
        });
    }

    public Path getFile() {
        return this.path;
    }
}
