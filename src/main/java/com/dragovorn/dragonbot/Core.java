package com.dragovorn.dragonbot;

import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.bot.DragonBot;
import com.dragovorn.dragonbot.gui.MainWindow;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 3:43 PM.
 * as of 8/5/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class Core {

    public static void main(String[] args) throws Exception {
        DragonBot bot = new DragonBot();

        Bot.setInstance(bot);
        bot.start();

        new MainWindow();
    }
}