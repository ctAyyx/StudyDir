package com.ct.aac.synthesize.repository

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.ct.aac.synthesize.vo.Source

/**
 * @ClassName : NetworkBoundResource
 * @CreateDate : 2020/4/15 10:53
 * @Author : CT
 * @Description : 数据边界调用类
 *  实现从数据库中获取数据,如果获取的数据不存在 则请求网络获取数据 并存入数据库
 */

abstract class NetworkBoundResource02<ResultType> {

    //定义一个观察多个数据源的LiveData
    private val result = MediatorLiveData<Source<ResultType>>()

    init {
        //表示当前状态为Loading
        result.value = Source.loading()
        //从数据库加载数据
        @Suppress("LeakingThis")
        val dbSource = loadFromBb()
        //将数据库源添加result中
        result.addSource(dbSource) { dbData ->
            //当数据库源数据改变时
            //1.移除数据库源，不在监听该数据源数据变化
            result.removeSource(dbSource)
            if (showFetch(dbData))
            //应该从网络加载数据
                fetchFromNetwork(dbSource)
            else
            //直接使用数据库的数据
            {
                result.addSource(dbSource) {
                    setValue(Source.success(it))
                }
            }
        }

    }

    fun asLiveData() = result as LiveData<ResultType>

    @MainThread
    private fun setValue(newValue: Source<ResultType>) {
        if (result.value != newValue)
            result.value = newValue
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        //获取网络数据源
        val apiResponse = createCall()
        //将网络源添加到result中
        result.addSource(apiResponse) { netData ->
            //当网络源数据更新时,同时取消对数据库源 和 网络源 的监听
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            result.addSource(loadFromBb()) {
                setValue(Source.success(it))
            }
            //这里向数据库存入数据
            saveFetchResult(netData)
        }

    }

    /**
     * 从数据库加载数据
     * */
    abstract fun loadFromBb(): LiveData<ResultType>

    /**
     * 是否应该从网络加载数据
     * @param dbData 数据库获取的数据 可能为null
     * 如果 这里是针对PagedList的使用 则返回false 不在使用这里的网络数据源
     * 应该使用BoundCallback来请求网络
     * */
    abstract fun showFetch(dbData: ResultType?): Boolean

    /**
     * 构建网络请求
     * */
    abstract fun createCall(): LiveData<ResultType>

    /**
     * 将网络数据保存到数据库
     * @param netData 网络数据
     * */
    abstract fun saveFetchResult(netData: ResultType)


}