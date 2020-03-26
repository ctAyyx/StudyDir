package com.ct.aac.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ct.aac.paging.ServiceApi
import com.ct.aac.vo.Category


class CategoryDataSourceFactory(private val serviceApi: ServiceApi) :
    DataSource.Factory<Int, Category>() {

    val dataSource = MutableLiveData<CategoryDataSource>()

    override fun create(): DataSource<Int, Category> {

        return CategoryDataSource(serviceApi).also {
            dataSource.postValue(it)
        }
    }
}