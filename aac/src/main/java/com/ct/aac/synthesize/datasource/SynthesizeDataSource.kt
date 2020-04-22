package com.ct.aac.synthesize.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import androidx.room.paging.LimitOffsetDataSource
import com.ct.aac.synthesize.api.GankServiceApi
import com.ct.aac.synthesize.vo.Category

/**
 * @ClassName : SynthesizeDataSource
 * @CreateDate : 2020/4/15 9:42
 * @Author : CT
 * @Description :
 *
 */
class SynthesizeDataSource(val category: String, val serviceApi: GankServiceApi, val page: Int) :
    PageKeyedDataSource<Int, Category>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Category>
    ) {

        Log.e("TAG", "SynthesizeDataSource---从网络初始化数据")
        val call = serviceApi.getCategory(category, page)
        val result = call.execute().body()?.data ?: emptyList()
        callback.onResult(result, page, page + 1)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Category>) {
        Log.e("TAG", "SynthesizeDataSource---从网络加载更多数据")
        val call = serviceApi.getCategory(category, params.key)
        val result = call.execute().body()?.data ?: emptyList()
        if (params.key >= 6)
            callback.onResult(emptyList(), params.key + 1)
        else
            callback.onResult(result, params.key + 1)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Category>) {
    }
}