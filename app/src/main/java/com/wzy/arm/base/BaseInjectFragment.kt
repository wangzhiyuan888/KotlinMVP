package com.wzy.arm.base

import com.wzy.arm.di.component.FragmentComponent
import com.wzy.arms.base.BaseApp
import com.wzy.arms.base.BaseFragment
import com.wzy.arms.di.contract.BaseContract
import com.wzy.arms.di.module.FragmentModule
import com.wzy.arm.di.component.DaggerFragmentComponent
import javax.inject.Inject

/**
 * @author: wzy
 * date: 2019/11/11
 * desc: 基础InjectFragment
 */
abstract class BaseInjectFragment<T : BaseContract.BasePresenter<*>> : BaseFragment(), BaseContract.BaseView {

    @Inject
    lateinit var mPresenter: T

    protected val fragmentModule: FragmentModule get() = FragmentModule(this)

    protected val fragmentComponent: FragmentComponent
        get() = DaggerFragmentComponent.builder()
                .apiComponent(BaseApp.instance.mApiComponent)
                .fragmentModule(fragmentModule)
                .build()

    override fun showError(tag:String, msg: String) {
    }

    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }

    override fun complete() {
    }
}