package com.cgy.mycollections.functions.sqlite.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;


import com.cgy.mycollections.MyApplication;
import com.cgy.mycollections.functions.file.FileInfo;
import com.cgy.mycollections.utils.L;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.cgy.mycollections.functions.sqlite.db.DBHelper.TABLE_NAME_PROTECTED_FILES;
import static com.cgy.mycollections.functions.sqlite.db.DBHelper.TABLE_NAME_USER_ACCOUNT;
import static com.cgy.mycollections.functions.sqlite.db.DBHelper.TABLE_NAME_USER_KEY;

/**
 * Description :数据库操作者
 * Author :cgy
 * Date :2018/10/16
 */
public class DBOperator implements IDBOperate {
    private static final String TAG = DBOperator.class.getSimpleName();
    private static DBOperator mInstance;

    public static DBOperator getInstance() {
        if (mInstance == null) {
            mInstance = new DBOperator(MyApplication.getInstance());
        }
        return mInstance;
    }

    private DBHelper mDBHelper;

    private DBOperator(Context context) {
        mDBHelper = new DBHelper(context);
    }


    @Override
    public Observable<Boolean> addProtectedFile(final String userId, final FileInfo fileInfo) {
        return applySchedulers(Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                if (TextUtils.isEmpty(userId) || fileInfo == null) {
                    e.onError(new NullPointerException("userid/fileInfo is null"));
                    return;
                }
                L.d("start addProtectedFile");
                mDBHelper.startUsingDatabase();
                SQLiteDatabase db = mDBHelper.getWritableDatabase();
                db.execSQL("delete from " + TABLE_NAME_PROTECTED_FILES + " where USER_ID=\"" +
                        userId + "\" and FILE_PATH = \"" + fileInfo.filePath + "\";");//先删除 再添加，实现更新
                String[] strData = new String[DBHelper.ENUM_PROTECTED_FILES.values().length];


                strData[0] = userId;
                strData[1] = fileInfo.filePath;
                strData[2] = fileInfo.getFileType();
                strData[3] = String.valueOf(System.currentTimeMillis());
                strData[4] = String.valueOf(fileInfo.protectState);

                String sql = getInsertSql(TABLE_NAME_PROTECTED_FILES, strData);
                db.execSQL(sql);
                mDBHelper.closeDatabase();

                e.onNext(true);
                e.onComplete();
            }
        }));
    }

    @Override
    public Observable<Boolean> addProtectedFiles(final String userId, final List<FileInfo> fileList) {
        return applySchedulers(Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                L.d(TAG, "start addProtectedFiles");
                mDBHelper.startUsingDatabase();
                SQLiteDatabase db = mDBHelper.getWritableDatabase();
                try {
                    db.beginTransaction();
                    for (FileInfo fileInfo : fileList) {
                        db.execSQL("delete from " + TABLE_NAME_PROTECTED_FILES + " where USER_ID=\"" +
                                userId + "\" and FILE_PATH = \"" + fileInfo.filePath + "\";");// 删除 保护的文件

                        String[] strData = new String[DBHelper.ENUM_PROTECTED_FILES.values().length];

                        strData[0] = userId;
                        strData[1] = fileInfo.filePath;
                        strData[2] = fileInfo.getFileType();
                        strData[3] = String.valueOf(System.currentTimeMillis());
                        strData[4] = String.valueOf(fileInfo.protectState);

                        String sql = getInsertSql(TABLE_NAME_PROTECTED_FILES, strData);
                        db.execSQL(sql);
                    }
                    db.setTransactionSuccessful();
                    db.endTransaction();
                } catch (Exception ex) {
                    e.onError(ex);
                }
                mDBHelper.closeDatabase();

                e.onNext(true);
                e.onComplete();
            }
        }));
    }

    @Override
    public Observable<Boolean> removeProtectedFile(final String userId, final FileInfo fileInfo) {
        return applySchedulers(Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                L.d("start removeProtectedFile");
                mDBHelper.startUsingDatabase();
                SQLiteDatabase db = mDBHelper.getWritableDatabase();
                db.execSQL("delete from " + TABLE_NAME_PROTECTED_FILES + " where USER_ID=\"" +
                        userId + "\" and FILE_PATH = \"" + fileInfo.filePath + "\";");// 删除 保护的文件
                mDBHelper.closeDatabase();

                e.onNext(true);
                e.onComplete();
            }
        }));
    }

    @Override
    public Observable<Boolean> removeProtectedFiles(final String userId, final List<FileInfo> fileList) {
        return applySchedulers(Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                if (TextUtils.isEmpty(userId) || fileList == null) {
                    e.onError(new NullPointerException("userid/fileInfo is null"));
                    return;
                }
                L.d(TAG, "start addLockList");
                mDBHelper.startUsingDatabase();
                SQLiteDatabase db = mDBHelper.getWritableDatabase();
                try {
                    db.beginTransaction();
                    for (FileInfo fileInfo : fileList) {
                        db.execSQL("delete from " + TABLE_NAME_PROTECTED_FILES + " where USER_ID=\"" +
                                userId + "\" and FILE_PATH = \"" + fileInfo.filePath + "\";");// 删除 保护的文件
                    }
                    db.setTransactionSuccessful();
                    db.endTransaction();
                    e.onNext(true);
                } catch (Exception ex) {
                    e.onError(ex);
                }
                mDBHelper.closeDatabase();

                e.onComplete();
            }
        }));
    }

    @Override
    public Observable<List<FileInfo>> getProtectedFiles(final String userId) {
        return applySchedulers(Observable.create(new ObservableOnSubscribe<List<FileInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<List<FileInfo>> e) throws Exception {
                L.d(TAG, "start getAllLockInfo");
                mDBHelper.startUsingDatabase();
                SQLiteDatabase db = mDBHelper.getReadableDatabase();
                String querySql = "select * from " + TABLE_NAME_PROTECTED_FILES;

                if (!TextUtils.isEmpty(userId)) {//不为空则添加查询条件
                    querySql += " where USER_ID = \"" + userId + "\"";
                }
                querySql += ";";

                List<FileInfo> fileList = new ArrayList<>();
                Cursor c = null;
                try {
                    c = db.rawQuery(querySql, null);
                    L.d(TAG, "getProtectedFiles   count:" + c.getCount());
                    while (c.moveToNext()) {
                        String filePath = c.getString(1);

                        File file = new File(filePath);
                        FileInfo fileInfo = new FileInfo(file);
                        fileInfo.addProtectDate = c.getLong(3);
                        fileInfo.protectState = c.getInt(4);

                        fileList.add(fileInfo);
                    }
                } catch (Exception ex) {
                    e.onError(ex);
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
                mDBHelper.closeDatabase();

                e.onNext(fileList);
                e.onComplete();
            }
        }));
    }

    @Override
    public Observable<Boolean> addUserAccount(@NonNull final UserAccount account) {
        return applySchedulers(Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                L.d("start addUserAccount");
                mDBHelper.startUsingDatabase();
                SQLiteDatabase db = mDBHelper.getWritableDatabase();
                db.execSQL("delete from " + TABLE_NAME_USER_ACCOUNT + " where USER_ID=\"" +
                        account.getUid() + "\";");//先删除 再添加，实现更新
                String[] strData = new String[DBHelper.ENUM_USER_ACCOUNT.values().length];
                strData[0] = account.getUid();
                strData[1] = account.getPhone();
                strData[2] = account.getAct();
                strData[3] = account.getName();
                strData[4] = account.getSex();
                strData[5] = account.getEmail();
                String sql = getInsertSql(TABLE_NAME_USER_ACCOUNT, strData);
                db.execSQL(sql);
                mDBHelper.closeDatabase();

                e.onNext(true);
                e.onComplete();
            }
        }));
    }

    @Override
    public Observable<Boolean> addUserKey(@NonNull final UserAccount account) {
        return applySchedulers(Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                L.d("start addUserKey");
                mDBHelper.startUsingDatabase();
                SQLiteDatabase db = mDBHelper.getWritableDatabase();
                db.execSQL("delete from " + TABLE_NAME_USER_KEY + " where USER_ID=\"" +
                        account.getUid() + "\";");//先删除 再添加，实现更新
                String[] strData = new String[DBHelper.ENUM_USER_KEY.values().length];
                strData[0] = account.getUid();
                strData[1] = account.getPublicKey();
                strData[2] = account.getPrivateKey();
                String sql = getInsertSql(TABLE_NAME_USER_KEY, strData);
                db.execSQL(sql);
                mDBHelper.closeDatabase();
                e.onNext(true);
                e.onComplete();
            }
        }));
    }

    @Override
    public Observable<Boolean> deleteUserAccount(@NonNull final UserAccount account) {
        return applySchedulers(Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                L.d("start deleteUserAccount");
                mDBHelper.startUsingDatabase();
                SQLiteDatabase db = mDBHelper.getWritableDatabase();
                db.execSQL("delete from " + TABLE_NAME_USER_ACCOUNT + " where USER_ID=\"" +
                        account.getUid() + "\";");// 删除 账户信息
                mDBHelper.closeDatabase();

                e.onNext(true);
                e.onComplete();
            }
        }));
    }

    @Override
    public Observable<Boolean> deleteAllUserAccount() {
        return applySchedulers(Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                L.d("start deleteUserAccount");
                mDBHelper.startUsingDatabase();
                SQLiteDatabase db = mDBHelper.getWritableDatabase();
                db.execSQL("delete from " + TABLE_NAME_USER_ACCOUNT + ";");// 删除 账户信息
                mDBHelper.closeDatabase();

                e.onNext(true);
                e.onComplete();
            }
        }));
    }

    @Override
    public Observable<UserAccount> getUserAccountInfo(final String uid) {
        return applySchedulers(Observable.create(new ObservableOnSubscribe<UserAccount>() {
            @Override
            public void subscribe(ObservableEmitter<UserAccount> e) throws Exception {
                L.d("start getUserAccountInfo");
                mDBHelper.startUsingDatabase();
                SQLiteDatabase db = mDBHelper.getReadableDatabase();
                String querySql = "select * from " + TABLE_NAME_USER_ACCOUNT + " left join " + TABLE_NAME_USER_KEY +
                        " on " + TABLE_NAME_USER_ACCOUNT + ".USER_ID = " + TABLE_NAME_USER_KEY + ".USER_ID";

                if (!TextUtils.isEmpty(uid)) {//不为空则添加查询条件
                    querySql += " where " + TABLE_NAME_USER_ACCOUNT + ".USER_ID = \"" + uid + "\"";
                }
                querySql += ";";
                UserAccount user = null;
                Cursor c = null;
                try {
                    c = db.rawQuery(querySql, null);
                    L.d("getUserAccountInfo account count:" + c.getCount());
                    if (c.moveToNext()) {
                        user = new UserAccount();
                        user.setUid(c.getString(0));
                        user.setPhone(c.getString(1));
                        user.setAct(c.getString(2));
                        user.setName(c.getString(3));
                        user.setSex(c.getString(4));
                        user.setEmail(c.getString(5));

                        user.setPublicKey(c.getString(7));//publicKey
                        user.setPrivateKey(c.getString(8));//privateKey
                    }
                } catch (Exception ex) {
                    e.onError(ex);
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
                mDBHelper.closeDatabase();
                if (user == null)
                    e.onError(new NullPointerException("user is null"));
                else
                    e.onNext(user);
                e.onComplete();
            }
        }));
    }

    @Override
    public Observable<UserAccount> getUserKeyInfo(final String uid) {
        return applySchedulers(Observable.create(new ObservableOnSubscribe<UserAccount>() {
            @Override
            public void subscribe(ObservableEmitter<UserAccount> e) throws Exception {
                L.d("start getUserKeyInfo");
                mDBHelper.startUsingDatabase();
                SQLiteDatabase db = mDBHelper.getReadableDatabase();
                String querySql = "select * from " + TABLE_NAME_USER_KEY;

                if (!TextUtils.isEmpty(uid)) {//不为空则添加查询条件
                    querySql += " where USER_ID = \"" + uid + "\"";
                }
                querySql += ";";
                UserAccount user = new UserAccount();
                Cursor c = null;
                try {
                    c = db.rawQuery(querySql, null);
                    L.d("getUserKeyInfo key count:" + c.getCount());
                    if (c.moveToNext()) {
                        user.setUid(c.getString(0));
                        user.setPublicKey(c.getString(1));
                        user.setPrivateKey(c.getString(2));
                    }
                } catch (Exception ex) {
                    e.onError(ex);
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
                mDBHelper.closeDatabase();
                e.onNext(user);
                e.onComplete();
            }
        }));
    }

    /**
     * 插入语句
     *
     * @param tableName
     * @param strData
     * @return
     */
    private String getInsertSql(String tableName, String[] strData) {
        String sql = "insert into " + tableName + " values(";
        for (String str : strData) {
            sql += "\"" + str + "\",";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += ");";
        return sql;
    }

    /**
     * 线程调度，线程中操作，主线程观察
     *
     * @param responseObservable
     * @param <T>
     * @return
     */
    protected <T> Observable<T> applySchedulers(Observable<T> responseObservable) {
        return responseObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
