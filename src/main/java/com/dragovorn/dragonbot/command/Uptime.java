package com.dragovorn.dragonbot.command;

import com.dragovorn.dragonbot.api.bot.command.Command;
import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.bot.DragonBot;
import com.dragovorn.dragonbot.bot.User;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 9:29 PM.
 * as of 9/1/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class Uptime extends Command {

    public Uptime() {
        super("uptime", 0, true);
    }

    @Override
    public void execute(User user, String[] args) {
        try {
            JSONObject stream = (JSONObject) DragonBot.getInstance().getTwitchAPI().getStream(Bot.getInstance().getChannel()).get("stream");

            if (stream == null) {
                Bot.getInstance().sendMessage("%s isn\'t live!", Bot.getInstance().getChannel());
                return;
            }

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date parse = format.parse(stream.getString("created_at"));

            long difference = System.currentTimeMillis() - parse.getTime();

            int days = (int) difference / 864000;
            int remainder = (int) difference % 864000;
            int hours = (int) (difference / 3600000);
            remainder = remainder % 3600000;
            int minutes = remainder / 60000;
            remainder = remainder % 60000;
            int seconds = remainder / 1000;

            StringBuilder builder = new StringBuilder();

            if (days > 0) {
                builder.append(days).append(" day").append((days > 1 ? "s " : " "));
            }

            if (hours > 0) {
                builder.append(hours).append(" hour").append((hours > 1 ? "s " : " "));
            }

            if (minutes > 0) {
                builder.append(minutes).append(" minute").append((minutes > 1 ? "s " : " "));
            }

            if (seconds > 0) {
                builder.append(seconds).append(" second").append((seconds > 1 ? "s" : ""));
            }


            Bot.getInstance().sendMessage("%s has been live for %s!", Bot.getInstance().getChannel(), builder.toString().trim());
        } catch (Exception exception) {
            Bot.getInstance().sendMessage("I couldn't connect to the twitch api!");
        }
    }
}
