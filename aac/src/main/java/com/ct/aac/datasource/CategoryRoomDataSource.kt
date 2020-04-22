package com.ct.aac.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.ct.aac.synthesize.db.GankDao
import com.ct.aac.synthesize.vo.Category

/**
 * @ClassName : CategoryRoomDataSource
 * @CreateDate : 2020/4/17 13:56
 * @Author : CT
 * @Description :
 *
 */
class CategoryRoomDataSource(val dao: GankDao) : PageKeyedDataSource<Int, Category>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Category>
    ) {
        val list = dao.getCategoryByPage(5, 0)
        callback.onResult(list, 1, 1)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Category>) {

        val list = dao.getCategoryByPage(5, params.key*5)
        Log.e("TAG", "ROOm获取跟多数据 ${params.key}--$list")
        if (list.isNullOrEmpty() || list.size < 5) {
            //没有数据了 获取网络数据 加入数据库
            Thread.sleep(3000)
            val result = buildCategory()
            dao.insertCategory(*result.toTypedArray())
            val newList = dao.getCategoryByPage(5, params.key*5)
            callback.onResult(newList, params.key + 1)
        } else
            callback.onResult(list, params.key + 1)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Category>) {
    }

    fun buildCategory() = mutableListOf<Category>().apply {

        (0..10).forEach {
            add(Category().apply {
                id = "${System.currentTimeMillis()}_$it"
                url = "http://gank.io/images/28fc02e86d584ff08802c8dcd9535b35"
            })
            Thread.sleep(10)
        }
    }
}