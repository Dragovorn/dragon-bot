package com.dragovorn.dragonbot.helper;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 5:54 PM.
 * as of 8/7/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
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