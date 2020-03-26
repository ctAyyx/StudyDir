package com.ct.aac.handler

import android.util.Log
import android.view.View
import com.ct.aac.vo.ObservableModel
import com.ct.aac.vo.User

class BindHandler {
    fun onClick(view: View) {}
    fun onClick(user: User) {}
    fun onClick(view: View, user: User) {}

    fun onLongClick(view: View): Boolean {
        return true
    }


    fun onObservableClick(model: ObservableModel) {
        model.firstName.set("尼古拉斯.凯奇")
        model.customObservableModel?.firstName = "改变了自定义的可观察字段"
    }

    fun changeLayout(view: View) {
        view.setPadding(20, 20, 20, 20)
    }

    fun onLayoutChange() {
        Log.e("TAG", "事件--onLayoutChange")
    }

    fun bothWayBind(model: ObservableModel) {

    }
}