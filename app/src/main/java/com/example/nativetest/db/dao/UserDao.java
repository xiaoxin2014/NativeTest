package com.example.nativetest.db.dao;

import com.example.nativetest.model.sc.UserBean;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE id=:id")
    LiveData<UserBean> getUserById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserBean userInfo);

    @Query("SELECT * FROM user WHERE id=:id")
    UserBean getUserByIdSync(String id);
}
