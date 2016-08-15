package com.dragovorn.dragonbot;

import java.io.File;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 10:23 AM.
 * as of 8/6/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class FileLocations {

    public static final File directory = new File("Dragon Bot");
    public static final File config = new File(directory, "config.yml");
    public static final File logs = new File(directory, "logs");
    public static final File plugins = new File(directory, "plugins");
    public static final File updater = new File(directory, "updater.jar");
}