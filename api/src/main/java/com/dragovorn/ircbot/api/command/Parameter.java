package com.dragovorn.ircbot.api.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Parameter {

    String DEFAULT_NAME = "[DEFAULT NAME]";

    ParameterType value();

    String name() default DEFAULT_NAME;
}
