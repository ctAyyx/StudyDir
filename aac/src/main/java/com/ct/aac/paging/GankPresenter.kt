package com.ct.aac.paging

import android.content.Context
import com.ct.net.presenter.BasePresenter

class GankPresenter(context: Context) : BasePresenter<ServiceApi>(context) {
    override fun getBaseUrl(): String {
        return "https://gank.io"
    }

    override fun initServiceApi(): Class<ServiceApi> {
        return ServiceApi::class.java
    }



}