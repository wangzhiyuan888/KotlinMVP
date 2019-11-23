package com.wzy.arm.di.presenter

import com.wzy.arm.di.contract.MainContract
import com.wzy.arms.di.presenter.RxPresenter

/**
 * @author: wzy
 * date: 2019/11/11
 * desc: 主界面-MainPresenter
 */
class MainPresenter : RxPresenter<MainContract.View>(), MainContract.Presenter<MainContract.View> {
}
