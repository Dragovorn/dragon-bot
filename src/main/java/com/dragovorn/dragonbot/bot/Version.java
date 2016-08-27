package com.dragovorn.dragonbot.bot;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 3:39 PM.
 * as of 8/5/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
class Version {

    private static final char PATCH = 'a';

    private static final int SNAPSHOT = 0;

    private static final String VERSION = "1.05";
    static final String PRETTY_VERSION = "v" + VERSION + PATCH + (SNAPSHOT > 0 ? "_SNAPSHOT-" + SNAPSHOT : "");
}