package com.cgy.mycollections.functions.sqlite.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;


import com.cgy.mycollections.MyApplication;
import com.cgy.mycollections.utils.L;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.cgy.mycollections.functions.sqlite.db.DBHelper.TABLE_NAME_USER_ACCOUNT;
import static com.cgy.mycollections.functions.sqlite.db.DBHelper.TABLE_NAME_USER_KEY;

/**
 * Description :数据库操作者
 * Author :cgy
 * Date :2018/10/16
 */
public class DBOperator implements IDBOperate {
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
