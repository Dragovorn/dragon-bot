package com.dragovorn.dragonbot.api.event;

public @interface Listener {

    boolean ignoreCancelled() default false;

    EventPriority priority() default EventPriority.NORMAL;
}
