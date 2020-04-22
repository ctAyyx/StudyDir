package com.ct.study.rv.decoration

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.util.Size
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ct.study.rv.vo.Category

/**
 * @ClassName : StickHeaderItemDecoration
 * @CreateDate : 2020/4/21 16:17
 * @Author : CT
 * @Description : 垂直 粘性头部
 *
 */
class StickHeaderItemDecoration(private val stickProvider: StickProvider) :
    RecyclerView.ItemDecoration() {
    private val mPaint = Paint().apply {
        color = Color.YELLOW
    }
    private val SIZE = 80

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val adapter = parent.adapter
        if (adapter == null || adapter !is StickProvider)
            return
        val position = parent.getChildAdapterPosition(view)
        if (stickProvider.isStick(position))
            outRect.top = SIZE
        else
            outRect.top = 5


    }


    /**
     * 直接 头部 和 粘性头部
     * */

//    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//        //绘制顶部Header
//        val adapter = parent.adapter
//        if (adapter == null || adapter !is StickProvider)
//            return
//
//        val parentLeft = parent.paddingLeft
//        val parentRight = parent.width - parent.paddingRight
//
//        val childCount = parent.childCount
//
//        var tag = ""
//        var preTag: String? = null;
//        for (i in 0 until childCount) {
//            val childView = parent.getChildAt(i) ?: continue
//            val adapterPosition = parent.getChildAdapterPosition(childView);
//            //当前Item的Top
//            val top = childView.top
//            val bottom = childView.bottom
//            preTag = tag
//            tag = stickProvider.getModel(adapterPosition).type
//            //判断下一个是不是分组的头部
//            if (preTag == tag)
//                continue
//            //这里面我把每个分组的头部显示的文字列表单独提出来了,为了测试方便用,
//            val name = "头部"
//            var height = Math.max(top, SIZE)
//            Log.e("TAG", "当前View$adapterPosition -- $i --$top -- $height")
//            //判断下一个Item是否是分组的头部
//            if (adapterPosition + 1 < adapter.itemCount) {
//                val nextTag = stickProvider.getModel(adapterPosition + 1).type
//                //这里就是实现渐变效果的地方
//                //因为如果遍历到
//                if (tag != nextTag)
//                    height = Math.min(bottom, height);
//            }
//            val rect = Rect()
//            rect.left = parentLeft
//            rect.top = height - SIZE
//            rect.right = parentRight
//            rect.bottom = height
//            Log.e("TAG", "当前头部:$rect")
//            mPaint.setColor(Color.YELLOW)
//
//            c.drawRect(rect, mPaint)
////            mPaint.setColor(Color.BLACK)
////            mPaint.textSize = 32f
////            c.drawText(name, parentLeft.toFloat(), rect.bottom.toFloat(), mPaint)
//        }
//
//
//    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val adapter = parent.adapter ?: return
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val childView = parent.getChildAt(i)
            val adapterPosition = parent.getChildAdapterPosition(childView)
            if (adapterPosition + 1 >= adapter.itemCount) continue

            val tag = stickProvider.getModel(adapterPosition).type
            val nextTag = stickProvider.getModel(adapterPosition + 1).type
            var height = childView.top
            val rect = Rect()
            rect.left = childView.left
            rect.right = childView.right
            if (i == 0) {
                rect.top = 0
                if (tag != nextTag)
                    rect.bottom = Math.min(SIZE, childView.bottom)
                else
                    rect.bottom = SIZE

                mPaint.setColor(Color.YELLOW)
                c.drawRect(rect, mPaint)
                mPaint.textSize = 32f
                mPaint.setColor(Color.BLACK)
                c.drawText("明治", 12f, rect.bottom - 10f, mPaint)
            } else {
                if (!stickProvider.isStick(adapterPosition)) continue
                rect.top = height - SIZE
                rect.bottom = height

                mPaint.setColor(Color.YELLOW)
                c.drawRect(rect, mPaint)
                mPaint.textSize = 32f
                mPaint.setColor(Color.BLACK)
                c.drawText("明治", 12f, rect.bottom - 10f, mPaint)
            }

        }

    }

    /***************************在 onDraw中绘制头部  在onDrawOver中绘制粘性头部********************************/

//    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//
//        val adapter = parent.adapter
//        if (adapter == null || adapter !is StickProvider)
//            return
//        //第一步 绘制所有头部
//        val childCount = parent.childCount
//        //这里直接忽略第一个View
//        //因为要绘制粘性头部
//        for (i in 1 until childCount) {
//            val childView = parent.getChildAt(i)
//            val position = parent.getChildAdapterPosition(childView)
//            if (stickProvider.isStick(position)) {
//                val top = childView.top
//                val left = childView.left
//                val bottom = childView.bottom
//                val right = childView.right
////                Log.e(
////                    "TAG",
////                    "当前子View$i 的位置--Top:$top -- Bottom:$bottom -- Left:$left --Right:$right"
////                )
//                val rect = Rect()
//                rect.left = left
//                rect.top = top - SIZE
//                rect.right = right
//                rect.bottom = top
//                mPaint.setColor(Color.YELLOW)
//                c.drawRect(rect, mPaint)
//                mPaint.textSize = 32f
//                mPaint.setColor(Color.BLACK)
//                c.drawText("明治", 12f, rect.bottom - 10f, mPaint)
//            }
//        }
//
//    }
//
//    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//        val adapter = parent.adapter
//        if (adapter == null || adapter !is StickProvider)
//            return
//
//        //获取第一个childView
//        val childView = parent.getChildAt(0) ?: return
//
//        //获取当前childView中Adapter中的角标
//        val currentPosition = parent.getChildAdapterPosition(childView)
//        if (currentPosition + 1 >= adapter.itemCount)
//            return
//        //获取下一个childView
//        val tag = stickProvider.getModel(currentPosition).type
//        val nextTag = stickProvider.getModel(currentPosition + 1).type
//
//        var height = SIZE
//
//        if (tag != nextTag) {
//            //表示下一个ItemView是Header
//            height = Math.min(SIZE, childView.bottom)
//        }
//
//        val rect = Rect()
//        rect.left = childView.left
//        rect.top = 0
//        rect.right = childView.right
//        rect.bottom = height
//
//        mPaint.setColor(Color.YELLOW)
//        c.drawRect(rect, mPaint)
//        mPaint.textSize = 32f
//        mPaint.setColor(Color.BLACK)
//        c.drawText("明治", 12f, rect.bottom - 10f, mPaint)
//
//    }


    interface StickProvider {
        fun isStick(position: Int): Boolean

        fun getModel(position: Int): Category
    }

}