package com.wzy.arm.ui.fragment

import kotlinx.android.synthetic.main.fragment_home.*
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.liaoinstan.springview.container.DefaultFooter
import com.liaoinstan.springview.container.DefaultHeader
import com.wzy.arm.R
import com.wzy.arm.base.BaseInjectFragment
import com.wzy.arm.di.contract.HomeContract
import com.wzy.arm.di.presenter.HomePresenter
import com.wzy.arms.network.model.PageInfo
import com.wzy.arms.network.model.User
import com.wzy.arms.utils.ArmsUtils
import com.wzy.arms.utils.ToastUtils
import com.liaoinstan.springview.widget.SpringView
import com.wzy.arm.ui.activity.MainActivity
import com.wzy.arm.ui.adapter.HomeAdapter
import com.wzy.arms.iter.ReceiverAction

/**
 * @author: wzy
 * date: 2019/11/11
 * desc:
 */
class HomeFragment():BaseInjectFragment<HomePresenter>(), SpringView.OnFreshListener, HomeContract.View {

    override fun receiverActions(): MutableList<String> {
        return mutableListOf(ReceiverAction.RECEIVER_ACTION_REQUEST_USER)
    }

    protected var mLayoutManager: GridLayoutManager? = null

    protected var mAdapter: HomeAdapter? = HomeAdapter(ArrayList())

    private var pageInfo: PageInfo = PageInfo();

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initInject() = fragmentComponent.inject(this)

    override fun initPresenter() = mPresenter.attachView(this)

    override fun initVariables() {
    }

    override fun reLoadData() {
        loadData()
    }

    override fun initWidget() {
        initRecyclerView()
        recyclerView.setAdapter(mAdapter)

    }

    override fun loadData() {
        mPresenter.requestUsers(pageInfo.page, pageInfo.pageNumber);

    }

    override fun onRefresh() {
        pageInfo.page = 1
        mPresenter.requestUsers(pageInfo.page, pageInfo.pageNumber);
    }

    override fun onLoadmore() {
        mPresenter.requestUsers(pageInfo.page, pageInfo.pageNumber);

    }

    /**
     * 初始化RecyclerView
     */
    override fun initRecyclerView() {
        mLayoutManager = GridLayoutManager(activity, 2);
        springView.setHeader(DefaultHeader(activity));
        springView.setFooter(DefaultFooter(activity));
        ArmsUtils.configRecyclerView(recyclerView, mLayoutManager!!);
        springView.setListener(this)
    }

    override fun showUsers(users: MutableList<User>) {
        mAdapter!!.onRefresh(users, pageInfo.page == 1)
        pageInfo.page = pageInfo.page+1

        (mActivity as MainActivity).sendMsg(ReceiverAction.RECEIVER_ACTION_REQUEST_USER,"大王-早上好!")

    }

    override fun showError(tag: String, msg: String) {
        ToastUtils.showToast(msg)
        this.springView.onFinishFreshAndLoad()
        showFailureView(msg)

    }

    override fun complete() {
        this.springView.onFinishFreshAndLoad()
    }

    override fun onReceive(action: String, value: Any) {
        Log.d("HomeFragment", "action:"+action+"     value:"+value as String)
    }

}