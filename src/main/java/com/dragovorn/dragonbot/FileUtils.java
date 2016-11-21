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

package com.dragovorn.dragonbot;

import com.dragovorn.dragonbot.helper.FileHelper;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Maybe move this into api.bot.file?
public class FileUtils {

    private static File directory = new File("Dragon Bot");
    private static File config = new File(directory, "config.yml");
    private static File logs = new File(directory, "logs");
    private static File plugins = new File(directory, "plugins");
    private static File updater = new File(directory, "updater.jar");

    private static List<File> pluginAddedFiles = new ArrayList<>();

    public static void reloadFiles() {
        config = new File(directory, "config.yml");
        logs = new File(directory, "logs");
        plugins = new File(directory, "plugins");
        updater = new File(directory, "updater.jar");

        final List<File> files = new ArrayList<>();

        pluginAddedFiles.forEach(file -> files.add(new File(directory, file.getName())));
        pluginAddedFiles.clear();
        pluginAddedFiles.addAll(files);

        // TODO after reloading cut and paste them to the new location
    }

    @Nullable
    public static File getFile(@NotNull String name) {
        for (File file : pluginAddedFiles) {
            if (file.getName().indexOf(".") > 0) {
                if (file.getName().substring(0, file.getName().lastIndexOf(".")).equals(name)) {
                    return file;
                }
            }
        }

        return null;
    }

    public static File addFile(@NotNull String name) {
        File file = new File(directory, name);

        if (!file.exists()) {
            if (name.lastIndexOf(".") == -1) {
                file.mkdirs();
            } else {
                try {
                    file.createNewFile();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }

        pluginAddedFiles.add(file);

        return file;
    }

    public static void setDirectory(String directory) {
        FileUtils.directory = new File(directory, "Dragon Bot");

        if (!FileUtils.directory.exists()) {
            FileUtils.directory.mkdirs();
        }

        File file = FileHelper.getResource("path");

        try {
            FileWriter fileWriter = new FileWriter(file);

            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(directory);

            writer.close();
            fileWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        reloadFiles();
    }

    public static File getDirectory() {
        return directory;
    }

    public static File getConfig() {
        return config;
    }

    public static File getLogs() {
        return logs;
    }

    public static File getPlugins() {
        return plugins;
    }

    public static File getUpdater() {
        return updater;
    }
}