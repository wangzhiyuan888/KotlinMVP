package com.wzy.arm.di.presenter

import com.wzy.arm.di.contract.MainContract
import com.wzy.arms.di.presenter.RxPresenter
import com.wzy.arms.network.helper.RetrofitHelper
import javax.inject.Inject

/**
 * @author: wzy
 * date: 2019/11/11
 * desc: 主界面-MainPresenter
 */
class MainPresenter @Inject constructor(private val mRetrofitHelper: RetrofitHelper) :
        RxPresenter<MainContract.View>(), MainContract.Presenter<MainContract.View> {
}
