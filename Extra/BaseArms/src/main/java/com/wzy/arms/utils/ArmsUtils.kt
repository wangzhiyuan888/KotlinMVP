package com.wzy.arms.utils

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView

object ArmsUtils {

    /**
     * 配置 RecyclerView
     *
     * @param recyclerView
     * @param layoutManager
     */
    @Deprecated("Use {@link #configRecyclerView(RecyclerView, RecyclerView.LayoutManager)} instead")
    fun configRecycleView(recyclerView: RecyclerView, layoutManager: RecyclerView.LayoutManager) {
        recyclerView.layoutManager = layoutManager
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
    }

    /**
     * 配置 RecyclerView
     *
     * @param recyclerView
     * @param layoutManager
     */
    fun configRecyclerView(recyclerView: RecyclerView, layoutManager: RecyclerView.LayoutManager) {
        recyclerView.layoutManager = layoutManager
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
    }
}
