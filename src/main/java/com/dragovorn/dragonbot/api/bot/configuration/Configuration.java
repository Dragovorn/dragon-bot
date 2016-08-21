package com.dragovorn.dragonbot.api.bot.configuration;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 10:13 AM.
 * as of 8/6/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class Configuration {

    protected Map<String, Object> entries = new HashMap<>();

    protected File file;

    public Configuration(File file) {
        this.file = file;
    }

    public void save() {
        try {
            DumperOptions options = new DumperOptions();

            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

            Yaml yaml = new Yaml();
            FileWriter writer = new FileWriter(this.file);

            yaml.dump(entries, writer);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void load() {
        try {
            Yaml yaml = new Yaml();

            InputStream stream = new FileInputStream(file);

            entries = (Map<String, Object>) yaml.load(stream);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    public void clear() {
        entries.clear();
    }

    public void set(String key, Object value) {
        entries.put(key, value);
    }

    public String getString(String key) {
        return (String) entries.get(key);
    }

    public boolean getBoolean(String key) {
        return (boolean) entries.get(key);
    }

    public List<?> getList(String key) {
        return (List<?>) entries.get(key);
    }

    public Map<String, Object> getEntries() {
        return entries;
    }
}