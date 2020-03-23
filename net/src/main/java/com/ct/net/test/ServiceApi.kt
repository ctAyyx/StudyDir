package com.ct.net.test

import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ServiceApi {

    @FormUrlEncoded
    @POST("/api/v2/banner")
    fun getBanner(@Field("type") type: String): Observable<BaseResponse<List<BannerModel>>>
}