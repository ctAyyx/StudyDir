package com.ct.aac.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ct.aac.paging.ServiceApi
import com.ct.aac.synthesize.db.GankDao
import com.ct.aac.synthesize.vo.Category
import com.ct.aac.vo.Category2


class CategoryRoomDataSourceFactory(val dao: GankDao) :
    DataSource.Factory<Int, Category>() {

    val dataSource = MutableLiveData<CategoryRoomDataSource>()

    override fun create(): DataSource<Int, Category> {

        return CategoryRoomDataSource(dao).also {
            dataSource.postValue(it)
        }
    }
}