package com.cgy.mycollections.functions.sqlite.db;


import java.util.List;

import io.reactivex.Observable;

/**
 * Description :数据库操作
 * Author :cgy
 * Date :2018/10/16
 */
public interface IDBOperate {

    //--------------------用户账户操作----------------------

    /**
     * 添加用户帐号信息到本地数据库（包含更新用户账户信息功能）
     *
     * @param account
     * @return
     */
    Observable<Boolean> addUserAccount(UserAccount account);
    //--------------------用户账户操作----------------------

    /**
     * 添加用户Key到本地数据库
     *
     * @param account
     * @return
     */
    Observable<Boolean> addUserKey(UserAccount account);

    /**
     * 删除本地用户账户信息
     *
     * @param account
     * @return
     */
    Observable<Boolean> deleteUserAccount(UserAccount account);

    /**
     * 删除所有用户数据
     *
     * @return
     */
    Observable<Boolean> deleteAllUserAccount();

    /**
     * 获取本地用户账户信息,
     *
     * @param account 用户账户，如果为空则默认返回最新的一个用户
     * @return
     */
    Observable<UserAccount> getUserAccountInfo(String account);

    /**
     * 获取本地用户key信息,
     *
     * @param account 用户key，如果为空则默认返回最新的一个用户
     * @return
     */
    Observable<UserAccount> getUserKeyInfo(String account);

}
