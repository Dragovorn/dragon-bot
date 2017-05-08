/*
 * Copyright (c) 2016. Andrew Burr
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 *  associated documentation files (the "Software"), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge, publish, distribute,
 *  sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or
 *  substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 *  INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 *  PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 *  COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 *  ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 *  THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.dragovorn.dragonbot.command;

import com.dragovorn.dragonbot.api.bot.command.Command;
import com.dragovorn.dragonbot.api.bot.plugin.BotPlugin;
import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.DragonBot;
import com.dragovorn.dragonbot.bot.User;
import com.sun.istack.internal.NotNull;

public class VersionCmd extends Command {

    public VersionCmd() {
        super("version", -1, false);
    }

    @Override
    public void execute(@NotNull User user, @NotNull String[] args) {
        StringBuilder builder = new StringBuilder();

        for (int x = 1; x < args.length; x++) {
            builder.append(args[x]).append(" ");
        }

        String name = builder.toString().trim();

        for (BotPlugin plugin : DragonBot.getInstance().getPluginManager().getPlugins().values()) {
            if (plugin.getInfo().getName().trim().equalsIgnoreCase(name)) {
                Bot.getInstance().sendMessage("%s - Version: %s, Created by: %s", plugin.getInfo().getName(), plugin.getInfo().getVersion(), plugin.getInfo().getAuthor());
                return;
            }
        }
    }
}