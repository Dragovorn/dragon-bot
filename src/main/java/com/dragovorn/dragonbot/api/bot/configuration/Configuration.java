/*
 * Copyright (c) 2016. Andrew Burr
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 *  associated documentation files (the "Software"), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge, publish, distribute,
 *  sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.dragovorn.dragonbot.api.bot.configuration;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public abstract class Configuration {

    protected Map<String, Object> entries;
    protected Map<String, Object> defaults;

    private File file;

    public Configuration(File file) {
        this.file = file;
        this.defaults = new HashMap<>();
    }

    public void save() {
        try {
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

            Yaml yaml = new Yaml(options);
            FileWriter writer = new FileWriter(this.file);

            yaml.dump(this.entries, writer);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void load() {
        try {
            Yaml yaml = new Yaml();

            InputStream stream = new FileInputStream(this.file);

            this.entries = (Map<String, Object>) yaml.load(stream);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }

        if (this.entries == null) {
            generate();
            load();

            return;
        }

        update();
    }

    public void update() {
        this.defaults.clear();
        addDefaults();

        this.defaults.forEach((key, value) -> {
            if (!this.entries.containsKey(key)) {
                this.entries.put(key, value);
            }
        });

        ArrayList<String> remove = new ArrayList<>();

        this.entries.forEach((key, value) -> {
            if (!this.defaults.containsKey(key)) {
                remove.add(key);
            }
        });

        remove.forEach(string -> this.entries.remove(string));
    }

    public void generate() {
        this.entries = new HashMap<>();

        setDefaults();
        save();
    }

    public void setDefaults() {
        this.defaults.clear();
        addDefaults();
        this.entries.clear();
        this.entries.putAll(this.defaults);
    }

    protected abstract void addDefaults();

    protected void add(String key, Object value) {
        this.defaults.put(key, value);
    }

    public void clear() {
        this.entries.clear();
    }

    public void set(String key, Object value) {
        this.entries.put(key, value);
    }

    public String getString(String key) {
        return (String) this.entries.get(key);
    }

    public boolean getBoolean(String key) {
        return (boolean) this.entries.get(key);
    }

    public <T> List<T> getList(String key) {
        return (List<T>) this.entries.get(key);
    }

    public Map<String, Object> getEntries() {
        return this.entries;
    }
}