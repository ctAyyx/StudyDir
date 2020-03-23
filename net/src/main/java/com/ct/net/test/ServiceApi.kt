package com.ct.net.test

import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * 这是一个对Retrofit使用的示例
 * */
interface ServiceApi {


    @FormUrlEncoded
    @POST("/api/v2/banner")
    fun getBanner(@Field("type") type: String): Observable<BaseResponse<List<BannerModel>>>


    /**
     * 针对Retrofit2文件上传
     *
     * 文件上传必须使用Multipart注解修饰
     *
     * 以下是Http发送的文件上传请求
     * POST https://gank.io/api/v2/banner HTTP/1.1
     *Content-Type: multipart/form-data;(标记类型-- multipart/form-data文件上传)  boundary=56c03dea-1c37-4af7-bc1b-e87ce5dfcfbb(重要 --分隔标识 用于分割提交的数据 )
     *Content-Length: 406
     * Host: gank.io
     * Connection: Keep-Alive
     *Accept-Encoding: gzip
     * User-Agent: okhttp/3.14.7

     * --56c03dea-1c37-4af7-bc1b-e87ce5dfcfbb//请求参数开始位置
     * Content-Disposition: form-data; name="aFile"; filename="a.txt"//name表示与后端约定的key  fileName是文件保存的名称
     *Content-Type: multipart/form-data
     *Content-Length: 19

     *dasdasdasdasdasdada
     * --56c03dea-1c37-4af7-bc1b-e87ce5dfcfbb//请求参数开始位置
     * Content-Disposition: form-data; name="bFile"; filename="b.txt"
     * Content-Type: multipart/form-data
     * Content-Length: 19

     *  dasdasdasdasdasdada
     * --56c03dea-1c37-4af7-bc1b-e87ce5dfcfbb--//结束位置

     *
     *  MultipartBody.Part 包装上传的文件
     *  RequestBody 也包装上传的文件 或 参数
     *
     *  */
    /**
     * 单文件 参数上传
     * */
    @Multipart
    @POST("/api/v2/banner")
    fun upFile(@Part file: MultipartBody.Part, @Part("description") description: RequestBody): Observable<BaseResponse<List<BannerModel>>>

    /**
     * 多文件上传
     * 使用List<MultipartBody.Part>上传多文件
     * */
    @Multipart
    @POST("/api/v2/banner")
    fun upFile2(@Part files: MutableList<MultipartBody.Part>): Observable<BaseResponse<List<BannerModel>>>

    /**
     * 多文件上传
     * 使用 Map<String, RequestBody>上传多文件
     * map中的Key 必须为 "name\";filename=\"" 因为Map的Key就是name=""中的内容
     * */
    @Multipart
    @POST("/api/v2/banner")
    fun upFile3(@PartMap map: MutableMap<String, RequestBody>): Observable<BaseResponse<List<BannerModel>>>

    /**
     *多个 文件参数同时传递
     *错误
     * */

    @Multipart
    @POST("/api/v2/banner")
    fun upFile4(@Part files: MutableList<MultipartBody.Part>, @FieldMap map: MutableMap<String, String>): Observable<BaseResponse<List<BannerModel>>>
}