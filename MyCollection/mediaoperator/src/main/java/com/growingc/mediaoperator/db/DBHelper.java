package com.growingc.mediaoperator.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;

import appframe.utils.L;


/**
 * 数据库
 */
class DBHelper extends SQLiteOpenHelper {
    final static String DB_NAME = "bclbasic.db";
    final static int DB_VER = 2;

    public final static String TABLE_NAME_USER_ACCOUNT = "TABLE_NAME_USER_ACCOUNT";//用户账户表
    public final static String TABLE_NAME_USER_KEY = "TABLE_NAME_USER_KEY";//用户KEY表
    public final static String TABLE_NAME_PROTECTED_FILES = "protected_files";//受保护的文件表

    /**
     * 用户账户
     */
    public enum ENUM_USER_ACCOUNT {
        USER_ID, PHONE, NAME,
        SEX, EMAIL, ACCOUNT
    }

    /**
     * 用户账户Key
     */
    public enum ENUM_USER_KEY {
        USER_ID, PUBLIC_KEY,
        PRIVATE_KEY
    }

    /**
     * 受保护文件
     */
    public enum ENUM_PROTECTED_FILES {
        USER_ID, FILE_PATH,
        FILE_TYPE, ADD_PROTECT_DATE, PROTECTION_STATE
    }


    protected DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        L.d("onCreate DB!");
        createTables(db);
    }

    private void createTables(SQLiteDatabase db) {
        String[] sql = new String[3];
        sql[0] = "CREATE TABLE if not exists [" + TABLE_NAME_USER_ACCOUNT + "] (" + "["
                + ENUM_USER_ACCOUNT.USER_ID.toString() + "] TEXT NOT NULL, " + "["
                + ENUM_USER_ACCOUNT.PHONE.toString() + "] TEXT NOT NULL, " + "["
                + ENUM_USER_ACCOUNT.ACCOUNT.toString() + "] TEXT, " + "["
                + ENUM_USER_ACCOUNT.NAME.toString() + "] TEXT, " + "["
                + ENUM_USER_ACCOUNT.SEX.toString() + "] TEXT," + "["
                + ENUM_USER_ACCOUNT.EMAIL.toString() + "] TEXT " + ")";


        sql[1] = "CREATE TABLE if not exists [" + TABLE_NAME_USER_KEY + "] (" + "["
                + ENUM_USER_KEY.USER_ID.toString() + "] TEXT NOT NULL, " + "["
                + ENUM_USER_KEY.PUBLIC_KEY.toString() + "] TEXT NOT NULL, " + "["
                + ENUM_USER_KEY.PRIVATE_KEY.toString() + "] TEXT NOT NULL );";

        sql[2] = "CREATE TABLE if not exists [" + TABLE_NAME_PROTECTED_FILES + "] (" + "["
                + ENUM_PROTECTED_FILES.USER_ID.toString() + "] TEXT NOT NULL, " + "["
                + ENUM_PROTECTED_FILES.FILE_PATH.toString() + "] TEXT NOT NULL, " + "["
                + ENUM_PROTECTED_FILES.FILE_TYPE.toString() + "] TEXT NOT NULL, " + "["
                + ENUM_PROTECTED_FILES.ADD_PROTECT_DATE.toString() + "] LONG, " + "["
                + ENUM_PROTECTED_FILES.PROTECTION_STATE.toString() + "] INTEGER NOT NULL );";

        try {
            for (String sql1 : sql) {
                db.execSQL(sql1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            L.e(newVersion + "------onUpgrade------" + oldVersion);
            String sql1 = "CREATE TABLE if not exists [" + TABLE_NAME_PROTECTED_FILES + "] (" + "["
                    + ENUM_PROTECTED_FILES.USER_ID.toString() + "] TEXT NOT NULL, " + "["
                    + ENUM_PROTECTED_FILES.FILE_PATH.toString() + "] TEXT NOT NULL, " + "["
                    + ENUM_PROTECTED_FILES.FILE_TYPE.toString() + "] TEXT NOT NULL, " + "["
                    + ENUM_PROTECTED_FILES.ADD_PROTECT_DATE.toString() + "] LONG, " + "["
                    + ENUM_PROTECTED_FILES.PROTECTION_STATE.toString() + "] INTEGER NOT NULL );";
            try {
                db.execSQL(sql1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private byte[] mutex = new byte[1];//数据库打开关闭锁
    private AtomicInteger mOpenCounter = new AtomicInteger();//数据库打开次数统计，该实例线程安全

    /**
     * 开始使用数据库标志与{@link #closeDatabase}搭配使用来管理数据库的关闭,<br>
     * 一般应该在getWritableDatabase或getReadableDatabase调用前调用
     */
    SQLiteDatabase startUsingDatabase(boolean isWrite) {
        int count = mOpenCounter.incrementAndGet();
        L.d("start database for " + count);
        return isWrite ? getWritableDatabase() : getReadableDatabase();
    }

    /**
     * 关闭数据库，仅在数据库没有在被占用的情况下才会关闭
     */
    void closeDatabase() {
        synchronized (mutex) {
            L.d("close database count: " + mOpenCounter.get());
            if (mOpenCounter.decrementAndGet() == 0) {
                L.d("closeDatabase is closing~~");
                close();
                L.d("closeDatabase close ok!");
            }
        }
    }
}
