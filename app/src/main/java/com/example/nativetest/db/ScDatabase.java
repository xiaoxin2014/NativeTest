package com.example.nativetest.db;

import com.example.nativetest.db.dao.UserDao;
import com.example.nativetest.model.sc.UserBean;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {UserBean.class, }, version = 1, exportSchema = false)
//@TypeConverters(cn.rongcloud.im.db.TypeConverters.class)
public abstract class ScDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();

}
