package com.ct.study

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.ct.study.notification.NotificationActivity
import com.ct.study.rv.RvActivity
import com.ct.study.test.GankDb
import com.ct.study.ui.DialogActivity
import com.ct.study.ui.MethodActivity
import com.ct.study.ui.ScrollerActivity
import kotlinx.android.synthetic.main.activity_home.*
import pub.devrel.easypermissions.EasyPermissions

import kotlin.reflect.KClass

class HomeActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


    }

    fun onClick(view: View) {

        when (view.id) {
            R.id.btn_home_net -> {//关于网络模块的使用
                val db = Room.databaseBuilder(this, GankDb::class.java, "test.db").build()
                Log.e("TAG", "$db")
                db.userDao()
            }
            R.id.btn_home_dialog -> {
                //关于Dialog模块的使用
                readyGo(DialogActivity::class)
            }
            R.id.btn_home_method -> readyGo(MethodActivity::class)
            R.id.btn_home_scroller->readyGo(ScrollerActivity::class)
            R.id.btn_home_file -> {
                //请求权限
                EasyPermissions.requestPermissions(
                    this,
                    "权限",
                    100,
                    *arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                )
            }

            R.id.btn_home_replace -> {
                formatName(btn_home_replace, "名字")

            }
            R.id.btn_home_recycler -> startActivity(Intent(this, RvActivity::class.java))
            R.id.btn_home_notification ->startActivity(Intent(this, NotificationActivity::class.java))

        }
    }

    fun formatName(view: TextView, name: String?) {
        if (name.isNullOrEmpty()) {
            view.text = ""
            return
        }

        if (name.length < 3)
            view.text = name
        else {
            var format = name.substring(0, 2)
            (0..name.length - 3).forEach {
                format += "*"
            }
            view.text = format
        }
    }

    private fun readyGo(cls: KClass<*>) {
        val intent = Intent(this, cls.java)
        startActivity(intent)
    }


    /**
     * 获取权限 创建文件
     * */

    private fun createFile() {
        val rootFile = Environment.getRootDirectory()

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        createFile()
    }



}
