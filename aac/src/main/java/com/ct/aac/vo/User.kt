package com.ct.aac.vo

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt

data class User(var firstName: String, var lastName: String, val age: Int) {

    val job: String? = null

    val icon =
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1585127864615&di=fc87de54e6b44fd476da6bec524c282f&imgtype=0&src=http%3A%2F%2Fi2.hdslb.com%2Fbfs%2Farchive%2F1e600d4387aa055df59cfb4fe119fa60eb8917b4.jpg"

}