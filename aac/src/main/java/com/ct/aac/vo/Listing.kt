package com.ct.aac.vo

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList

data class Listing<T>(
    val pagedList: LiveData<PagedList<T>>,
    val refresh: ( ) -> Unit
)