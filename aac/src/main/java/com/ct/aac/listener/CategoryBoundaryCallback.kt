package com.ct.aac.listener

import android.util.Log
import androidx.paging.PagedList
import com.ct.aac.vo.Category2

class CategoryBoundaryCallback :PagedList.BoundaryCallback<Category2>() {
    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        Log.e("TAG","CategoryBoundaryCallback - onZeroItemsLoaded")
    }

    override fun onItemAtEndLoaded(itemAtEnd: Category2) {
        super.onItemAtEndLoaded(itemAtEnd)
        Log.e("TAG","CategoryBoundaryCallback - onItemAtEndLoaded - $itemAtEnd")
    }

    override fun onItemAtFrontLoaded(itemAtFront: Category2) {
        super.onItemAtFrontLoaded(itemAtFront)
        Log.e("TAG","CategoryBoundaryCallback - onItemAtFrontLoaded -- $itemAtFront")
    }
}