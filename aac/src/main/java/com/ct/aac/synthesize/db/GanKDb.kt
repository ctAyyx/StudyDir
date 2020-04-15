package com.ct.aac.synthesize.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ct.aac.synthesize.vo.Category

/**
 * @ClassName : GanKDb
 * @CreateDate : 2020/4/15 9:20
 * @Author : CT
 * @Description : 数据库
 *
 */
@Database(entities = [Category::class], version = 1, exportSchema = false)
abstract class GanKDb : RoomDatabase() {

    abstract fun gankDao(): GankDao
}