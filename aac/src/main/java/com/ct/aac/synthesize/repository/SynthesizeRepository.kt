package com.ct.aac.synthesize.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.ct.aac.datasource.CategoryDataSourceFactory
import com.ct.aac.datasource.toMyLiveData
import com.ct.aac.synthesize.api.GankServiceApi
import com.ct.aac.synthesize.datasource.SynthesizeDataSourceFactory
import com.ct.aac.synthesize.db.GankDao
import com.ct.aac.synthesize.vo.BaseResponse
import com.ct.aac.synthesize.vo.Category
import com.ct.aac.vo.Listing
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @ClassName : CatoryRepository
 * @CreateDate : 2020/4/15 10:06
 * @Author : CT
 * @Description :
 *
 */
class SynthesizeRepository(val serviceApi: GankServiceApi) {

    fun getCategory(category: String): Listing<Category> {
        val factory = SynthesizeDataSourceFactory(category, serviceApi)
        val result = factory.toMyLiveData(pageSize = 10, boundaryCallback = BoundCallback())

        return Listing(
            pagedList = result,
            refresh = {}
        )
    }

    fun getCategoryRoom(dao: GankDao, serviceApi: GankServiceApi): LiveData<PagedList<Category>> {
        return dao.getCategory02()
            .toLiveData(pageSize = 10, boundaryCallback = BoundCallback(dao, serviceApi))

    }


    fun getCategory02(dao: GankDao, serviceApi: GankServiceApi): LiveData<List<Category>> {

        return object : NetworkBoundResource<List<Category>>() {
            override fun loadFromBb(): LiveData<List<Category>> {

                return dao.getCategory()
            }

            override fun showFetch(dbData: List<Category>?): Boolean {
                return true
            }

            override fun createCall(): LiveData<List<Category>> {
                val net = MutableLiveData<List<Category>>()
                serviceApi.getCategory("Girl", 5)
                    .enqueue(object : Callback<BaseResponse<List<Category>>> {
                        override fun onFailure(
                            call: Call<BaseResponse<List<Category>>>,
                            t: Throwable
                        ) {
                        }

                        override fun onResponse(
                            call: Call<BaseResponse<List<Category>>>,
                            response: Response<BaseResponse<List<Category>>>
                        ) {
                            net.value = response.body()?.data
                        }

                    })
                return net
            }

            override fun saveFetchResult(netData: List<Category>) {
                Log.e("'TAG", "保存到数据库")
                object : Thread() {
                    override fun run() {
                        dao.insertCategory(*netData.toTypedArray())
                    }
                }.start()
            }
        }.asLiveData()

    }

    fun getCategoryWithPaged(
        dao: GankDao,
        serviceApi: GankServiceApi
    ): LiveData<PagedList<Category>> {

        return object : NetworkBoundResource<PagedList<Category>>() {
            override fun loadFromBb(): LiveData<PagedList<Category>> {
                return dao.getCategory02()
                    .toLiveData(pageSize = 10, boundaryCallback = BoundCallback())
            }

            override fun showFetch(dbData: PagedList<Category>?): Boolean {
                return dbData == null || dbData.size == 0
            }

            override fun createCall(): LiveData<PagedList<Category>> {
                Log.e("TAG", "需要从网络获取数据")
                val factory = SynthesizeDataSourceFactory("Girl", serviceApi)

                return factory.toMyLiveData(pageSize = 10, boundaryCallback = BoundCallback())
            }

            override fun saveFetchResult(netData: PagedList<Category>) {
                Log.e("TAG", "保存到数据库.......")
                object : Thread() {
                    override fun run() {
                        dao.insertCategory(*netData.toTypedArray())
                    }
                }.start()
            }

        }.asLiveData()
    }
}