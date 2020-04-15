package com.ct.aac.synthesize.api

import com.ct.aac.synthesize.vo.BaseResponse
import com.ct.aac.synthesize.vo.Category
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @ClassName : GankServiceApi
 * @CreateDate : 2020/4/15 9:44
 * @Author : CT
 * @Description :
 *
 */
interface GankServiceApi {

    /**
     * 获取分类数据
     * */
    @GET("/api/v2/data/category/Girl/type/{category}/page/{page}/count/10")
    fun getCategory(@Path("category") category: String, @Path("page") page: Int): Call<BaseResponse<List<Category>>>
}