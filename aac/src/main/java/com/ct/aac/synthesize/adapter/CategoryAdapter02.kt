package com.ct.aac.synthesize.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ct.aac.adapter.DataBoundListAdapter
import com.ct.aac.databinding.ItemSynthesizeCategoryBinding
import com.ct.aac.synthesize.vo.Category
import java.util.concurrent.Executors

/**
 * @ClassName : CategoryAdapter
 * @CreateDate : 2020/4/15 9:28
 * @Author : CT
 * @Description :
 *
 */
class CategoryAdapter02 : DataBoundListAdapter<Category, ItemSynthesizeCategoryBinding>(
    diffCallback = DIFF()
) {

    class DIFF : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem.title == newItem.title

    }

    override fun createBinding(parent: ViewGroup): ItemSynthesizeCategoryBinding {
        return ItemSynthesizeCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    override fun bind(binding: ItemSynthesizeCategoryBinding, item: Category) {
        binding.model = item
    }


}