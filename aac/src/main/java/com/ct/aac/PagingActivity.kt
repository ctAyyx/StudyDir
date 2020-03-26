package com.ct.aac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ct.aac.adapter.CategoryAdapter
import com.ct.aac.datasource.CategoryRepository
import com.ct.aac.paging.GankPresenter
import com.ct.aac.vo.Category
import com.ct.aac.vo.Listing
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_paging.*

/**
 * 针对 分页库 的使用
 *
 * 1.定义数据源
 * */
class PagingActivity : AppCompatActivity() {

    private lateinit var adapter: CategoryAdapter
    private lateinit var listing: Listing<Category>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging)
        adapter = CategoryAdapter()
        recycler_paging.layoutManager = LinearLayoutManager(this)
        recycler_paging.adapter = adapter

        refresh_paging.setOnRefreshListener {
            listing.refresh.invoke()
        }
        initData()

    }

    private fun initData() {
        listing = CategoryRepository(GankPresenter(this).serviceApi)
            .getCategory()

        listing.pagedList.observe(this, Observer {
            adapter.submitList(it)
        })
    }
}
