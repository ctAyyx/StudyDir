package com.ct.aac.synthesize.repository

import android.util.Log
import androidx.paging.PagedList
import com.ct.aac.paging.PagingRequestHelper
import com.ct.aac.paging.createStatusLiveData
import com.ct.aac.synthesize.api.GankServiceApi
import com.ct.aac.synthesize.db.GankDao
import com.ct.aac.synthesize.vo.BaseResponse
import com.ct.aac.synthesize.vo.Category
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

/**
 * @ClassName : PagingBoundCallback
 * @CreateDate : 2020/4/16 13:58
 * @Author : CT
 * @Description : Paging数据边界回调
 *  可以在这里进行网络数据的获取 并存入数据
 *
 * 参照Google 加入PagingRequestHelper
 *
 */
class PagingBoundCallback(val dao: GankDao, val serviceApi: GankServiceApi) :
    PagedList.BoundaryCallback<Category>() {

    internal var page = 1
    val helper = PagingRequestHelper(Executors.newSingleThreadExecutor())
    val networkState = helper.createStatusLiveData()

    /**
     * 重要方法
     * 当列表里面的数据为NULL或者长度为0时
     *
     * 比如:如果用户刷新数据,我们可以清空数据库表,然后再这里请求网络数据
     * */
    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        page = 1
        Log.e("Paging", "onZeroItemsLoaded--${Thread.currentThread().name}")
//        object : Thread() {
//            override fun run() {
//                //请求下一页的数据
//                val result =
//                    serviceApi.getCategory("Girl", page).execute()
//                val list = result.body()?.data ?: emptyList()
//                //如果数据不为空 则存入数据库
//                if (list.isNotEmpty())
//                    dao.insertCategory(*list.toTypedArray())
//            }
//        }.start()

        //使用Helper
        //这里模拟一个耗时
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            serviceApi.getCategory("Girl", page).enqueue(createWebServiceCallback(it))
        }

    }

    /**
     * 重要方法
     * 当数据库没有数据需要从网络获取数据时,就是在这里调用获取网络数据的
     *
     * 到数据已经到达底部 没有更多数据的时候
     * @param itemAtEnd 最后一个Item
     * */
    override fun onItemAtEndLoaded(itemAtEnd: Category) {
        super.onItemAtEndLoaded(itemAtEnd)
        //数据库数据已经没有了
        //从网络获取更多数据
        //建议 如果是 使用Page分页的,应该在实体类中记录page 在这里我们就可以直接获取了
        Log.e("Paging", "onItemAtEndLoaded--${Thread.currentThread().name}--${itemAtEnd.page}")
        page = itemAtEnd.page + 1
//        object : Thread() {
//            override fun run() {
//                //请求下一页的数据
//                val result =
//                    serviceApi.getCategory(itemAtEnd.category, page).execute()
//                val list = result.body()?.data ?: emptyList()
//
//                //如果数据不为空 则存入数据库
//                if (list.isNotEmpty()) {
//                    list.forEach { it.page = page }
//                    dao.insertCategory(*list.toTypedArray())
//                }
//            }
//        }.start()

        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            serviceApi.getCategory("Girl", page).enqueue(createWebServiceCallback(it))
        }


    }


    /**
     *
     * */
    override fun onItemAtFrontLoaded(itemAtFront: Category) {
        super.onItemAtFrontLoaded(itemAtFront)
    }


    private fun createWebServiceCallback(pagingCallback: PagingRequestHelper.Request.Callback) =
        object : Callback<BaseResponse<List<Category>>> {
            override fun onFailure(call: Call<BaseResponse<List<Category>>>, t: Throwable) {
                pagingCallback.recordFailure(t)
            }

            override fun onResponse(
                call: Call<BaseResponse<List<Category>>>,
                response: Response<BaseResponse<List<Category>>>
            ) {
                insertDb(response.body()?.data ?: emptyList(), pagingCallback)
            }

        }

    internal fun insertDb(
        list: List<Category>,
        pagingCallback: PagingRequestHelper.Request.Callback
    ) {
        object : Thread() {
            override fun run() {
                //模拟耗时
                sleep(3000)
                //如果数据不为空 则存入数据库
                if (list.isNotEmpty()) {
                    list.forEach { it.page = page }
                    dao.insertCategory(*list.toTypedArray())
                }
                //在数据库插入成功后 执行成功
                pagingCallback.recordSuccess()
            }
        }.start()
    }
}