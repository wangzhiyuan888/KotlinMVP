package com.wzy.arm.ui.holder

import android.view.View
import com.wzy.arm.R
import com.wzy.arms.base.BaseHolder
import com.wzy.arms.network.model.User
import com.wzy.arms.utils.GlideUtils
import kotlinx.android.synthetic.main.recycle_list.view.*

class HomeHolder<T>(itemView: View): BaseHolder<T>(itemView){

    override fun setData(data: T, position: Int) {
        var user:User =  data as User
        with(user){
            itemView.tv_name!!.text = login
            GlideUtils.glideRoundTransform(avatarUrl, itemView.iv_avatar, R.mipmap.ic_launcher)

        }
    }

}