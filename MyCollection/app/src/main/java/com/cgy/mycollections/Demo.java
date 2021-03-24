package com.cgy.mycollections;

/**
 * Created by RB-cgy on 2015/12/18.
 */
public class Demo {
    String mClassPath;

    public Demo(int tId, int iId, String classPath) {
        this.titleId = tId;
        this.infoId = iId;
        this.c = getClassByPath(classPath);
        this.hasChild = false;//默认没有二级内容
        this.mClassPath = classPath;
    }

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

    public Demo(int tId, int iId, boolean hasChild, String classPath) {
        this.titleId = tId;
        this.infoId = iId;
        this.c = getClassByPath(classPath);
        this.hasChild = hasChild;
        this.mClassPath = classPath;
    }

    public int titleId;
    public int infoId;
    private Class<?> c;
    public boolean hasChild;//是否有二级目录

    public Class<?> getDemoClass() {
        return c;
    }

    private Class<?> getClassByPath(String path) {
        try {
            c = Class.forName(path);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return c;
    }

    public String getClassPath() {
        return mClassPath;
    }
}
