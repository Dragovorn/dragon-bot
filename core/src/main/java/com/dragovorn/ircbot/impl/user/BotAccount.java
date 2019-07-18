package com.dragovorn.ircbot.impl.user;

import com.dragovorn.ircbot.api.config.IConfiguration;
import com.dragovorn.ircbot.api.config.IConfigurationSerializable;
import com.dragovorn.ircbot.api.user.UserInfo;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

// This class isn't exposed in the API, should make storing the bot account a little more secure.
public class BotAccount extends User implements IConfigurationSerializable {

    private String password;

    public BotAccount() {
        // Make a dummy user info object to 'trick' the constructor.
        this(new UserInfo("bot", "bot", "bot", ImmutableMap.<String, String>builder().build()));
    }

    private BotAccount(UserInfo userInfo) {
        super(userInfo);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String token) {
        this.password = token;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isValid() {
        return super.isValid() && !(this.username == null || this.username.equals("")) && !(this.password == null || this.password.equals(""));
    }

    @Override
    public void save(IConfiguration configuration) {
        configuration.set("account.username", this.username);
        configuration.set("account.password", this.password);
    }
}
