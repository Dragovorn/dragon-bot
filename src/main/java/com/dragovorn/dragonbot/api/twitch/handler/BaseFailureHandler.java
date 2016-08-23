package com.dragovorn.dragonbot.api.twitch.handler;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 1:08 AM.
 * as of 8/23/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public interface BaseFailureHandler {

    void onFailure(int code, String statusMessage, String errorMessage);

    void onFailure(Throwable throwable);
}