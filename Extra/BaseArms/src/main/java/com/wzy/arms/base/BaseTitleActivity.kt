package com.wzy.arms.base

import android.view.View
import com.wzy.arms.R

/**
 * 有Title的BaseActivity
 */
abstract class BaseTitleActivity: BaseActivity() {

    override fun loadingLayoutId(): Int {
        return 0
    }

    override fun onLoadingViewCreated(rootView: View, text: String) {
    }

    override fun onTopViewCreated(rootView: View?) {
        //绑定到butterknife
        if(null == rootView){
            return
        }

    }

    override fun topLayoutId(): Int {
        return R.layout.layout_default_title;
    }

    override fun onContentViewCreated(rootView: View?) {
        onCreateViewComplete()

    }

    /**
     * 页面加载完成后执行
     */
    abstract fun onCreateViewComplete();

}