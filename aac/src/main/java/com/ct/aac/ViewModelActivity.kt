package com.ct.aac

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.ct.aac.viewmodel.UserViewModel
import com.ct.aac.vo.User
import kotlinx.android.synthetic.main.activity_view_model.*

/**
 * 针对 ViewModel 和 LiveData的使用
 * */
class ViewModelActivity : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_model)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        viewModel.getUser().observe(this, Observer {
            tv_vm_01.text = "${it.firstName} -- ${it.lastName}"
        })
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_vm_01 -> {
                viewModel.upUser("更改名称")
                viewModel.upUser(User("新的对象", "测试", 2))
            }
            R.id.btn_vm_mediator -> {
                useMediatorLiveData()
            }
            R.id.btn_vm_mediator_01 -> {
                changeSource01()
            }
            R.id.btn_vm_mediator_02 -> {
                changeSource02()
            }
            R.id.btn_vm_mediator_03 -> {
                change()
            }
        }
    }


    /**
     * 针对 MediatorLiveData 的使用
     * 同时观察多个数据源
     * Transformations中的map switchMap 也是用的MediatorLiveData
     * */
    //创建一个 MediatorLiveData
    private val mediatorLiveData = MediatorLiveData<String>()
    //创建数据源
    private val source01 = MutableLiveData<String>()
    private val source02 = MutableLiveData<String>()
    private fun useMediatorLiveData() {


        //添加源到 MediatorLiveData 并观察其改变
        mediatorLiveData.addSource(source01) {
            Log.e("TAG", "当source01的数据发生改变")
            mediatorLiveData.value = it
        }
        mediatorLiveData.addSource(source02) {
            Log.e("TAG", "当source02的数据发生改变")
            mediatorLiveData.value = it
        }


        //对数据监听
        mediatorLiveData.observe(this, Observer {
            Log.e("TAG", "监听MediatorLiveData的数据.....$it")
        })
    }

    private fun changeSource01() {
        source01.value = "Source01改变数据"
    }

    private fun changeSource02() {
        source01.value = "Source02改变数据"
    }

    private fun change() {
        mediatorLiveData.value = "直接改变值"
    }
}
