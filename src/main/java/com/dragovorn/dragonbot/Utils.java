package com.dragovorn.dragonbot;

import com.google.common.base.CharMatcher;
import com.sun.istack.internal.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 7:22 AM.
 * as of 8/6/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class Utils {

    @Nullable
    public static String parseCommand(@NotNull String prefix, @NotNull String message) {
        if (message.startsWith(prefix)) {
            return message.substring(prefix.length());
        }

        return null;
    }

    public static List<String> tokenizeLine(String input) {
        List<String> parts = new ArrayList<>();

        if (input == null || input.length() == 0) {
            return parts;
        }

        String trimmedInput = CharMatcher.WHITESPACE.trimFrom(input);
        int pos = 0;
        int end;

        while ((end = trimmedInput.indexOf(' ', pos)) >= 0) {
            parts.add(trimmedInput.substring(pos, end));
            pos = end + 1;

            if (trimmedInput.charAt(pos) == ':') {
                parts.add(trimmedInput.substring(pos + 1));
                return parts;
            }
        }

        parts.add(trimmedInput.substring(pos));

        return parts;
    }
}