package com.wzy.arm.ui.holder

import android.view.View
import com.wzy.arm.R
import com.wzy.arms.base.BaseHolder
import com.wzy.arms.network.model.UserInfo
import com.wzy.arms.utils.GlideUtils
import kotlinx.android.synthetic.main.recycle_list.view.*

class HomeHolder<T>(itemView: View): BaseHolder<T>(itemView){

    override fun setData(data: T, position: Int) {
        /*var user:UserInfo? = data as UserInfo
        itemView.tv_name!!.text = user!!.login
        GlideUtils.glideRoundTransform(user.getAvatarUrl(), itemView.iv_avatar, R.mipmap.ic_launcher)
        */

        with(data as UserInfo){
            itemView.tv_name!!.text = this?.login
            GlideUtils.glideRoundTransform(this?.getAvatarUrl(), itemView.iv_avatar, R.mipmap.ic_launcher)

        }

    }

}