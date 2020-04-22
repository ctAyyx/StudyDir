package com.ct.aac.synthesize.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.toLiveData
import com.ct.aac.datasource.toMyLiveData
import com.ct.aac.paging.NetworkState
import com.ct.aac.synthesize.api.GankServiceApi
import com.ct.aac.synthesize.datasource.SynthesizeDataSourceFactory
import com.ct.aac.synthesize.db.GankDao
import com.ct.aac.synthesize.repository.NetworkBoundResource
import com.ct.aac.synthesize.repository.PagingBoundCallback
import com.ct.aac.synthesize.repository.SynthesizeRepository
import com.ct.aac.synthesize.vo.Category
import com.ct.aac.vo.Listing

/**
 * @ClassName : SynthesizeViewModel
 * @CreateDate : 2020/4/15 9:59
 * @Author : CT
 * @Description :
 *
 */
class SynthesizeViewModel(val repository: SynthesizeRepository) : ViewModel() {

    /**
     * 使用Paging分页库,获取数据
     *
     * */
    fun getCategory(category: String, serviceApi: GankServiceApi, dao: GankDao): Listing<Category> {
        //这是直接使用网络获取数据
//        val factory = SynthesizeDataSourceFactory(category, serviceApi)
//        val result = factory.toMyLiveData(pageSize = 10)
//        return Listing(
//            pagedList = result,
//            refresh = {}
//        )

        //使用数据库+网络获取数据
        //这里需要使用PagedList.BoundCallback
        val boundCallback = PagingBoundCallback(dao, serviceApi)
        val dbResult = dao.getCategory02()
            .toMyLiveData(pageSize = 10, boundaryCallback = boundCallback)
        return Listing(
            pagedList = dbResult,
            refresh = {
                //这里如果刷新数据
                //应该是清空数据库
                //重新从网络获取数据

            },
            networkState = boundCallback.networkState,
            retry = { boundCallback.helper.retryAllFailed() }
        )
    }


    /**
     * 获取可观察的List数据
     * */


    //我们要传入的参数
    private val _category = MutableLiveData<String>()

    val result: LiveData<Listing<Category>> = _category.map {

        if (it.isBlank()) {
            Log.e("TAG", "查询类型是空")
            Listing<Category>(null,
                MutableLiveData(NetworkState.LOADED), {}, retry = {})
        } else {
            Log.e("TAG", "查询类型：$it")
            repository.getCategory(it)
        }

    }

    fun setCategory(category: String) {
        if (category == _category.value)
            return
        _category.value = category
    }

}