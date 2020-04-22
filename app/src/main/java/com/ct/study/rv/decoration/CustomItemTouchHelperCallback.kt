package com.ct.study.rv.decoration

import android.graphics.Canvas
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * @ClassName : CustomItemTouchHelperCallback
 * @CreateDate : 2020/4/22 9:59
 * @Author : CT
 * @Description : ItemTouchHelper 继承了 ItemDecoration
 *
 *  使用ItemTouchHelper可以实现 拖拽 和 滑动删除
 *  1.实现  ItemTouchHelper.Callback抽象类,主要方法有 getMovementFlags onMove onSwiped
 *
 */
class CustomItemTouchHelperCallback(private val listener: ItemTouchHelperListener) :
    ItemTouchHelper.Callback() {

    interface ItemTouchHelperListener {

        /**
         * 当用户拖拽ItemView的时候回调
         *
         * @param recyclerView
         * @param drag 被用户拖动的ItemView的ViewHolder
         * @param target 被覆盖的ItemView的ViewHolder
         * @return true 如果交换了位置
         * */
        fun onDrag(
            recyclerView: RecyclerView,
            drag: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean

        /**
         * 当用户 滑动ItemView的时候回调
         *
         * @param viewHolder 被用户滑动的ItemView的ViewHolder
         * @param direction 滑动的方向
         * */
        fun onSwipe(viewHolder: RecyclerView.ViewHolder, direction: Int)
    }


    /**
     * 在此方法中定义 拖拽 和 滑动的方向
     * */
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        //表示 可以上下拖拽
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        //表示 可以左右移动
        val moveFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

        return makeMovementFlags(dragFlags, moveFlags)
    }

    /**
     * 当 拖拽的时候回调的方法
     *
     * @param viewHolder 用户Drag的ItemView的Holder对象
     * @param target Drag的ItemView覆盖的对象,也就是要交换位置的对象
     * */
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return listener.onDrag(recyclerView, viewHolder, target)
    }

    /**
     * 表示 当前target对应的ItemView是否可以拖拽
     * @return true 表示可以拖拽
     * */
    override fun canDropOver(
        recyclerView: RecyclerView,
        current: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return super.canDropOver(recyclerView, current, target)
    }

    /**
     *用于 当拖拽的ItemView和覆盖在一个ItemView上时
     * 用来增大或缩小拖拽ItemView
     * */
    override fun getBoundingBoxMargin(): Int {
        return super.getBoundingBoxMargin()
    }

    override fun isLongPressDragEnabled(): Boolean {
        return super.isLongPressDragEnabled()
    }

    /**
     * 当滑动删除时回调的方法
     * */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.onSwipe(viewHolder, direction)
    }

    /**
     * 是否允许Swipe(滑动)操作
     * */
    override fun isItemViewSwipeEnabled(): Boolean {
        return super.isItemViewSwipeEnabled()
    }

    /**
     * Swipe滑动百分之多少就消失默认 50%
     * */
    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return super.getSwipeThreshold(viewHolder)
    }

    /**
     * 设置Swipe滑动的逃逸速度
     * */
    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return super.getSwipeEscapeVelocity(defaultValue)
    }

    /**
     * 当 Swipe和Drag状态结束时调用
     * */
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
    }


}