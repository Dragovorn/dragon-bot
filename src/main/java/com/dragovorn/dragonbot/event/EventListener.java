package com.dragovorn.dragonbot.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 4:45 PM.
 * as of 8/10/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventListener { }