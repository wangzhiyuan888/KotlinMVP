package com.wzy.arm.base

import android.view.View
import com.wzy.arm.di.component.FragmentComponent
import com.wzy.arms.base.BaseApp
import com.wzy.arms.base.BaseFragment
import com.wzy.arms.di.contract.BaseContract
import com.wzy.arms.di.module.FragmentModule
import com.wzy.arm.di.component.DaggerFragmentComponent
import com.wzy.arms.utils.BtnClickHelper
import javax.inject.Inject

/**
 * @author: wzy
 * date: 2019/11/11
 * desc: 基础InjectFragment
 */
abstract class BaseInjectFragment<T : BaseContract.BasePresenter<*>> : BaseFragment(), BaseContract.BaseView, View.OnClickListener {

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

    override fun onClick(v: View?) {
        // 防止重复多次点击
        if (BtnClickHelper.isFastDoubleClick()) {
            return
        }
        onOtherClick(v!!)
    }

    /**
     * 由于这里已经实现了OnClickListener接口，所以继承此类后，不需要再实现OnClickListener接口，所有的View点击事件️
     * 都在这里操作即可
     */
    abstract fun onOtherClick(v: View)
}