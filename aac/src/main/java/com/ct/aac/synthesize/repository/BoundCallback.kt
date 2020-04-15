package com.ct.aac.synthesize.repository

import android.util.Log
import androidx.paging.PagedList
import com.ct.aac.synthesize.api.GankServiceApi
import com.ct.aac.synthesize.db.GankDao
import com.ct.aac.synthesize.vo.BaseResponse
import com.ct.aac.synthesize.vo.Category
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

/**
 * @ClassName : BoundCallback
 * @CreateDate : 2020/4/15 14:32
 * @Author : CT
 * @Description :
 *
 */
class BoundCallback<T>(val dao: GankDao? = null, val serviceApi: GankServiceApi? = null) :
    PagedList.BoundaryCallback<T>() {
    override fun onZeroItemsLoaded() {
        Log.e("TAG", "onZeroItemsLoaded")
    }

    /**
     *
     * */
    override fun onItemAtEndLoaded(itemAtEnd: T) {
        Log.e("TAG", "onItemAtEndLoaded$itemAtEnd")

    }

    override fun onItemAtFrontLoaded(itemAtFront: T) {
        Log.e("TAG", "onItemAtFrontLoaded$itemAtFront")
    }

}