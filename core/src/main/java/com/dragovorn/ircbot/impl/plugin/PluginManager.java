package com.dragovorn.ircbot.impl.plugin;

import com.dragovorn.ircbot.api.plugin.IPlugin;
import com.dragovorn.ircbot.api.plugin.IPluginManager;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.objectweb.asm.ClassReader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class PluginManager implements IPluginManager {

    private final Map<Class<? extends IPlugin>, LoadedPlugin> pluginMap = Maps.newHashMap();

    // Keep this list because I want to make sure enabling happens in the right order.
    private final List<LoadedPlugin> plugins = Lists.newLinkedList();

    @Override
    public void enablePlugins() {
        this.plugins.forEach(p -> {
            try {
                System.out.println("Enabling " + p.getName() + " v" + p.getVersion() + "...");
                p.onEnable();
                System.out.println("Enabled " + p.getName() + " v" + p.getVersion() + "!");
            } catch (Throwable throwable) {
                System.err.println("Exception while enabling " + p.getName() + "!");
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public void disablePlugins() {
        this.plugins.forEach(p -> {
            try {
                System.out.println("Disabling " + p.getName() + " v" + p.getVersion() + "...");
                p.onEnable();
                System.out.println("Disabled " + p.getName() + " v" + p.getVersion() + "!");
            } catch (Throwable throwable) {
                System.err.println("Exception while disabled " + p.getName() + "!");
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public void loadPlugins(Path plugins) {
        File folder = plugins.toFile();

        List<LoadedPlugin> loaded = Lists.newLinkedList();

        // Iterate over the files folder.
        for (File file : folder.listFiles()) {
            // Skip non-jar files.
            if (!file.getName().matches("(.+).(jar)")) {
                continue;
            }

            // Load plugin data.
            LoadedPlugin plugin = loadPluginData(file);
            loaded.add(plugin);
        }

        Map<LoadedPlugin, Boolean> pluginStatus = Maps.newHashMap();

        // Load the plugin in with our class loader, log if we couldn't load it.
        loaded.forEach(p -> {
            boolean status = loadPlugin(pluginStatus, new Stack<>(), p, loaded);
            pluginStatus.put(p, status);

            if (!status) {
                System.err.println("Failed to load plugin: " + p.getName() + "!");
            } else {
                this.plugins.add(p);
                this.pluginMap.put(p.getPlugin().getClass(), p);
            }
        });
    }

    private boolean loadPlugin(Map<LoadedPlugin, Boolean> pluginStatus, Stack<LoadedPlugin> dependencyStack, LoadedPlugin plugin, List<LoadedPlugin> loadedPlugins) {
        // Base condition.
        if (pluginStatus.containsKey(plugin)) {
            return pluginStatus.get(plugin);
        }

        Set<String> dependencies = Sets.newHashSet();

        // If the plugin has deps logged add them to the set.
        if (plugin.getDepends() != null && plugin.getDepends().length != 0) {
            dependencies.addAll(Arrays.asList(plugin.getDepends()));
        }

        for (String name : dependencies) {
            Optional<LoadedPlugin> depend = loadedPlugins.stream()
                    .filter(p -> p.getName().toLowerCase().equals(name.toLowerCase()))
                    .findFirst();

            // Store dep status like this so that it can be null.
            Boolean depStatus;

            if (depend.isPresent()) {
                depStatus = pluginStatus.get(depend.get());
            } else {
                depStatus = false;
            }

            if (depStatus == null) {
                // Check for circular dep.
                if (dependencyStack.contains(depend.get())) {
                    System.err.println("Circular dependency detected on " + plugin.getName() + "!");
                    return false;
                } else {
                    // We found a dep that hasn't been loaded, add this plugin to the loader stack and begin loading the unloaded dep.
                    dependencyStack.push(plugin);
                    depStatus = loadPlugin(pluginStatus, dependencyStack, depend.get(), loadedPlugins);
                    dependencyStack.pop();
                }
            }

            // Compare like this to avoid NPE.
            if (depStatus == Boolean.FALSE) {
                System.err.println(name + " is a dependency of " + plugin.getName() + ", but it isn't present!");
                return false;
            }
        }

        try {
            URLClassLoader loader = new PluginLoader(new URL[] { plugin.getFile().toURI().toURL() });

            Class<?> main = loader.loadClass(plugin.getMain());

            IPlugin constructedPlugin = (IPlugin) main.getDeclaredConstructor().newInstance();
            constructedPlugin.onLoad();

            plugin.setPlugin(constructedPlugin);

            System.out.println("Loaded " + plugin.getName() + " v" + plugin.getVersion() + " by " + plugin.getAuthor() + "!");
        } catch (Exception e) { // Make this catch all exceptions because the multi-catch was very long.
            e.printStackTrace();
        }

        return true;
    }

    private LoadedPlugin loadPluginData(File file) {
        LoadedPlugin.Builder builder = LoadedPlugin.newBuilder();
        builder.setFile(file);

        try {
            JarFile jar = new JarFile(file);

            // Iterate over the entries in the jarfile.
            for (ZipEntry entry : Collections.list(jar.entries())) {
                // Skip null entries.
                if (entry == null) {
                    continue;
                }

                // Skip BS Mac files.
                if (entry.getName().equals("__MACOSX")) {
                    continue;
                }

                // Skip random resources.
                if (!entry.getName().matches("(.+).(class)")) {
                    continue;
                }

                // Execute our ASM class visitor to discover the plugin annotation.
                ClassReader reader = new ClassReader(jar.getInputStream(entry));
                reader.accept(new PluginClassVisitor(entry.getName(), builder), 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        LoadedPlugin plugin = builder.build();

        // Make sure we actually got a plugin.
        if (plugin.getMain() == null || plugin.getMain().equals("")) {
            throw new IllegalStateException(file.getName() + " doesn't have a @Plugin annotation!");
        }

        return plugin;
    }

    @Override
    public <T extends IPlugin> T getPluginClass(Class<T> clazz) {
        return (T) this.pluginMap.get(clazz).getPlugin();
    }
}
