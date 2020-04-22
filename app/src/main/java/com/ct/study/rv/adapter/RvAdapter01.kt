package com.ct.study.rv.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ct.study.R
import com.ct.study.rv.decoration.CustomItemTouchHelperCallback
import com.ct.study.rv.decoration.StickHeaderItemDecoration
import com.ct.study.rv.vo.Category
import java.util.*

/**
 * @ClassName : RvAdapter01
 * @CreateDate : 2020/4/21 10:36
 * @Author : CT
 * @Description :
 *
 */
class RvAdapter01 : ListAdapter<Category, RvAdapter01.ViewHolder>(DIFF()),
    StickHeaderItemDecoration.StickProvider,
    CustomItemTouchHelperCallback.ItemTouchHelperListener {
    private var mList: MutableList<Category>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_rv_gank,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun submitList(list: MutableList<Category>?) {
        mList = list
        super.submitList(list)
    }

    class ViewHolder(private val rootView: View) : RecyclerView.ViewHolder(rootView) {
        private val img = rootView.findViewById<AppCompatImageView>(R.id.img_itemRv)
        private val tv = rootView.findViewById<AppCompatTextView>(R.id.tv_itemRv)
        fun bind(model: Category) {
            Glide.with(img)
                .load(model.url)
                .into(img)
            tv.text = model.id
        }

    }

    class DIFF : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem.desc == newItem.desc

    }

    /***********************添加头部实现的回调************************/
    override fun isStick(position: Int): Boolean {
        if (position == 0)
            return true
        val lastModel = getItem(position - 1)
        val model = getItem(position)

        val result = model.type != lastModel.type
        //Log.e("TAG", "$position 是否是头部:$result -- ${model.type}")
        return result
    }


    override fun getModel(position: Int): Category {
        return getItem(position)
    }


    override fun onDrag(
        recyclerView: RecyclerView,
        drag: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        mList?.run {
            val from = drag.adapterPosition
            val to = target.adapterPosition
            Collections.swap(this, from, to)
            notifyItemMoved(from, to)
            Log.e("TAG", "DragITemView：$from -- $to")
            true
        }

        return false
    }

    override fun onSwipe(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        Log.e("TAG", "滑动:$direction -- ${viewHolder.adapterPosition}'")
    }


}