package com.ct.aac.datasource

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

fun <Key, Value> DataSource.Factory<Key, Value>.toMyLiveData(
    pageSize: Int,
    initialLoadKey: Key? = null,
    boundaryCallback: PagedList.BoundaryCallback<Value>? = null

    ): LiveData<PagedList<Value>> {
    return LivePagedListBuilder(this, CustomPagedConfig(pageSize))
        .setInitialLoadKey(initialLoadKey)
        .setBoundaryCallback(boundaryCallback)

        .build()
}

@Suppress("FunctionName")
fun CustomPagedConfig(
    pageSize: Int,
    prefetchDistance: Int = pageSize,
    enablePlaceholders: Boolean = true,
    initialLoadSizeHint: Int =
        pageSize,
    maxSize: Int = PagedList.Config.MAX_SIZE_UNBOUNDED
): PagedList.Config {
    return PagedList.Config.Builder()
        .setPageSize(pageSize)
        .setPrefetchDistance(prefetchDistance)
        .setEnablePlaceholders(enablePlaceholders)
        .setInitialLoadSizeHint(initialLoadSizeHint)
        .setMaxSize(maxSize)
        .build()
}