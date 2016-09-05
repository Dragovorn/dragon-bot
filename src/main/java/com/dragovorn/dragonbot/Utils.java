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
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.dragovorn.dragonbot;

import com.google.common.base.CharMatcher;

import java.util.ArrayList;
import java.util.List;

public class Utils {

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