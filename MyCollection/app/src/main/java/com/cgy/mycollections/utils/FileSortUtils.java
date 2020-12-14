package com.cgy.mycollections.utils;

import com.cgy.mycollections.functions.file.FileInfo;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * author: chengy
 * created on: 2020-12-14 11:35
 * description: 文件排序工具
 */
public class FileSortUtils {

    /**
     * 根据名称排序
     *
     * @param fileList    文件list
     * @param isAscending true-升序 false -降序
     * @return
     */
    public static List<FileInfo> sortFileInfoListByName(List<FileInfo> fileList, boolean isAscending) {
        if (fileList != null) {
            if (isAscending) {
                Collections.sort(fileList, new Comparator<FileInfo>() {
                    @Override
                    public int compare(FileInfo o1, FileInfo o2) {
                        return o1.getFileName().toUpperCase().compareTo(o2.getFileName().toUpperCase());
                    }
                });
            } else {
                Collections.sort(fileList, new Comparator<FileInfo>() {
                    @Override
                    public int compare(FileInfo o1, FileInfo o2) {
                        return o2.getFileName().toUpperCase().compareTo(o1.getFileName().toUpperCase());
                    }
                });
            }
        }
        return fileList;
    }

    /**
     * 根据名称排序
     *
     * @param fileList    文件list
     * @param isAscending true-升序 false -降序
     * @return
     */
    public static List<File> sortFileListByName(List<File> fileList, boolean isAscending) {
        if (fileList != null) {
            if (isAscending) {
                Collections.sort(fileList, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        return o1.getName().toUpperCase().compareTo(o2.getName().toUpperCase());
                    }
                });
            } else {
                Collections.sort(fileList, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        return o2.getName().toUpperCase().compareTo(o1.getName().toUpperCase());
                    }
                });
            }
        }
        return fileList;
    }
}
