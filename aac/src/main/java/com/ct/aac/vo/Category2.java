package com.ct.aac.vo;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 创建一个Room的表
 *
 * 使用 @Entity 标记该类为实体类
 *  可以使用tabName 修改表名称 默认和实体类一致
 *
 * 使用@PrimaryKey 标记主键
 *    如果想让 Room 为实体分配自动 ID ，则可以设置 @PrimaryKey 的 autoGenerate 属性
 *    如果实体具有复合主键，您可以使用 @Entity 注释的 primaryKeys 属性
 * Room 将字段名称用作数据库中的列名称
 *    如果希望列具有不同的名称，将 @ColumnInfo 注释添加到字段
 * 默认情况下，Room 会为在实体中定义的每个字段创建一个列。
 *    如果某个实体中有您不想保留的字段，则可以使用 @Ignore 为这些字段注释
 * 如果您的应用需要通过全文搜索 (FTS) 快速访问数据库信息，请使用虚拟表（使用 FTS3 或 FTS4 SQLite 扩展模块）为您的实体提供支持
 * 启用 FTS 的表始终使用 INTEGER 类型的主键且列名称为“rowid”。如果是由 FTS 表支持的实体定义主键，则必须使用相应的类型和列名称
 * */

//@Fts4
//@Entity(tableName = "categories")
public class Category2 {


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

    @PrimaryKey
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
    @ColumnInfo(name = "like_counts")
    @SerializedName("likeCounts")
    public int likeCounts;
    @ColumnInfo(name = "published_at")
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
    @Ignore
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
