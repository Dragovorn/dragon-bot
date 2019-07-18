package com.dragovorn.ircbot.impl.plugin;

import com.google.common.collect.Sets;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;

public class PluginLoader extends URLClassLoader {

    // This makes loading kidna intensive, but the access it gives is worth it imo.
    private static final Set<PluginLoader> loaders = Sets.newCopyOnWriteArraySet();

    static {
        ClassLoader.registerAsParallelCapable();
    }

    public PluginLoader(URL[] urls) {
        super(urls);

        // Keep track of this loader!
        loaders.add(this);
    }

    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {

        // Make sure to forward load class to our recursive helper.
        return loadClass(name, resolve, true);
    }

    private Class<?> loadClass(String name, boolean resolve, boolean checkOther) throws ClassNotFoundException {
        try {
            // Try to load the class in our loader, if it blows up high chance it's been loaded in another loader.
            return super.loadClass(name, resolve);
        } catch (ClassNotFoundException exception) { /* Do Nothing */ }

        // Check the other class loaders if they have loaded the class that just blew up.
        if (checkOther) {
            for (PluginLoader loader : loaders) {
                if (loader != this) {
                    try {
                        // Get the class from the loader that loaded it.
                        return loader.loadClass(name, resolve, false);
                    } catch (ClassNotFoundException exception) { /* Do Nothing */ }
                }
            }
        }

        // Ok JK the class actually doesn't exist.
        throw new ClassNotFoundException(name);
    }
}
