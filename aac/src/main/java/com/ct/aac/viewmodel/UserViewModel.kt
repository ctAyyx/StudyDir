package com.ct.aac.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import com.ct.aac.vo.User

/**
 * 对 ViewModel的使用
 * 结合LiveData的使用
 * */
class UserViewModel : ViewModel() {

    private val user: MutableLiveData<User> by lazy {
        Log.e("TAG", "-----Lazy")
        MutableLiveData<User>().also {
            it.value = User("LiveData", "ViewModel", 1)
        }
    }

    fun getUser(): LiveData<User> {
        return user
    }

    fun upUser(user: User) {
        this.user.value = user
    }

    fun upUser(firstName: String) {
        this.user.value?.firstName = firstName
        Log.e("TAG", "${user.value}")

    }

}