package com.dragovorn.dragonbot.util;

import java.net.URL;

public class FileSystem {

    public static URL getResource(String path) {
        return FileSystem.class.getClassLoader().getResource(path);
    }
}
