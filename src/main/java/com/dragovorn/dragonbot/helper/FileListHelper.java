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

package com.dragovorn.dragonbot.helper;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

public class FileListHelper {

    public static File[] sortFileList(File[] files) {
        Arrays.sort(files, CaseInsensitiveFileComparator.INSTANCE);

        return files;
    }

    public static File[] sortFileList(File dir, FilenameFilter filter) {
        File[] files = dir.listFiles(filter);

        return sortFileList(files);
    }

    private enum CaseInsensitiveFileComparator implements Comparator<File> {
        INSTANCE;

        @Override
        public int compare(File file, File file2) {
            return (file != null && file2 != null ? file.getName().compareToIgnoreCase(file2.getName()) : (file == null ? -1 : 1));
        }
    }
}