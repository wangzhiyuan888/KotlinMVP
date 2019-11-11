package com.wzy.arm.di.contract

import com.wzy.arms.di.contract.BaseContract


/**
 * @author: wzy
 * date: 2019/11/11
 * desc: 主界面- MainContract
 */
interface MainContract {

    interface View : BaseContract.BaseView {
    }

    interface Presenter<in T> : BaseContract.BasePresenter<T> {
    }
}