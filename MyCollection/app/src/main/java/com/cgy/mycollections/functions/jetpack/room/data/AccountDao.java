package com.cgy.mycollections.functions.jetpack.room.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * 作者: 陈高阳
 * 创建日期: 2020/9/10 10:34
 * 修改日期: 2020/9/10 10:34
 * 类说明：
 */
@Dao
public interface AccountDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    //    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
    @Query("SELECT * FROM user WHERE name = :name LIMIT 1")
    User findByName(String name);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
