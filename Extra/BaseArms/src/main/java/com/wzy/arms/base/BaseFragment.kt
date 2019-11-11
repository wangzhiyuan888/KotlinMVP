package com.wzy.arms.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.ImageView
import android.widget.TextView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.trello.rxlifecycle2.components.support.RxFragment
import com.wzy.arms.R

abstract class BaseFragment: RxFragment() {
    //正式页面
    protected var mContentView: View? = null;
    //加载页面
    protected var mLoadingView: View? = null;
    //错误页面
    protected var mFailureView: View? = null;
    //加载Stub
    protected var mLoadingStub: ViewStub? = null;
    //失败Stub
    protected var mFailureStub: ViewStub? = null;
    protected var mActivity: Activity? = null
    protected var mInflater: LayoutInflater? = null
    protected var mContext: Context? = null
    // 标记初始化已经完成
    protected var mIsPrepared: Boolean = false
    // 标志Fragment是否可见
    protected var mIsVisible: Boolean = false

    private var mUnbinder: Unbinder? = null

    fun getDefaultId(): Int = R.layout.fragment_base

    override fun onAttach(context: Context?) {
        mActivity = context as? Activity
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mContentView != null) {
            val parent = mContentView?.parent as ViewGroup
            parent.removeView(mContentView)
        } else {
            mContentView = inflater.inflate(getDefaultId(), container, false)
            mActivity = getSupportActivity()
            mContext = mActivity
            this.mInflater = inflater
        }

        return mContentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLoadingStub = view.findViewById(R.id.loading_sub)
        val mContentStub = view.findViewById<ViewStub>(R.id.content_sub)
        mContentView = setLayout(mContentStub, getLayoutId())
        if (null != mContentView)
            mUnbinder = ButterKnife.bind(this, mContentView!!)
        mFailureStub = view.findViewById(R.id.failure_sub)

        initInject()
        initPresenter()
        initVariables()
        initWidget()
        initSetListener()
        finishCreateView(savedInstanceState)
        initDatas()
    }

    // ViewStub.inflate();执行完之后返回的View实际上是替换后的那个View的根布局
    private fun setLayout(mStub: ViewStub, layoutId: Int): View? {
        if (0 != layoutId) {
            mStub.layoutResource = layoutId
            return mStub.inflate()
        }
        return null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (mUnbinder != null && mUnbinder !== Unbinder.EMPTY)
            mUnbinder!!.unbind()
        this.mUnbinder = null
    }

    protected open fun initSetListener() {
    }

    protected open fun lazyLoadData() {
    }

    protected open fun initDatas() {
        loadData()
    }

    open fun finishCreateView(state: Bundle?) {
        mIsPrepared = true
        lazyLoad()
    }

    /**
     * 分离
     */
    override fun onDetach() {
        this.mActivity = null
        super.onDetach()
    }

    /**
     * 初始化RV
     */
    protected open fun initRecyclerView() {
    }

    /**
     * 初始化刷新
     */
    @SuppressLint("CheckResult")
    protected open fun initRefreshLayout() {
    }

    /**
     * 清除数据
     */
    protected open fun clearData() {
    }

    /**
     * 初始化Presenter
     */
    protected open fun initPresenter() {}

    /**
     * 初始化变量
     */
    open fun initVariables() {}

    /**
     * 重新加载数据
     */
    open fun reLoadData(){}

    /**
     * 懒加载
     */
    protected open fun lazyLoad() {
        if (!mIsPrepared || !mIsVisible) return
        lazyLoadData()
        mIsPrepared = false
    }

    protected open fun onInvisible() {
    }

    /**
     * 加载数据
     */
    protected open fun loadData() {}

    /**
     * 注入dagger2依赖
     */
    protected open fun initInject() {
    }

    protected open fun finishTask() {
    }

    /**
     * 布局
     * @return int
     */
    abstract fun getLayoutId(): Int

    /**
     * 对各种控件进行设置、适配、填充数据
     */
    open fun initWidget() {
    }

    protected open fun onVisible() {
        lazyLoad()
    }

    /**
     * Fragment数据的懒加载
     * @param isVisibleToUser
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            mIsVisible = true
            onVisible()
        } else {
            mIsVisible = false
            onInvisible()
        }
    }

    /**
     * 获取Activity
     * @return FragmentActivity
     */
    fun getSupportActivity(): FragmentActivity = super.getActivity() as FragmentActivity

    /**
     * 获取ApplicationContext 信息
     * @return Context
     */
    fun getApplicationContext(): Context? =
            if (this.mContext == null) {
                if (activity == null) {
                    null
                } else {
                    activity!!.application
                }
            } else {
                this.mContext?.applicationContext
            }

    /**
     * 隐藏View
     * @param views 视图
     */
    fun gone(vararg views: View) {
        if (views.isNotEmpty()) {
            for (view in views) {
                view.visibility = View.GONE
            }
        }
    }

    /**
     * 显示View
     * @param views 视图
     */
    fun visible(vararg views: View) {
        if (views.isNotEmpty()) {
            for (view in views) {
                view.visibility = View.VISIBLE
            }
        }
    }

    inner class BaseStagesTypeAdapter(fm: FragmentManager, private val mTitles: List<String>, private val mFragments: List<Fragment>) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            mFragments.let {
                return it[position]
            }
        }

        override fun getCount(): Int {
            return mTitles.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mTitles[position]
        }
    }

    /**
     * 显示异常内容；网络断开或无数据都属于异常情况
     */
    protected fun showFailureView(msg: String) {
        showFailureView(R.drawable.net_error, msg)
    }

    /**
     * 显示异常内容；网络断开或无数据都属于异常情况
     */
    protected fun showFailureView(imgResId: Int, msg: String) {
        mContentView!!.visibility = View.GONE
        if (null == mFailureView) {
            mFailureView = mFailureStub!!.inflate()
        }
        val mFailureImg = mFailureView!!.findViewById<ImageView>(R.id.failure_img)
        val mFailureMsg = mFailureView!!.findViewById<TextView>(R.id.failure_msg)
        //imgResId = imgResId > 0 ? imgResId : R.drawable.net_error;
        mFailureImg.visibility = if (imgResId > 0) View.VISIBLE else View.GONE
        mFailureImg.setImageResource(imgResId)
        mFailureMsg.text = if (TextUtils.isEmpty(msg)) "加载失败，点击重试" else msg
        mFailureView!!.setOnClickListener({ v ->
            hideFailureView()
            reLoadData()
        })
        mFailureView!!.visibility = View.VISIBLE
    }

    /**
     * 隐藏异常内容
     */
    protected fun hideFailureView() {
        mContentView!!.visibility = View.VISIBLE
        if (null != mFailureView)
            mFailureView!!.visibility = View.GONE
    }

    /**
     * 接收Activity传递过来的信息
     */
    fun sendMessageByActivity(action: String, value: Any){
        if (action in receiverActions()) {
            onReceive(action, value)

        }
    }

    /**
     * 向全局发送数据
     */
    open fun sendMsg(action:String, value: Any){
        (mActivity as BaseActivity).sendMsg(action, value)

    }

    /**
     * Activity接收消息
     */
    open fun onReceive(action: String, value: Any){ }

    /**
     * 接收需要的Actions
     */
    open fun receiverActions(): MutableList<String>{
        return mutableListOf()
    }

}