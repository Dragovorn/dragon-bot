package com.dragovorn.dragonbot.command;

import com.dragovorn.dragonbot.api.bot.command.Command;
import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.bot.User;

public class Github extends Command {

    public Github() {
        super("github", 0, true);
    }

    @Override
    public void execute(User user, String[] args) {
        Bot.getInstance().sendMessage("Dragon Bot %s | You can find my code on GitHub: https://github.com/dragovorn/dragon-bot-twitch", Bot.getInstance().getVersion());
    }
}