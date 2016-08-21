package com.dragovorn.dragonbot.api.bot.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 7:48 AM.
 * as of 8/7/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface Plugin {

    String name();
    String version() default "1.0";
    String author() default "Unknown";
}