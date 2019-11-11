package com.wzy.arms.di.subscriber

import com.wzy.arms.di.contract.BaseContract
import com.wzy.arms.network.helper.ApiException
import com.wzy.arms.utils.AppUtils
import com.wzy.arms.utils.LogUtils
import com.wzy.arms.utils.NetworkUtils
import com.wzy.arms.utils.ToastUtils
import io.reactivex.subscribers.ResourceSubscriber
import retrofit2.HttpException
import java.net.SocketTimeoutException

/**
 * @author: wzy
 * date: 2019/11/11
 * desc: 统一处理订阅者
 */
abstract class BaseSubscriber<T>(private val view: BaseContract.BaseView?) : ResourceSubscriber<T>() {
    private var msg: String? = null
    abstract fun onSuccess(mData: T)

    constructor(view: BaseContract.BaseView?, msg: String?) : this(view) {
        this.msg = msg
    }

    open fun onFailure(code: Int, message: String) {
    }

    override fun onStart() {
        super.onStart()
        if (!NetworkUtils.isConnected(AppUtils.appContext!!)) {
            // Logger.d("没有网络");
            ToastUtils.showToast("当前网络连接异常")
            return
        }
    }

    override fun onComplete() {
    }

    override fun onNext(response: T) {
        view?.let {
            it.complete()
            onSuccess(response)
        } ?: return
    }

    override fun onError(e: Throwable) {
        view?.let {
            if (!msg.isNullOrEmpty()) it.showError(msg!!)
            else {
                when (e) {
                    is ApiException -> it.showError(e.toString())
                    is SocketTimeoutException -> it.showError("服务器响应超时ヽ(≧Д≦)ノ")
                    is HttpException -> it.showError("数据加载失败ヽ(≧Д≦)ノ")
                    else -> {
                        view.showError("未知错误ヽ(≧Д≦)ノ")
                        LogUtils.e("MYERROR:$e")
                    }
                }
            }
        } ?: return
    }
}