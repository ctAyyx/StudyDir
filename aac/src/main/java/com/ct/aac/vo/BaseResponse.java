package com.ct.aac.vo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseResponse<T> {


    @SerializedName("page")
    public int page;
    @SerializedName("page_count")
    public int pageCount;
    @SerializedName("status")
    public int status;
    @SerializedName("total_counts")
    public int totalCounts;
    @SerializedName("data")
    public T data;
}
