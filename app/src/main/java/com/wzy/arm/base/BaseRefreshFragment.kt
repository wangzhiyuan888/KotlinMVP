package com.wzy.arm.base

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import com.wzy.arm.R
import com.wzy.arms.di.contract.BaseContract
import com.wzy.arms.utils.AppUtils
import com.wzy.arms.utils.ToastUtils

/**
 * @author: wzy
 * date: 2019/11/11
 * desc: 基础刷新Fragment
 */
abstract class BaseRefreshFragment<T : BaseContract.BasePresenter<*>, K> : BaseInjectFragment<T>(), SwipeRefreshLayout.OnRefreshListener {
    protected var mRecycler: RecyclerView? = null
    protected var mRefresh: SwipeRefreshLayout? = null
    protected var mIsRefreshing = false
    protected var mList = mutableListOf<K>()

    override fun initRefreshLayout() {
        mRefresh?.let {
            it.setColorSchemeResources(R.color.colorPrimary)
            mRecycler?.post {
                it.isRefreshing = true
                lazyLoadData()
            }
            it.setOnRefreshListener(this)
        }
    }

    override fun onRefresh() {
        clearData()
        lazyLoadData()
    }

    override fun clearData() {
        mIsRefreshing = true
    }

    override fun finishCreateView(state: Bundle?) {
        mRefresh = mContentView?.findViewById(R.id.refresh) as SwipeRefreshLayout?
        mRecycler = mContentView?.findViewById(R.id.recycler) as RecyclerView?
        mIsPrepared = true
        lazyLoad()
    }

    override fun lazyLoad() {
        if (!mIsPrepared || !mIsVisible) return
        initRefreshLayout()
        initRecyclerView()
        mRefresh ?: lazyLoadData()
        mIsPrepared = false
    }

    override fun complete() {
        AppUtils.runOnUIDelayed(Runnable{
            mRefresh?.let {
                it.isRefreshing = false
            }
        }, 550)
        if (mIsRefreshing) {
            mList.clear()
            clear()
            ToastUtils.showToast("刷新成功")
        }
        mIsRefreshing = false
    }

    protected open fun clear() {
    }

    override fun initWidget() {
    }
}