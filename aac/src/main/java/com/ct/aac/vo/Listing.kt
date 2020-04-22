package com.ct.aac.vo

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.ct.aac.paging.NetworkState

data class Listing<T>(
    val pagedList: LiveData<PagedList<T>>?,

    //显示用户的网络请求状态
    val networkState: LiveData<NetworkState>,

    val refresh: () -> Unit,
    val retry: () -> Unit = {}

)