package com.wzy.arms.di.contract

/**
 * @author: wzy
 * date: 2019/11/11
 * desc: 基础Contract
 */
interface BaseContract {
    interface BaseView {

        /**
         *  请求错误
         */
        fun showError(tag: String, msg: String)

        open fun showNetWorkError(msg: String){}

        fun complete()
    }

    interface BasePresenter<in T> {
        /**
         * 绑定
         *
         * @param view view
         */
        fun attachView(view: T)

        /**
         * 解绑
         */
        fun detachView()
    }
}