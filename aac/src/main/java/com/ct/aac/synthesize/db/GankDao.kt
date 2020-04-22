package com.ct.aac.synthesize.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.room.*
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

    @Query(
        """
        SELECT * FROM CATEGORY
        LIMIT :size OFFSET :page
    """
    )
    fun getCategoryByPage(size: Int, page: Int): List<Category>

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

    @Query(
        """
        DELETE FROM Category
    """
    )
    fun clearCategory()
}