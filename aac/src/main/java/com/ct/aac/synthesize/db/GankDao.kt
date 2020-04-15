package com.ct.aac.synthesize.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ct.aac.synthesize.vo.Category

/**
 * @ClassName : GankDao
 * @CreateDate : 2020/4/15 9:19
 * @Author : CT
 * @Description : 数据Dao层
 *
 */
@Dao
interface GankDao {

    /**
     * 返回 可观察数据
     * */
    @Query(
        """
        SELECT * FROM CATEGORY
    """
    )
    fun getCategory(): LiveData<List<Category>>

    /**
     * 返回 边界数据
     * */
    @Query(
        """
        SELECT * FROM CATEGORY
    """
    )
    fun getCategory02(): DataSource.Factory<Int, Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(vararg category: Category)
}