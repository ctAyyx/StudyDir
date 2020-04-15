package com.ct.aac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import com.ct.aac.R
import com.ct.aac.databinding.ActivityBindBinding
import com.ct.aac.handler.BindHandler
import com.ct.aac.vo.CustomObservableModel
import com.ct.aac.vo.ObservableModel
import com.ct.aac.vo.User

/**
 * 数据绑定
 * 开启数据绑定
 * */
class BindActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBindBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_bind)
        binding = DataBindingUtil.setContentView<ActivityBindBinding>(this, R.layout.activity_bind)

        simpleBind()
        observableBind()
    }

    /**
     * 简单的数据绑定
     * */
    private fun simpleBind() {

        val user = User("尼古拉斯", "凯奇", 20)
        binding.user = user

        val map = mutableMapOf<String, String>(Pair("name", "绑定Map"))
        val list = mutableListOf<String>("绑定List")
        binding.map = map
        binding.list = list
        binding.handler = BindHandler()

    }

    /**
     * 可观察数据对象绑定
     * */
    private fun observableBind() {
        val model = ObservableModel()
        model.firstName.set("杰森斯坦森")
        model.weight.set(130)
        model.lastName = ObservableField<String>("赵四")
        model.customObservableModel = CustomObservableModel()
        model.customObservableModel?.firstName = "自定义的可观察字段"
        binding.model = model

    }

    /**
     * 生成绑定类
     *
     * 针对Activity,Fragment,RecyclerView
     * 不会用直接去看 https://developer.android.google.cn/topic/libraries/data-binding/generated-binding
     *
     * */


    /**
     * 绑定适配器
     * */

}
