package com.cgy.mycollections.functions.jetpack.room.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Description : 用户对象
 * Author :cgy
 * Date :2019/10/18
 */
@Entity
public class User {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "age")
    public Integer age;
}
