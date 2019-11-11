package com.wzy.arm.ui.adapter

import android.view.View
import com.wzy.arm.R
import com.wzy.arm.ui.holder.HomeHolder
import com.wzy.arms.base.BaseHolder
import com.wzy.arms.base.DefaultAdapter
import com.wzy.arms.network.model.User
import kotlinx.android.synthetic.main.recycle_list.*

class HomeAdapter(private val mDatas: MutableList<User>) : DefaultAdapter<User>(mDatas) {

    override fun getHolder(v: View, viewType: Int): BaseHolder<User> {
        return HomeHolder(v)
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.recycle_list;
    }

    fun onRefresh(users: List<User>, isRefresh : Boolean){
        if(isRefresh){
            mDatas.clear()
        }
        mDatas.addAll(users)
        notifyDataSetChanged()
    }

}