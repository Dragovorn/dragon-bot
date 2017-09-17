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

package com.dragovorn.dragonbot.api.bot.plugin;

import java.io.File;

public class PluginInfo {
    private final File file;

    private final String main;
    private final String name;
    private final String version;
    private final String author;
    private final String[] dependencies;

    static class Builder {
        private File file;

        private String main = "";
        private String name = "";
        private String version = "1.0.0";
        private String author = "Unknown";
        private String[] dependencies = new String[0];

        public Builder setFile(File file) {
            this.file = file;

            return this;
        }

        public String getMain() {
            return this.main;
        }

        public Builder setMain(String main) {
            this.main = main;

            return this;
        }

        public Builder setName(String name) {
            this.name = name;

            return this;
        }

        public Builder setVersion(String version) {
            this.version = version;

            return this;
        }

        public Builder setAuthor(String author) {
            this.author = author;

            return this;
        }

        public Builder setDependencies(String... dependencies) {
            this.dependencies = dependencies;

            return this;
        }
        public PluginInfo build() {
            return new PluginInfo(this.file, this.main, this.name, this.version, this.author, this.dependencies);
        }

    }

    private PluginInfo(File file, String main, String name, String version, String author, String[] dependencies) {
        this.file = file;
        this.main = main;
        this.name = name;
        this.version = version;
        this.author = author;
        this.dependencies = dependencies;
    }

    public File getFile() {
        return this.file;
    }

    public String getMain() {
        return this.main;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getAuthor() {
        return author;
    }

    public String[] getDependencies() {
        return this.dependencies;
    }
}