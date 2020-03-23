package com.ct.net.test;

import com.google.gson.annotations.SerializedName;

public class BannerModel {


    /**
     * image : http://gank.io/images/3587b553fe10404abcce4dde1b28772c
     * title : Android Studio 3.6 发布啦，快来围观
     * url : https://gank.io/post/5e552d9befd6f28e2554f486
     */

    @SerializedName("image")
    public String image;
    @SerializedName("title")
    public String title;
    @SerializedName("url")
    public String url;
}
