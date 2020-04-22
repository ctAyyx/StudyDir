package com.ct.study.rv

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.ct.study.R
import com.ct.study.rv.adapter.RvAdapter01
import com.ct.study.rv.decoration.CustomItemTouchHelperCallback
import com.ct.study.rv.decoration.LinearItemDecoration
import com.ct.study.rv.decoration.StickHeaderItemDecoration
import com.ct.study.rv.vo.Category
import kotlinx.android.synthetic.main.activity_rv.*

/**
 * 针对 RecyclerView的使用
 * 1.ItemDecoration
 * 2.ItemTouchHelper
 * 3.LayoutManager
 * */
class RvActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv)


    }

    fun onClick(view: View) {
        when (view.id) {
            //针对 列表
            R.id.btn_rv_list -> listRv()
            R.id.btn_rv_stick -> listStickRv()

        }
    }

    /**
     * 针对 列表的
     * */
    private fun listRv() {
        val adapter = RvAdapter01()
        recycler_rv.adapter = adapter
        recycler_rv.layoutManager = LinearLayoutManager(this)
        //加入间隔
        recycler_rv.addItemDecoration(LinearItemDecoration())
        //加入 ItemTouchHelper
        val helper = ItemTouchHelper(CustomItemTouchHelperCallback(adapter))
        helper.attachToRecyclerView(recycler_rv)

        adapter.submitList(buildList())
    }

    /**
     * 针对 列表的粘性头部
     * */
    private fun listStickRv() {
        val adapter = RvAdapter01()
        recycler_rv.adapter = adapter
        recycler_rv.layoutManager = LinearLayoutManager(this)
        //加入间隔
        recycler_rv.addItemDecoration(StickHeaderItemDecoration(adapter))
        adapter.submitList(buildStickList())
    }

    /**
     * 针对 ItemTouchHelper的使用
     * */


    private fun buildList() = mutableListOf<Category>().apply {
        (1..10).forEach {
            add(Category().apply {
                id = "${System.currentTimeMillis()}_$it"
                url = "http://gank.io/images/28fc02e86d584ff08802c8dcd9535b35"
            })
        }
    }

    private fun buildStickList() = mutableListOf<Category>().apply {
        (1..30).forEach {
            add(Category().apply {
                id = "${System.currentTimeMillis()}_$it"
                url = "http://gank.io/images/28fc02e86d584ff08802c8dcd9535b35"
                type =
                    if (it < 6) "A" else if (it < 11) "B" else if (it < 16) "C" else if (it < 21) "D" else "E"
            })
        }
    }


}
