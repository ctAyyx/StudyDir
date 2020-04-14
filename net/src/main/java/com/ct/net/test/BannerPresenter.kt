package com.ct.net.test

import android.content.Context
import android.os.Environment
import com.ct.net.presenter.BasePresenter
import com.ct.tool.rx.RxFileTool
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class BannerPresenter(context: Context, private val serviceApi: ServiceApi) :
    BasePresenter(context) {


    fun upFile(code: Int) {
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),
            "a.txt"
        )
        if (file.exists())
            file.createNewFile()
        val description = RequestBody.create(MediaType.parse("multipart/form-data"), "这是文件描述")
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val filePart = MultipartBody.Part.createFormData("testFile", "a.txt", requestFile)

        onFilter(serviceApi.upFile(filePart, description), code)
    }

    fun upFile2(code: Int) {
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),
            "a.txt"
        )
        val file2 = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),
            "b.txt"
        )

        val description = RequestBody.create(MediaType.parse("multipart/form-data"), "这是文件描述")
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val filePart = MultipartBody.Part.createFormData("aFile", "a.txt", requestFile)

        val requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), file2)
        val filePart2 = MultipartBody.Part.createFormData("bFile", "b.txt", requestFile2)

        onFilter(serviceApi.upFile2(mutableListOf(filePart, filePart2)), code)
    }


    fun upFile3(code: Int) {
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),
            "a.txt"
        )
        val file2 = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),
            "b.txt"
        )

        val description = RequestBody.create(MediaType.parse("multipart/form-data"), "这是文件描述")
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val filePart = MultipartBody.Part.createFormData("aFile", "a.txt", requestFile)

        val requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), file2)
        val filePart2 = MultipartBody.Part.createFormData("bFile", "b.txt", requestFile2)

        val map = mutableMapOf<String, RequestBody>()
        map["map_aFile\"; filename=\"a.txt"] = requestFile
        map["map_bFile\"; filename=\"b.txt"] = requestFile

        onFilter(serviceApi.upFile3(map), code)
    }

    fun upFile4(code: Int) {
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),
            "a.txt"
        )
        val file2 = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),
            "b.txt"
        )

        val description = RequestBody.create(MediaType.parse("multipart/form-data"), "这是文件描述")
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val filePart = MultipartBody.Part.createFormData("aFile", "a.txt", requestFile)

        val requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), file2)
        val filePart2 = MultipartBody.Part.createFormData("bFile", "b.txt", requestFile2)

        val params = mutableMapOf(Pair("name", ""), Pair("password", ""))
        onFilter(serviceApi.upFile4(mutableListOf(filePart, filePart2), params), code)
    }


    /**
     * 文件下载 http://3g.163.com/links/4636
     * */
    fun downApk(code: Int) {

        serviceApi.downApk("http://3g.163.com/links/4636")

    }


}