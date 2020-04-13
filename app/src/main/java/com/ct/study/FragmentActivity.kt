package com.ct.study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import com.ct.study.fm.NewFragment
import com.ct.study.fm.ReplaceFragment

/**
 * 针对 Fragment的使用
 * */
class FragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_fragment_finish -> onBackPressed()
            R.id.btn_fragment_add -> addFragment()
            R.id.btn_fragment_replace ->replaceFragment()
        }

    }

    /**
     * 向 FrameLayout中添加一个Fragment
     * */
    private fun addFragment() {
        //初始化要添加的Fragment
        val newFragment = NewFragment()
        //开启一个事务
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, newFragment)
        //这里可以使用addToBackStack将Fragment加入到Activity的回退栈中
        //直接使用finish()方法还是会直接销毁Activity
        transaction.addToBackStack("newFragment")
        //提交事务
        //commit() 不会立即执行 而是在 Activity 的界面线程（“主”线程）可执行该操作时，再安排该事务在线程上运行
        //executePendingTransactions() 以立即执行 commit() 提交的事务
        // 只能在 Activity 保存其状态（当用户离开 Activity）之前使用 commit() 提交事务。
        // 如果您试图在该时间点后提交，则会引发异常。
        // 这是因为如需恢复 Activity，则提交后的状态可能会丢失。对于丢失提交无关紧要的情况，
        // 请使用 commitAllowingStateLoss()。
        transaction.commit()

    }


    private fun replaceFragment() {
        //初始化要显示的Fragment
        val replaceFragment = ReplaceFragment()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, replaceFragment)
        transaction.addToBackStack("replaceFragment")

        transaction.commit()


    }


}
