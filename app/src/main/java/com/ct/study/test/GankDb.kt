package com.ct.study.test

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @ClassName : GankDb
 * @CreateDate : 2020/4/15 12:46
 * @Author : CT
 * @Description :
 *
 */
@Database(entities = [User::class], version = 1,exportSchema = false)
abstract class GankDb : RoomDatabase() {

    abstract fun userDao(): UserDao
}