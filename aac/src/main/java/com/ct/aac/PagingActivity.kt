package com.ct.aac

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ct.aac.adapter.CategoryAdapter
import com.ct.aac.vo.Category2
import com.ct.aac.vo.Listing
import kotlinx.android.synthetic.main.activity_paging.*

/**
 * 针对 分页库 的使用
 *
 * 1.定义数据源
 *
 * 在使用Paging的时候 如果使用数据库+网络的模式
 * 使用 PagedList.BoundCallback 来监听到数据的数据没有的时候，就开始启用网络请求，在请求到数据后 将数据保存到数据库 *
 * */
class PagingActivity : AppCompatActivity() {

    private lateinit var adapter: CategoryAdapter
    private lateinit var listing: Listing<Category2>
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
//        listing = CategoryRepository(GankPresenter(this).serviceApi)
//            .getCategory()
//
//        listing.pagedList.observe(this, Observer {
//            adapter.submitList(it)
//        })
    }
}
