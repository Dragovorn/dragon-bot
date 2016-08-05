package com.dragovorn.dragonbot;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 3:39 PM.
 * as of 8/5/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class Version {

    private static final char PATCH = 'a';

    private static final int SNAPSHOT = 1;

    private static final String VERSION = "1.03";
    static final String PRETTY_VERSION = VERSION + PATCH + (SNAPSHOT > 0 ? "_SNAPSHOT-" + SNAPSHOT : "");
}