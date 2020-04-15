package com.ct.study

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.room.Room
import com.ct.study.test.GankDb
import com.ct.study.ui.DialogActivity
import com.ct.study.ui.MethodActivity
import kotlin.reflect.KClass

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    fun onClick(view: View) {

        when (view.id) {
            R.id.btn_home_net -> {//关于网络模块的使用
                val db=Room.databaseBuilder(this, GankDb::class.java, "test.db").build()
                Log.e("TAG","$db")
                db.userDao()
            }
            R.id.btn_home_dialog -> {
                //关于Dialog模块的使用
                readyGo(DialogActivity::class)
            }
            R.id.btn_home_method -> readyGo(MethodActivity::class)
        }
    }

    private fun readyGo(cls: KClass<*>) {
        val intent = Intent(this, cls.java)
        startActivity(intent)
    }
}
