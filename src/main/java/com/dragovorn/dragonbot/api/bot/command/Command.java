/*
 * Copyright (c) 2016. Andrew Burr
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 *  associated documentation files (the "Software"), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge, publish, distribute,
 *  sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE..
 */

package com.dragovorn.dragonbot.api.bot.command;

import com.dragovorn.dragonbot.bot.User;

public abstract class Command {

    private final boolean argsRequired;

    private final int requiredArgs;

    private final String name;

    public Command(String name, int requiredArgs, boolean argsRequired) {
        this.name = name;
        this.requiredArgs = requiredArgs;
        this.argsRequired = argsRequired;
    }

    public final boolean isArgsRequired() {
        return argsRequired;
    }

    public final int getArgs() {
        return requiredArgs;
    }

    public final String getName() {
        return name;
    }

    public abstract void execute(User user, String[] args);
}