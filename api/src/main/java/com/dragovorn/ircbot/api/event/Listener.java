package com.dragovorn.ircbot.api.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Listener {

    boolean async() default false;
    boolean ignoreCancelled() default false;

    EventPriority priority() default EventPriority.NORMAL;
}
