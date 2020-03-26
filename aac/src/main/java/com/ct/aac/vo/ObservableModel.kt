package com.ct.aac.vo

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt

/**
 * 这是一个在数据绑定中可观察的实体类
 *
 * ObservableBoolean
 *ObservableByte
 *ObservableChar
 *ObservableShort
 *ObservableInt
 *ObservableLong
 *ObservableFloat
 *ObservableDouble
 *ObservableParcelable
 * ObservableField
 * */
class ObservableModel {
    val firstName = ObservableField<String>()
    val weight = ObservableInt()
    var lastName: ObservableField<String>? = null
    val isChecked = ObservableBoolean(false)

    var customObservableModel: CustomObservableModel? = null


}