package com.ct.aac.paging

import com.ct.aac.vo.BaseResponse
import com.ct.aac.vo.Category2
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ServiceApi {

    //https://gank.io/api/v2/data/category/<category>/type/<type>/page/<page>/count/<count>
    /**
     * category 可接受参数 All(所有分类) | Article | GanHuo | Girl
     *type 可接受参数 All(全部类型) | Android | iOS | Flutter | Girl ...，即分类API返回的类型数据
     *count： [10, 50]
     *page: >=1
     * */
    @GET("/api/v2/data/category/{category}/type/{type}/page/{page}/count/{count}")
    fun getCategoryList(
        @Path("category") category: String,
        @Path("type") type: String,
        @Path("page") page: Int,
        @Path("count") count:Int

    ): Call<BaseResponse<List<Category2>>>
}