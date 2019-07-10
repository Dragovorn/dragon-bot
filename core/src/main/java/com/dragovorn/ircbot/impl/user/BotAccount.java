package com.dragovorn.ircbot.impl.user;

import com.dragovorn.ircbot.api.config.IConfiguration;
import com.dragovorn.ircbot.api.config.IConfigurationSerializable;

// This class isn't exposed in the API, should make storing the bot account a little more secure.
public class BotAccount extends User implements IConfigurationSerializable {

    private String password;

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
