package com.ct.aac.synthesize.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ct.aac.databinding.ItemSynthesizeCategoryBinding
import com.ct.aac.synthesize.vo.Category

/**
 * @ClassName : CategoryAdapter
 * @CreateDate : 2020/4/15 9:28
 * @Author : CT
 * @Description :
 *
 */
class CategoryAdapter03 : ListAdapter<Category, CategoryAdapter03.ViewHolder>(DIFF()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemSynthesizeCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class ViewHolder(val binding: ItemSynthesizeCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Category) {
            binding.model = model
        }


    }

    class DIFF : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem.desc == newItem.desc

    }


}