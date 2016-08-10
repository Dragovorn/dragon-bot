package com.dragovorn.dragonbot;

import com.dragovorn.dragonbot.plugin.Plugin;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 3:43 PM.
 * as of 8/5/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class Core {

    private Core() {
        for (File file : FileLocations.plugins.listFiles()) {
            if (file.getName().matches("(.+).(jar)$")) {
                JarFile jar;

                try {
                    jar = new JarFile(file);

                    URLClassLoader loader = new URLClassLoader(new URL[] {file.toURI().toURL()}, getClass().getClassLoader());

                    for (ZipEntry entry : Collections.list(jar.entries())) {
                        if (entry.getName() != null && entry.getName().startsWith("__MACOSX")) {
                            continue;
                        }

                        if (!entry.getName().matches("(.+).(class)$")) {
                            continue;
                        }

                        Class<?> clazz = Class.forName(entry.getName().replace(".class", "").replace("/", "."), true, loader);

                        Plugin plugin = clazz.getAnnotation(Plugin.class);

                        if (plugin != null) {
                            System.out.println("Name - " + plugin.name());
                        }
                    }
                } catch (Exception exception) {

                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
//        new DragonBot();

        new Core();
    }
}