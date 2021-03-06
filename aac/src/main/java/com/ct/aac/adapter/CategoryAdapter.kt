package com.ct.aac.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ct.aac.databinding.ItemCategoryBinding
import com.ct.aac.vo.Category2

class CategoryAdapter : PagedListAdapter<Category2, CategoryAdapter.ViewHolder>(DIFF()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class ViewHolder(private val bind: ItemCategoryBinding) :
        RecyclerView.ViewHolder(bind.root) {

        fun bind(model: Category2) {
            bind.model = model
        }
    }


    class DIFF : DiffUtil.ItemCallback<Category2>() {
        override fun areItemsTheSame(oldItem: Category2, newItem: Category2): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Category2, newItem: Category2): Boolean {
            return oldItem.id == newItem.id
        }

    }


}