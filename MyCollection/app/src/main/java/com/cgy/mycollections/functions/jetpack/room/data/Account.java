package com.cgy.mycollections.functions.jetpack.room.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 作者: cgy
 * 创建日期: 2020/9/10 10:33
 * 修改日期: 2020/9/10 10:33
 * 类说明：各种各样的账户
 */
@Entity
public class Account {

    @PrimaryKey
    public int uid;

    @ColumnInfo
    public String accountName;

    @ColumnInfo
    public String password;
}
