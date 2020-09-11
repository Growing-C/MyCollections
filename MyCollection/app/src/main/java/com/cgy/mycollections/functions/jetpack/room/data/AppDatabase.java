package com.cgy.mycollections.functions.jetpack.room.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import appframe.utils.L;

/**
 * Description : 数据库
 * Author :cgy
 * Date :2019/10/18
 */
@Database(entities = {User.class, Account.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "mydb";

    public abstract UserDao userDao();

    public abstract AccountDao accountDao();

    // For Singleton instantiation
    private static AppDatabase instance;

    public static AppDatabase getInstance(@NonNull Context context) {
        synchronized (AppDatabase.class) {
//            AppDatabase db = Room.databaseBuilder(context.getApplicationContext(),
//                    AppDatabase.class, AppDatabase.DATABASE_NAME).build();
            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                        .addCallback(new RoomDatabase.Callback() {
                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                super.onCreate(db);
                                L.e("AppDatabase getInstance 数据库创建完成 ");
                            }
                        }).build();
            }
        }
        return instance;
    }
}
//override fun onCreate(db:SupportSQLiteDatabase){
//    super.onCreate(db)
//    val request = OneTimeWorkRequestBuilder < SeedDatabaseWorker > ().build()
//    WorkManager.getInstance(context).enqueue(request)
//}