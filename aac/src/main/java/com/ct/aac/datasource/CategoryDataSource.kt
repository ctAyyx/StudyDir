package com.ct.aac.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.ct.aac.paging.ServiceApi
import com.ct.aac.vo.Category2


/**
 * Gank.IO 分类数据 使用的page分页模式
 * */
class CategoryDataSource(private val serviceApi: ServiceApi) :
    PageKeyedDataSource<Int, Category2>() {


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Category2>
    ) {

        Log.e("TAG", "loadInitial...${params.requestedLoadSize}...${params}")
        val call = serviceApi.getCategoryList("Girl", "Girl", 1, params.requestedLoadSize)
        val response = call.execute()
        val data = response.body()?.data ?: emptyList()
        callback.onResult(data, 1, 2)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Category2>) {
        Log.e("TAG", "loadAfter...${params.requestedLoadSize} -- ${params.key}")
        val call = serviceApi.getCategoryList("Girl", "Girl", params.key, params.requestedLoadSize)
        val response = call.execute()
        val data = response.body()?.data ?: emptyList()
        callback.onResult(data, params.key + 1)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Category2>) {
    }
}