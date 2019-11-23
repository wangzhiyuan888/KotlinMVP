package com.wzy.arms.base

import android.app.Application
import com.wzy.arms.di.component.ApiComponent
import com.wzy.arms.di.component.DaggerApiComponent
import com.wzy.arms.di.module.ApiModule
import com.wzy.arms.rxcache.Repository
import com.wzy.arms.utils.AppUtils
import com.wzy.arms.utils.CrashHandler
import com.wzy.arms.utils.LogUtils
import com.wzy.arms.utils.NetworkUtils

open class BaseApp: Application() {

    private var repository: Repository? = null
    val mApiComponent: ApiComponent by lazy {
        DaggerApiComponent.builder()
                .apiModule(ApiModule())
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        AppUtils.init(this)
        instance = this
        initNetwork()
        initCrashHandler()
        initLog()
        repository = Repository(mApiComponent.retrofitHelper).getInstance(getFilesDir())
    }

    fun getRepository(): Repository {
        return repository!!
    }

    companion object {
        lateinit var instance: BaseApp
    }

    private fun initNetwork() = NetworkUtils.startNetService(this)

    private fun initCrashHandler() = CrashHandler.init(this)

    private fun initLog() = LogUtils.init(this)

}