package com.dragovorn.ircbot.api.command.argument.annotation;

import com.dragovorn.ircbot.api.command.argument.IArgument;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Repeatable(Arguments.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Argument {

    String key();

    Class<? extends IArgument> clazz();

    boolean required() default true;
    boolean overflow() default false;
}
