package com.cgy.mycollections.functions.jetpack.room.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Description : 用户对象操纵者
 * Author :cgy
 * Date :2019/10/18
 */
@Dao
public interface UserDao {
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