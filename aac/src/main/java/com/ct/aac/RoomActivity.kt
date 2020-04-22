package com.ct.aac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.toLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ct.aac.datasource.CategoryRoomDataSourceFactory
import com.ct.aac.datasource.toMyLiveData
import com.ct.aac.synthesize.adapter.CategoryAdapter
import com.ct.aac.synthesize.db.GanKDb
import com.ct.aac.synthesize.db.GankDao
import com.ct.aac.synthesize.vo.Category
import kotlinx.android.synthetic.main.activity_room.*
import kotlinx.android.synthetic.main.activity_synthesize.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * 针对 Room 的使用
 * 实体类 Category
 *
 * */
class RoomActivity : AppCompatActivity() {

    private lateinit var db: GanKDb
    private lateinit var dao: GankDao
    private lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        db = Room.databaseBuilder(this, GanKDb::class.java, "gank.db").build()

        dao = db.gankDao()

        adapter = CategoryAdapter()
        recycler_room.adapter = adapter
        recycler_room.layoutManager = LinearLayoutManager(this)
    }

    fun onClick(view: View) {

        when (view.id) {
            R.id.btn_room_add -> {
                GlobalScope.launch {
                    db.runInTransaction {
                        dao.insertCategory(*buildCategory().toTypedArray())
                    }
                }
            }
            R.id.btn_room_clear -> {
                GlobalScope.launch {
                    dao.clearCategory()
                }
            }
            R.id.btn_room_get -> {

                val factory = CategoryRoomDataSourceFactory(dao)
                factory.toLiveData(pageSize = 5)
                    .observe(this, Observer {
                        Log.e("TAG", "接收到数据:${it}")
                        if (!it.isNullOrEmpty())
                            adapter.submitList(it)
                    })

//                dao.getCategory02()
//                    .toLiveData(pageSize = 5, boundaryCallback = createBoundCallback())
//                    .observe(this, Observer {
//                        Log.e("TAG", "接收到数据:${it}")
//                        if (!it.isNullOrEmpty())
//                            adapter.submitList(it)
//                    })
            }
        }
    }


    fun buildCategory() = mutableListOf<Category>().apply {

        (0..10).forEach {
            add(Category().apply {
                id = "${System.currentTimeMillis()}_$it"
                url = "http://gank.io/images/28fc02e86d584ff08802c8dcd9535b35"
            })
            Thread.sleep(10)
        }
    }


    fun createBoundCallback() = object : PagedList.BoundaryCallback<Category>() {

        override fun onItemAtEndLoaded(itemAtEnd: Category) {
            Log.e("TAG", "ROOM 边界回调onItemAtEndLoaded")
            GlobalScope.launch {
                db.runInTransaction {
                    dao.insertCategory(*buildCategory().toTypedArray())
                }
            }
        }

        override fun onZeroItemsLoaded() {
            super.onZeroItemsLoaded()
        }
    }


}
