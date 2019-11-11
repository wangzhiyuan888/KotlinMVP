package com.wzy.arms.base

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.annotation.NonNull
import android.view.View

abstract class DefaultAdapter<T>(protected val mInfos: MutableList<T>) : RecyclerView.Adapter<BaseHolder<T>>() {
    protected var mOnItemClickListener: OnRecyclerViewItemClickListener<T>? = null
    private var mHolder: BaseHolder<T>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(getLayoutId(viewType), parent, false)
        mHolder = getHolder(view, viewType)
        //设置Item点击事件
        mHolder!!.setOnItemClickListener(object : BaseHolder.OnViewClickListener {
            override fun onViewClick(view: View, position: Int) {
                if (mOnItemClickListener != null && mInfos!!.size > 0) {
                    mOnItemClickListener!!.onItemClick(view, viewType, mInfos!!.get(position), position)
                }
            }
        })
        return mHolder!!
    }

    /**
     * 绑定数据
     *
     * @param holder   [BaseHolder]
     * @param position 在 RecyclerView 中的位置
     */
    override fun onBindViewHolder(holder: BaseHolder<T>, position: Int) {
        holder.setData(mInfos!!.get(position), position)
    }


    /**
     * 返回数据总个数
     *
     * @return 数据总个数
     */
    override fun getItemCount(): Int {
        return mInfos!!.size
    }

    /**
     * 返回数据集合
     *
     * @return 数据集合
     */
    fun getInfos(): MutableList<T> {
        return mInfos!!
    }


    /**
     * 获得 RecyclerView 中某个 position 上的 item 数据
     *
     * @param position 在 RecyclerView 中的位置
     * @return 数据
     */
    fun getItem(position: Int): T? {
        return if (mInfos == null) null else mInfos!!.get(position)
    }

    /**
     * 让子类实现用以提供 [BaseHolder]
     *
     * @param v        用于展示的 [View]
     * @param viewType 布局类型
     * @return [BaseHolder]
     */
    @NonNull
    abstract fun getHolder(@NonNull v: View, viewType: Int): BaseHolder<T>

    /**
     * 提供用于 item 布局的 `layoutId`
     *
     * @param viewType 布局类型
     * @return 布局 id
     */
    abstract fun getLayoutId(viewType: Int): Int

    /**
     * 遍历所有 [BaseHolder], 释放他们需要释放的资源
     *
     * @param recyclerView [RecyclerView]
     */
    fun releaseAllHolder(recyclerView: RecyclerView?) {
        if (recyclerView == null) return
        for (i in recyclerView.childCount - 1 downTo 0) {
            val view = recyclerView.getChildAt(i)
            val viewHolder = recyclerView.getChildViewHolder(view)
            if (viewHolder != null && viewHolder is BaseHolder<*>) {
                viewHolder.onRelease()
            }
        }
    }

    /**
     * item 点击事件
     * @param <T>
    </T> */
    interface OnRecyclerViewItemClickListener<T> {

        /**
         * item 被点击
         * @param view 被点击的 [View]
         * @param viewType 布局类型
         * @param data 数据
         * @param position 在 RecyclerView 中的位置
         */
        fun onItemClick(view: View, viewType: Int, data: T, position: Int)
    }

    /**
     * 设置 item 点击事件
     * @param listener
     */
    fun setOnItemClickListener(listener: OnRecyclerViewItemClickListener<T>) {
        this.mOnItemClickListener = listener
    }

}