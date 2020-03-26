package com.ct.aac.vo

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.ct.aac.BR

/**
 * 自定义可观察对象
 *
 * 实现Observable接口 ,这里我们继承BaseObservable
 *
 * 对要观察的字段 get方法添加@Bindable注解 set方法添加 notifyPropertyChanged(BR.firstName)方法
 *
 * */
class CustomObservableModel : BaseObservable() {


    var firstName: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
        }

}