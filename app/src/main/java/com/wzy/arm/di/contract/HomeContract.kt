package com.wzy.arm.di.contract

import com.wzy.arms.di.contract.BaseContract
import com.wzy.arms.network.model.UserInfo

/**
 * @author: wzy
 * date: 2019/11/11
 * desc: 精选界面-SelectionContract
 */
interface HomeContract {

    interface View : BaseContract.BaseView {

        /**
         * 加载结果
         */
        fun showUsers(users: MutableList<UserInfo>)
    }

    interface Presenter<in T> : BaseContract.BasePresenter<T> {
        fun requestUsers(since: Int?, perPage:Int?, update: Boolean?)
    }
}