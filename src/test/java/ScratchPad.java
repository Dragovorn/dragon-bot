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

import com.dragovorn.dragonbot.api.github.GitHubAPI;
import com.dragovorn.dragonbot.bot.Bot;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.fail;

public class ScratchPad {

    @Test
    public void scratchPad() {
        GitHubAPI api = new GitHubAPI("dragovorn", "dragon-bot-twitch", false);

        String version = "v1.05d";

        try {
            Map<String, String> releases = api.getReleases();

            for (Map.Entry<String, String> entry : releases.entrySet()) {
                double newVersion = Double.valueOf(entry.getKey().substring(0, 4));
                double botVersion = Double.valueOf(version.substring(1, 5));

                char newPatch = entry.getKey().charAt(4);
                char botPatch = version.charAt(5);

                int newSnapshot = 0;
                int oldSnapshot = 0;

                if (false) {
                    if (entry.getKey().contains("SNAPSHOT")) {
                        newSnapshot = Integer.valueOf(entry.getKey().split("-")[1]);
                    }

                    if (Bot.getInstance().getVersion().contains("SNAPSHOT")) {
                        oldSnapshot = Integer.valueOf(Bot.getInstance().getVersion().split("-")[1]);
                    }
                }

                if (newVersion > botVersion) {
                    System.out.println("New version greater than bot version.");
                    fail();
                } else if (newVersion == botVersion) {
                    if (newPatch > botPatch) {
                        System.out.println("New patch greater than bot patch.");
                        fail();
                    } else if (newSnapshot == 0 && (oldSnapshot != 0 && newSnapshot > oldSnapshot)) {
                        System.out.println("New snapshot greater than bot snapshot.");
                        fail();
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}