package com.ct.aac

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.ct.aac.databinding.ActivitySynthesizeBinding
import com.ct.aac.synthesize.adapter.CategoryAdapter
import com.ct.aac.synthesize.adapter.CategoryAdapter02
import com.ct.aac.synthesize.adapter.CategoryAdapter03
import com.ct.aac.synthesize.api.GankServiceApi
import com.ct.aac.synthesize.db.GanKDb
import com.ct.aac.synthesize.db.GankDao
import com.ct.aac.synthesize.repository.SynthesizeRepository
import com.ct.aac.synthesize.viewmodel.SynthesizeViewModel
import com.ct.aac.synthesize.viewmodel.SynthesizeViewModelFactory
import com.ct.aac.synthesize.vo.Category
import com.ct.net.service.ServiceNet
import kotlinx.android.synthetic.main.activity_synthesize.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * 针对 ROOM Paging DataBinding ViewModel LiveData的综合使用
 *  所有实现都在synthesize下
 *  在使用Room数据库的时候 从Model引入包 会导致Database构建失败
 *  1.实现数据的缓存，列表直接获取数据库的数据 数据库更多数据后 请求网络数据 存入数据库
 *  如果使用PagedList的话
 *  加入Room缓存数据，建议使用PagedList.BoundCallback,在里面的回调方法中请求网络 获取数据
 *  但是会出现 在从网络加载数据到数据库后,PagedList是重新获取的,会出现列表界面闪烁的问题
 *  我的解决办法是:自定义DataSource实现数据库的分页查询,可以见RoomActivity的使用
 *  TMD 我发现自己就是个SB 造成闪屏问题的原因就是因为我偷懒直接在DIFF中对比对象了
 *  如果普通请求
 *  加入Room缓存数据,可以参照NetworkBoundResource这个类的模式，使用MediatorLiveData来监听数据库源和网络源数据,在网络获取数据后将数据存入数据库
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

        //初始化
        initUi()

        //清除数据库
        clearDb()

        //针对Paging中 Room+Network
        pagingRoomAndNet()


        //针对数据边界回调 自定义的写法
        boundCallback()


        //
        withRequestState()
    }

    /**
     * 初始化需要的数据
     * */
    private fun initUi() {
        //初始化RecyclerView
        adapter = CategoryAdapter()
        recycler_synthesize.adapter = adapter
        recycler_synthesize.layoutManager = LinearLayoutManager(this)
        //recycler_synthesize.itemAnimator = DefaultItemAnimator()
        swipe_synthesize.setOnRefreshListener { }

        //初始化需要的数据
        // binding.lifecycleOwner = this
        serviceApi =
            ServiceNet.init().initService("https://gank.io").create(GankServiceApi::class.java)
        repository = SynthesizeRepository(serviceApi)
        val db = Room.databaseBuilder(this, GanKDb::class.java, "gank.bd").build()
        dao = db.gankDao()
        val factory = SynthesizeViewModelFactory(repository)
        viewmodel = ViewModelProvider(this, factory).get(SynthesizeViewModel::class.java)
    }


    /**
     * 清楚数据库
     * */
    private fun clearDb() {
        btn_syn_clear.setOnClickListener {
            GlobalScope.launch {
                dao.clearCategory()
            }
        }
    }


    /**
     * 针对Paging中 Room+NetWork
     * */
    private fun pagingRoomAndNet() {
        btn_syn_01.setOnClickListener {
            //针对使用Paging分页框架
            //这里只展示Room+Net的模式
            //建议使用PagedList.BoundCallback,会出现在向数据库插入数据后 列表界面闪屏 以为PagedList是重新传递过来的
            //造成的原因 是因为在DIFF中直接对比了对象
            //如果使用Paging 使用ViewModel
            val result = viewmodel.getCategory("Girl", serviceApi, dao)
            result.pagedList?.observe(this, Observer {
                Log.e("Paging", "获取的数据$it")
                if (!it.isNullOrEmpty())
                    adapter.submitList(it)
                else {
                    Log.e("TAG", "数据为空")
                }
            })

            result.networkState.observe(this, Observer {
                Log.e("TAG", "网络链接状态:${it}")
            })

        }
    }


    /**
     * 针对数据边界回调
     * */
    private fun boundCallback() {
        //针对 List界面
        //使用List 先不使用PagedList
        btn_syn_04.setOnClickListener {
            getCategoryByList()
        }

        //针对 PagedList
        btn_syn_03.setOnClickListener {
            repository.getCategoryByPagedList(dao, serviceApi)
                .observe(this, Observer {
                    Log.e("TAG", "针对边界类---PagedList：$it")
                    if (!it.isNullOrEmpty())
                        adapter.submitList(it)

                })
        }
    }

    private var isInsert = false
    private fun getCategoryByList() {
        val mediatorLiveData = MediatorLiveData<List<Category>>()
        val adapter = CategoryAdapter03()
        recycler_synthesize.adapter = adapter
        insert()
        val result = dao.getCategory()

        mediatorLiveData.addSource(result) {
            mediatorLiveData.value = it
        }
        mediatorLiveData.observe(this, Observer {
            Log.e("TAG", "获取的数据$it")
            adapter.submitList(it)
        })

        recycler_synthesize.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == adapter.itemCount - 1 && !isInsert) {
                    isInsert = true
                    insert()
                }
            }
        })
    }

    private fun buildList() = mutableListOf<Category>().apply {
        (0..10).forEach {
            add(Category().apply {
                id = "${System.currentTimeMillis()}_$it"
                url = "http://gank.io/images/28fc02e86d584ff08802c8dcd9535b35"
            })
        }
    }

    private fun insert() {
        object : Thread() {
            override fun run() {

                sleep(3000)
                dao.insertCategory(*buildList().toTypedArray())
                Log.e("TAG", "开始添加数据")
                isInsert = false
            }
        }.start()
    }


    /**
     * 添加数据的请求状态
     *
     * 加入数据请求时的状态
     * */
    private fun withRequestState() {

    }

    /******************************下面的是测试 没用的***********************************/

    private fun subscribeUi() {


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
                Log.e("TAG", "testBoundDataPaged$it")
                adapter.submitList(it)

                it.addWeakCallback(it, object : PagedList.Callback() {
                    override fun onChanged(position: Int, count: Int) {
                        Log.e("TAG", "onChanged:$position -- $count")
                    }

                    override fun onInserted(position: Int, count: Int) {
                        Log.e("TAG", "onInserted:$position -- $count")
                    }

                    override fun onRemoved(position: Int, count: Int) {
                        Log.e("TAG", "onRemoved:$position -- $count")
                    }

                })
            })
        }
    }
}
