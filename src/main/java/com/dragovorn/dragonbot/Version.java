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

import com.dragovorn.dragonbot.gui.MainWindow;
import com.dragovorn.dragonbot.util.FileUtil;

import java.io.IOException;
import java.util.Properties;

public class Version {

    private String version;

    Version() {
        Properties properties = new Properties();

        try {
            properties.load(FileUtil.getResource("project.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.version = properties.getProperty("version");

        MainWindow.TITLE = String.format(MainWindow.TITLE, this.version);
    }

    public String getVersion() {
        return this.version;
    }

    public static boolean shouldUpdate(String version1, String version2) {
        try {
            String[] v1Strs = version1.split("\\.");
            String[] v2Strs = version2.split("\\.");

            int[] v1Nums = new int[] {Integer.valueOf(v1Strs[0]), Integer.valueOf(v1Strs[1]), Integer.valueOf(v1Strs[2])};
            int[] v2Nums = new int[] {Integer.valueOf(v2Strs[0]), Integer.valueOf(v2Strs[1]), Integer.valueOf(v2Strs[2])};

            for (int x = 0; x < v1Nums.length; x++) {
                if (v1Nums[x] < v2Nums[x]) {
                    return true;
                } else if (v1Nums[x] > v2Nums[x]) {
                    return false;
                }
            }

            return false;
        } catch (NumberFormatException exception) {
            DragonBot.getInstance().getLogger().warning("Legacy versioning detected, ignoring...");

            return false;
        }
    }
}