/*
 * Copyright (c) 2017. Andrew Burr
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

import com.dragovorn.dragonbot.bot.Version;
import org.junit.Test;

public class TestVersionChecker {

    @Test
    public void testVersionChecker() {
        String ourVersion = Version.VERSION;
        String theirVersion = "v0.7.0";
        String[] ourNumbersStr = ourVersion.split("\\.");
        String[] theirNumbersStr = theirVersion.substring(1).split("\\.");

        int[] ourNumbers = new int[] {Integer.valueOf(ourNumbersStr[0]), Integer.valueOf(ourNumbersStr[1]), Integer.valueOf(ourNumbersStr[2])};
        int[] theirNumbers = new int[] {Integer.valueOf(theirNumbersStr[0]), Integer.valueOf(theirNumbersStr[1]), Integer.valueOf(theirNumbersStr[2])};

        for (int x = 0; x < ourNumbers.length; x++) {
            if (ourNumbers[x] < theirNumbers[x]) {
                System.out.println("update!");
                return;
            } else if (ourNumbers[x] > theirNumbers[x]) {
                System.out.println("no update!");
                return;
            }
        }

        System.out.println("same version");
    }
}