package com.dragovorn.ircbot.impl.plugin;

import com.dragovorn.ircbot.api.plugin.IPlugin;

import java.io.File;

public class LoadedPlugin {

    private IPlugin plugin;

    private File file;

    private final String main;
    private final String name;
    private final String author;
    private final String version;
    private final String[] depends;

    private LoadedPlugin(Builder builder) {
        this.file = builder.file;
        this.main = builder.main;
        this.name = builder.name;
        this.author = builder.author;
        this.version = builder.version;
        this.depends = builder.depends;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private File file;

        private String main;
        private String name;
        private String author;
        private String version;
        private String[] depends;

        private Builder() { }

        public Builder setFile(File file) {
            this.file = file;

            return this;
        }

        public Builder setMain(String main) {
            this.main = main;

            return this;
        }

        public Builder setName(String name) {
            this.name = name;

            return this;
        }

        public Builder setAuthor(String author) {
            this.author = author;

            return this;
        }

        public Builder setVersion(String version) {
            this.version = version;

            return this;
        }

        public Builder setDepends(String[] depends) {
            this.depends = depends;

            return this;
        }

        public LoadedPlugin build() {
            return new LoadedPlugin(this);
        }
    }

    public void setPlugin(IPlugin plugin) {
        this.plugin = plugin;
    }

    public void onLoad() {
        this.plugin.onLoad();
    }

    public void onEnable() {
        this.plugin.onEnable();
    }

    public void onDisable() {
        this.plugin.onDisable();
    }

    public IPlugin getPlugin() {
        return this.plugin;
    }

    public File getFile() {
        return this.file;
    }

    public String getMain() {
        return this.main;
    }

    public String getName() {
        return this.name;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getVersion() {
        return this.version;
    }

    public String[] getDepends() {
        return this.depends;
    }
}
