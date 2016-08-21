package com.dragovorn.dragonbot.api.bot.plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 7:14 PM.
 * as of 8/7/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class PluginClassLoader extends URLClassLoader {

//    final BotPlugin plugin;

    public PluginClassLoader(File file, ClassLoader parent) throws MalformedURLException {
        super(new URL[] {file.toURI().toURL()}, parent);


    }
}