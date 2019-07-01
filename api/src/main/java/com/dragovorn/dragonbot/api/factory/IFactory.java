package com.dragovorn.dragonbot.api.factory;

import java.io.IOException;

public interface IFactory<R, P> {

    R create(P param) throws IOException;
}