package com.wzy.arms.base

import android.app.Activity
import android.app.Application
import com.wzy.arms.di.component.ApiComponent
import com.wzy.arms.di.component.DaggerApiComponent
import com.wzy.arms.di.module.ApiModule
import com.wzy.arms.utils.AppUtils
import com.wzy.arms.utils.CrashHandler
import com.wzy.arms.utils.LogUtils
import com.wzy.arms.utils.NetworkUtils

open class BaseApp: Application() {
    private var allActivities: HashSet<Activity>? = null
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
    }

    companion object {
        lateinit var instance: BaseApp
    }

    private fun initNetwork() = NetworkUtils.startNetService(this)

    private fun initCrashHandler() = CrashHandler.init(this)

    private fun initLog() = LogUtils.init(this)


    /**
     * 增加Activity
     * @param act act
     */
    fun addActivity(act: Activity) {
        if (allActivities == null) {
            allActivities = HashSet()
        } else {
            allActivities?.add(act)
        }
    }

    /**
     * 移除Activity
     * @param act act
     */
    fun removeActivity(act: Activity) {
        allActivities?.remove(act)
    }

    @Synchronized
    fun exitApp() {
        allActivities?.let {
            it.forEach { activity ->
                activity.finish()
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(0)
    }

}