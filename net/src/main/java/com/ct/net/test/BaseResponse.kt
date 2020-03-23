package com.ct.net.test

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(@SerializedName("status") val status: Int, @SerializedName("data") val data: T)