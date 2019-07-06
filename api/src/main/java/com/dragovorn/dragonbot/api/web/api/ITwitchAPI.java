package com.dragovorn.dragonbot.api.web.api;

import com.dragovorn.dragonbot.api.IAPI;

public interface ITwitchAPI extends IAPI {

    String getUsernameFromAccessToken(String token);
}
