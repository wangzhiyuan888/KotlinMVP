package com.wzy.arm.base

import com.wzy.arm.di.component.ActivityComponent
import com.wzy.arm.di.component.DaggerActivityComponent
import com.wzy.arm.di.module.ActivityModule
import com.wzy.arms.base.BaseActivity
import com.wzy.arms.base.BaseApp
import com.wzy.arms.di.contract.BaseContract
import javax.inject.Inject

/**
 * @author: wzy
 * date: 2019/11/11
 * desc: Activity模型
 */
abstract class BaseInjectActivity<T : BaseContract.BasePresenter<*>> : BaseActivity(), BaseContract.BaseView {

    @Inject
    lateinit var mPresenter: T

    protected val activityModule: ActivityModule get() = ActivityModule(this)

    protected val activityComponent: ActivityComponent
        get() = DaggerActivityComponent.builder()
                .apiComponent(BaseApp.instance.mApiComponent)
                .activityModule(activityModule)
                .build()

    override fun onDestroy() {
        mPresenter.detachView()
        BaseApp.instance.removeActivity(this)
        super.onDestroy()
    }

    override fun showError(msg: String) {
    }

    override fun complete() {
    }
}