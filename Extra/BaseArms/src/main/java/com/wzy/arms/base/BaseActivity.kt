package com.wzy.arms.base

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewStub
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.wzy.arms.R
import com.wzy.arms.base.struct.FunctionsManager
import com.wzy.arms.helper.ActivityCollector
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.wzy.arms.utils.AppUtils
import com.wzy.arms.utils.NetworkUtils
import com.wzy.arms.utils.StatusBarUtil
import com.wzy.arms.utils.ToastUtils

abstract class BaseActivity: RxAppCompatActivity(){

    protected var mContext: Context? = null
    //加载正常的显示View
    var mContentView: View ?= null
    //错误页面
    protected var mFailureView: View? = null;
    //加载条的View
    var mRootView:View? = null
    //加载条的View
    var mLoadingView:View? = null
    //失败Stub
    protected var mFailureStub: ViewStub? = null;

    private var mUnbinder: Unbinder? = null

    var fmager: FunctionsManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCollector.addActivity(this)
        setContentView(R.layout.activity_base)
        mContext = this

        initStatusBar()
        initInject()
        initPresenter()
        initVariables()
        BaseApp.instance.addActivity(this)
        initStub()
        initWidget()
        loadDatas()

    }

    override fun onDestroy() {
        if (null != fmager)
            fmager = null
        super.onDestroy()
        if (mUnbinder != null && mUnbinder !== Unbinder.EMPTY)
            mUnbinder!!.unbind()
        this.mUnbinder = null
        ActivityCollector.removeActivity(this)
    }

    /**
     * 显示等待框
     **/
    protected fun showLoading(text: String){
        if (null == mLoadingView) {
            var mLoadingStub = findViewById<ViewStub>(R.id.loading_sub)
            mLoadingView = setLayout(mLoadingStub, loadingLayoutId())
            if(null == mLoadingView){
                mLoadingView = mLoadingStub.inflate()
            }else{
                onLoadingViewCreated(mLoadingView!!, text)

            }
        }
    }

    /**
     * 取消等待框
     **/
    protected open fun hideLoading(){
        if(null != mLoadingView){
            mLoadingView!!.visibility = View.GONE
        }
    }

    /**
     * 初始化StubView
     */
    private fun initStub(){
        mRootView = findViewById(R.id.base_root)
        val mTopStub = findViewById<ViewStub>(R.id.top_sub)
        val mContentStub = findViewById<ViewStub>(R.id.content_sub)
        onTopViewCreated(setLayout(mTopStub, topLayoutId()))
        mContentView = setLayout(mContentStub, contentLayoutId())
        onContentViewCreated(mContentView)
        if (null != mContentView)
            mUnbinder = ButterKnife.bind(this, mContentView!!)
        mFailureStub = findViewById(R.id.failure_sub)

    }

    private fun setLayout(mStub: ViewStub, layoutId: Int):View? {
        if (0 != layoutId) {
            mStub.layoutResource = layoutId
            return mStub.inflate()
        }
        return null;
    }

    /**
     * Fragment 与Activity间的互相通讯
     * @param tag
     */
    open fun setFunctionsForFragment(tag: String) {
    }

    /**
     * 向存活的Activity发送消息
     */
    open fun sendMsg(action:String, value: Any){
        if (action in receiverActions()) {
            ActivityCollector.sendMessage(action, value)

        }

    }

    /**
     * Activity接收消息
     */
    open fun onReceive(action: String, value: Any){}

    /**
     * 自定义等待框 布局文件ID
     *
     *
     * 默认值 0
     */
    abstract fun loadingLayoutId(): Int

    /**
     * 自定义等待框 根View
     *
     * @param text 自定义LoadingMsg
     */
    abstract fun onLoadingViewCreated(rootView: View, text: String)

    /**
     * 顶部 根View
     */
    abstract fun onTopViewCreated(rootView: View?)

    /**
     * 顶部 布局文件ID
     **/
    abstract fun topLayoutId(): Int

    /**
     * 中间部分 根View
     */
    abstract fun onContentViewCreated(rootView: View?)

    /**
     * 中间部分 布局文件ID
     */
    abstract fun contentLayoutId(): Int

    /**
     * 接收需要的Actions
     */
    open fun receiverActions(): MutableList<String>{
        return mutableListOf()
    }

    open fun initVariables() {}

    /**
     * 设置沉浸式状态栏
     */
    private fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 设置沉浸式状态栏
            val decorView = window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            // 设置状态栏透明
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun initStatusBar() = StatusBarUtil.setColorNoTranslucent(mContext as Activity, AppUtils.getColor(R.color.colorPrimary))

    open fun initPresenter() {
    }

    open fun initInject() {
    }

    open fun loadDatas() {
        loadData()
    }

    /**
     * 初始化控件
     */
    open fun initWidget() {}

    /**
     * 加载数据
     */
    open fun loadData() {}



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
            if (!NetworkUtils.isConnected(AppUtils.appContext!!)) {
                ToastUtils.showToast("当前网络连接异常")
                return@setOnClickListener
            }
            hideFailureView()
            loadData()
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

}