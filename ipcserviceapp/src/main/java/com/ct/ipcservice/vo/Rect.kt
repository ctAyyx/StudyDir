package com.ct.ipcservice.vo

import android.os.Parcel
import android.os.Parcelable

/**
 * 使用该对象进行跨进程通讯
 *
 * 实现Parcelable接口
 *添加 CREATOR 静态字段(有程序自动生成)
 * */
class Rect() : Parcelable {
    var left = 0
    var top = 0
    var right = 0
    var bottom = 0

    constructor(parcel: Parcel) : this() {
        readFromParcel(parcel)
    }

    fun readFromParcel(parcel: Parcel) {
        left = parcel.readInt()
        top = parcel.readInt()
        right = parcel.readInt()
        bottom = parcel.readInt()
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(left)
        dest?.writeInt(top)
        dest?.writeInt(right)
        dest?.writeInt(bottom)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Rect(left=$left, top=$top, right=$right, bottom=$bottom) -- ${hashCode()}"
    }

    companion object CREATOR : Parcelable.Creator<Rect> {
        override fun createFromParcel(parcel: Parcel): Rect {
            return Rect(parcel)
        }

        override fun newArray(size: Int): Array<Rect?> {
            return arrayOfNulls(size)
        }
    }


}