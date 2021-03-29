package com.growingc.mediaoperator.beans;

import java.io.Serializable;

/**
 * author: chengy
 * created on: 2020-12-14 11:20
 * description:排序信息
 */
public class SortInfo implements Serializable {


    public enum SortType {
        NAME,//名称排序
        FILE_SIZE,//文件大小排序
        CREATE_TIME,//文件创建时间排序
    }

    private SortType sortType;//排序方式
    private boolean isAscending;//是否是升序

    public SortInfo() {
    }

    public SortInfo(SortType sortType, boolean isAscending) {
        setSortType(sortType);
        setAscending(isAscending);
    }

    /**
     * 默认的排序，名称升序
     *
     * @return 排序信息
     */
    public static SortInfo getDefault() {
        return new SortInfo(SortType.NAME, true);
    }

    public SortType getSortType() {
        return sortType;
    }

    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    public boolean isAscending() {
        return isAscending;
    }

    public void setAscending(boolean ascending) {
        isAscending = ascending;
    }
}
