package com.wzy.arm.di.presenter

import com.wzy.arm.di.contract.HomeContract
import com.wzy.arms.di.presenter.RxPresenter
import com.wzy.arms.di.subscriber.BaseSubscriber
import com.wzy.arms.network.helper.RetrofitHelper
import com.wzy.arms.network.model.User
import com.wzy.arms.utils.rxSchedulerHelper
import javax.inject.Inject

/**
 * @author: wzy
 * date: 2019/11/11
 * desc: 精选界面-SelectionPresenter
 */
class HomePresenter
@Inject
constructor(private val mRetrofitHelper: RetrofitHelper) :
        RxPresenter<HomeContract.View>(), HomeContract.Presenter<HomeContract.View> {
    override fun requestUsers(since: Int?, perPage: Int?) {
        val subscriber = mRetrofitHelper.requestUsers(since, perPage)
                .compose(rxSchedulerHelper())
                .subscribeWith(object : BaseSubscriber<MutableList<User>>("requestUsers", mView) {
                    override fun onSuccess(mData: MutableList<User>) {
                        mView?.showUsers(mData)
                    }
                })
        addSubscribe(subscriber)
    }
}