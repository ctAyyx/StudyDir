package com.ct.aac

import android.database.DatabaseUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.ct.aac.databinding.ActivitySynthesizeBinding
import com.ct.aac.synthesize.adapter.CategoryAdapter
import com.ct.aac.synthesize.api.GankServiceApi
import com.ct.aac.synthesize.db.GanKDb
import com.ct.aac.synthesize.db.GankDao
import com.ct.aac.synthesize.repository.SynthesizeRepository
import com.ct.aac.synthesize.viewmodel.SynthesizeViewModel
import com.ct.aac.synthesize.viewmodel.SynthesizeViewModelFactory
import com.ct.net.service.ServiceNet
import kotlinx.android.synthetic.main.activity_synthesize.*

/**
 * 针对 ROOM Paging DataBinding ViewModel LiveData的综合使用
 *  所有实现都在synthesize下
 *  在使用Room数据库的时候 从Model引入包 会导致Database构建失败
 *  1.实现数据的缓存，列表直接获取数据库的数据 数据库更多数据后 请求网络数据 存入数据库
 *
 * */
class SynthesizeActivity : AppCompatActivity() {


    private lateinit var adapter: CategoryAdapter

    private lateinit var viewmodel: SynthesizeViewModel

    private lateinit var serviceApi: GankServiceApi
    private lateinit var repository: SynthesizeRepository
    private lateinit var dao: GankDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySynthesizeBinding>(
            this,
            R.layout.activity_synthesize
        )


        adapter = CategoryAdapter()
        recycler_synthesize.adapter = adapter
        recycler_synthesize.layoutManager = LinearLayoutManager(this)
        recycler_synthesize.itemAnimator = DefaultItemAnimator()
        // binding.lifecycleOwner = this
        serviceApi =
            ServiceNet.init().initService("https://gank.io").create(GankServiceApi::class.java)
        repository = SynthesizeRepository(serviceApi)
        val db = Room.databaseBuilder(this, GanKDb::class.java, "gank.bd").build()
        dao = db.gankDao()

        //subscribeUi()
        testBoundData()
        getRoom()
        testBoundDataPaged()
    }

    private fun subscribeUi() {


        val factory = SynthesizeViewModelFactory(repository)

        viewmodel = ViewModelProvider(this, factory).get(SynthesizeViewModel::class.java)

        viewmodel.result
            .observe(this, Observer {
                //获取的数据
                Log.e("TAG", "---->${it.pagedList?.value?.size}")

                it.pagedList?.observe(this, Observer { list ->
                    adapter.submitList(list)
                    //将数据存入数据库
                    object : Thread() {
                        override fun run() {
                            Log.e("TAG", "向数据库存入数据------>")
                            dao.insertCategory(*list.toTypedArray())
                        }
                    }.start()
                })
            })

        swipe_synthesize.setOnRefreshListener { }


        viewmodel.setCategory("Girl")


    }

    private fun getRoom() {
        btn_syn_02.setOnClickListener {
            repository.getCategoryRoom(dao, serviceApi)
                .observe(this, Observer {
                    Log.e("TAG", "最终获取到的数据:${it.size} -- $it")
                    adapter.submitList(it)
                })
        }
    }

    private fun testBoundData() {
        btn_syn_01.setOnClickListener {
            repository.getCategory02(dao, serviceApi).observe(this, Observer {
                Log.e("TAG", "最终获取到的数据:${it.size} -- $it")
            })
        }
    }

    private fun testBoundDataPaged() {
        btn_syn_03.setOnClickListener {
            repository.getCategoryWithPaged(dao, serviceApi).observe(this, Observer {
                Log.e("TAG","testBoundDataPaged$it")
                adapter.submitList(it)

                it.addWeakCallback(it,object : PagedList.Callback(){
                    override fun onChanged(position: Int, count: Int) {
                        Log.e("TAG","onChanged:$position -- $count")
                    }

                    override fun onInserted(position: Int, count: Int) {
                        Log.e("TAG","onInserted:$position -- $count")
                    }

                    override fun onRemoved(position: Int, count: Int) {
                        Log.e("TAG","onRemoved:$position -- $count")
                    }

                })
            })
        }
    }
}
