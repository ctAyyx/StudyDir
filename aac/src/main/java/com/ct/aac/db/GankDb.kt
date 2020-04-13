package com.ct.aac.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ct.aac.vo.Category

@Database(entities = [Category::class], version = 1)
abstract class GankDb : RoomDatabase() {
}