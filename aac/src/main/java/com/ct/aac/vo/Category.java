package com.ct.aac.vo;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {


    /**
     * _id : 5e777471d4bf7c272db642d7
     * author : 鸢媛
     * category : Girl
     * content : 我见青山多妩媚，料青山，见我应如是。
     * createdAt : 2020-03-26 08:00:00
     * desc : 我见青山多妩媚，料青山，见我应如是。
     * images : ["http://gank.io/images/367c59cb861044e4af2835b2d46988d0"]
     * likeCounts : 0
     * publishedAt : 2020-03-26 08:00:00
     * stars : 1
     * title : 第36期
     * type : Girl
     * url : http://gank.io/images/367c59cb861044e4af2835b2d46988d0
     * views : 42
     */

    @SerializedName("_id")
    public String id;
    @SerializedName("author")
    public String author;
    @SerializedName("category")
    public String category;
    @SerializedName("content")
    public String content;
    @SerializedName("createdAt")
    public String createdAt;
    @SerializedName("desc")
    public String desc;
    @SerializedName("likeCounts")
    public int likeCounts;
    @SerializedName("publishedAt")
    public String publishedAt;
    @SerializedName("stars")
    public int stars;
    @SerializedName("title")
    public String title;
    @SerializedName("type")
    public String type;
    @SerializedName("url")
    public String url;
    @SerializedName("views")
    public int views;
    @SerializedName("images")
    public List<String> images;

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", content='" + content + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", desc='" + desc + '\'' +
                ", likeCounts=" + likeCounts +
                ", publishedAt='" + publishedAt + '\'' +
                ", stars=" + stars +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", views=" + views +
                ", images=" + images +
                '}';
    }
}
