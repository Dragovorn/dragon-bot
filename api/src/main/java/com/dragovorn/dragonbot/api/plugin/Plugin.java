package com.dragovorn.dragonbot.api.plugin;

public @interface Plugin {

    String DEFAULT_URL = "DEFAULT_URL";

    String name();
    String author();
    String version();
    String updateURL() default "DEFAULT_URL";
}
