package com.dragovorn.dragonbot.user;

import com.dragovorn.ircbot.api.user.UserInfo;
import com.dragovorn.ircbot.impl.user.User;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class TwitchUser extends User {

    private final long userId;

    private final List<String> badges;

    TwitchUser(UserInfo userInfo) {
        super(userInfo);

        if (this.tags.isEmpty()) {
            this.userId = 0L;
            this.badges = null;
            return;
        }

        this.userId = Long.valueOf(getTag("user-id"));

        String[] badges = getTag("badges").split(",");

        this.badges = Arrays.stream(badges)
                .map(s -> s.substring(0, s.indexOf("/")))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid() {
        return super.isValid() && this.userId != 0 && this.badges != null;
    }

    public boolean isBroadcaster() {
        return this.badges.contains("broadcaster");
    }

    public boolean isSubscriber() {
        return Integer.valueOf(getTag("subscriber")) == 1;
    }

    public boolean isModerator() {
        return this.badges.contains("broadcaster") || this.badges.contains("moderator");
    }

    public long getUserId() {
        return this.userId;
    }

    public String getDisplayName() {
        return getTag("display-name");
    }

    public List<String> getBadges() {
        return this.badges;
    }
}
