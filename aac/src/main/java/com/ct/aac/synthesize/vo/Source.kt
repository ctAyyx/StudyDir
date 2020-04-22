package com.ct.aac.synthesize.vo

import com.ct.aac.paging.NetworkState

/**
 * @ClassName : Source
 * @CreateDate : 2020/4/20 15:45
 * @Author : CT
 * @Description :
 *
 */
data class Source<T>(
    //网络连接状态
    val networkState: NetworkState,
    //数据刷新状态
    val refreshState: NetworkState,
    //数据
    val data: T? = null,
    //刷新
    val refresh: (() -> Unit) = {},
    //重试
    val retry: (() -> Unit) = {}


) {
    companion object {
        fun <T> loading() = Source<T>(
            networkState = NetworkState.LOADING,
            refreshState = NetworkState.LOADING
        )

        fun <T> success(data: T?) = Source(
            networkState = NetworkState.LOADED,
            refreshState = NetworkState.LOADED,
            data = data
        )


    }
}