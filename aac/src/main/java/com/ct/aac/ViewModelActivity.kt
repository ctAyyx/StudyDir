package com.ct.aac

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ct.aac.viewmodel.UserViewModel
import com.ct.aac.vo.User
import kotlinx.android.synthetic.main.activity_view_model.*

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
        }
    }
}
