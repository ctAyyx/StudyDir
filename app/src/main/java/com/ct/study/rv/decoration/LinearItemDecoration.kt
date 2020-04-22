package com.ct.study.rv.decoration

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @ClassName : LinearItemDecoration
 * @CreateDate : 2020/4/21 10:57
 * @Author : CT
 * @Description :
 *
 */
class LinearItemDecoration : RecyclerView.ItemDecoration() {

    private val mPaint = Paint().apply {
        color = Color.YELLOW
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        //为每个ItemView添加一个5px的间隔
        outRect.bottom = 5

        //判读 如果是最后一个ItemView 则不添加间隔
        val position = parent.getChildAdapterPosition(view)
        //Log.e("TAG", "角标:$position")
        if ((position + 1) == parent.adapter?.itemCount)
            outRect.bottom = 0

    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        //Log.e("TAG", "拥有的Child:$childCount")
        for (i in 0 until childCount) {
            val childView = parent.getChildAt(i)
            //获取相对与父控件的位置
            val top = childView.top
            val left = childView.left
            val bottom = childView.bottom
            val right = childView.right
            //Log.e("TAG", "当前子View$i 的位置--Top:$top -- Bottom:$bottom -- Left:$left --Right:$right")


            //我们开始绘制间隔

            if (!isLastItemView(childView, parent)) {
                val rect = Rect()
                rect.left = left
                rect.top = bottom
                rect.right = right
                rect.bottom = rect.top + 5
                c.drawRect(rect, mPaint)
            }

        }

    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
    }


    private fun isLastItemView(childView: View, parent: RecyclerView) =
        parent.getChildAdapterPosition(childView) + 1 == parent.adapter?.itemCount
}