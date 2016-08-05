package com.dragovorn.dragonbot;

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
    }
}