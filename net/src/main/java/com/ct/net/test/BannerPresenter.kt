package com.ct.net.test

import android.content.Context
import com.ct.net.presenter.BasePresenter

class BannerPresenter(context: Context) :
    BasePresenter<ServiceApi>(context) {
    override fun getBaseUrl(): String {
        return "https://gank.io"
    }

    override fun initServiceApi(): Class<ServiceApi> {
        return ServiceApi::class.java
    }


    fun getBanner(code: Int) {
        onFilter(serviceApi.getBanner("测试数据"), code)
    }

}