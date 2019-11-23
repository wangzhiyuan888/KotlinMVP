package com.wzy.arms.base

import android.support.v7.widget.RecyclerView
import android.view.View
import android.support.annotation.NonNull
import org.jetbrains.annotations.NotNull

abstract class BaseHolder<T>
constructor(@NotNull itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
    protected var mOnViewClickListener: OnViewClickListener? = null

    init{
        itemView!!.setOnClickListener(this);
        //绑定 ButterKnife， java中需要而kotlin中不需要
        //ThirdViewUtil.bindTarget(this, itemView);
    }

    /**
     * 设置数据
     *
     * @param data     数据
     * @param position 在 RecyclerView 中的位置
     */
    abstract fun setData(@NonNull data: T, position: Int)

    /**
     * 在 Activity 的 onDestroy 中使用 [DefaultAdapter.releaseAllHolder] 方法 (super.onDestroy() 之前)
     * [BaseHolder.onRelease] 才会被调用, 可以在此方法中释放一些资源
     */
    fun onRelease() {

    }

    override fun onClick(view: View) {
        if (mOnViewClickListener != null) {
            mOnViewClickListener!!.onViewClick(view, this.position)
        }
    }
    /**
     * item 点击事件
     */
    interface OnViewClickListener {

        /**
         * item 被点击
         *
         * @param view     被点击的 [View]
         * @param position 在 RecyclerView 中的位置
         */
        fun onViewClick(view: View, position: Int)
    }

    fun setOnItemClickListener(listener: OnViewClickListener) {
        this.mOnViewClickListener = listener
    }
}