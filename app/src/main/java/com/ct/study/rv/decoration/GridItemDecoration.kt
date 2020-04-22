package com.ct.study.rv.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @ClassName : GridItemDecoration
 * @CreateDate : 2020/4/21 14:01
 * @Author : CT
 * @Description : 针对 GridLayoutManager
 *
 */
class GridItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
    }

    private fun getOffSize() {

    }

}