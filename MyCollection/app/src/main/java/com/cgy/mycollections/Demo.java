package com.cgy.mycollections;

/**
 * Created by RB-cgy on 2015/12/18.
 */
public class Demo {
    public Demo(int tId, int iId, Class c) {
        this.titleId = tId;
        this.infoId = iId;
        this.c = c;
        this.hasChild = false;//默认没有二级内容
    }

    public Demo(int tId, int iId, boolean hasChild, Class c) {
        this.titleId = tId;
        this.infoId = iId;
        this.c = c;
        this.hasChild = hasChild;
    }

    public int titleId;
    public int infoId;
    public Class c;
    public boolean hasChild;//是否有二级目录
}
