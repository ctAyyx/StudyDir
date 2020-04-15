package com.ct.aac.synthesize.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.ct.aac.synthesize.repository.SynthesizeRepository
import com.ct.aac.synthesize.vo.Category
import com.ct.aac.vo.Listing

/**
 * @ClassName : SynthesizeViewModel
 * @CreateDate : 2020/4/15 9:59
 * @Author : CT
 * @Description :
 *
 */
class SynthesizeViewModel(val repository: SynthesizeRepository) : ViewModel() {

    //我们要传入的参数
    private val _category = MutableLiveData<String>()

    val result: LiveData<Listing<Category>> = _category.map {

        if (it.isBlank()) {
            Log.e("TAG", "查询类型是空")
            Listing<Category>(null, {})
        } else {
            Log.e("TAG", "查询类型：$it")
            repository.getCategory(it)
        }

    }

    fun setCategory(category: String) {
        if (category == _category.value)
            return
        _category.value = category
    }

}