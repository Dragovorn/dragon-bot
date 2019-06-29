package com.dragovorn.dragonbot.api.annotation;

public @interface Plugin {

    String DEFAULT_URL = "DEFAULT_URL";

    String name();
    String author();
    String version();
    String updateURL() default "DEFAULT_URL";
}
