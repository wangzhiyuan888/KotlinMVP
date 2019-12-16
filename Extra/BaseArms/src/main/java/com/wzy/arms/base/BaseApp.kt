package com.wzy.arms.base

import android.app.Application
import android.text.TextUtils
import android.util.Log
import com.wzy.arms.BuildConfig
import com.wzy.arms.di.component.ApiComponent
import com.wzy.arms.di.component.DaggerApiComponent
import com.wzy.arms.di.module.ApiModule
import com.wzy.arms.rxcache.Repository
import com.wzy.arms.utils.AppUtils
import com.wzy.arms.utils.CrashHandler
import com.wzy.arms.utils.NetworkUtils
import timber.log.Timber
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

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
        cacheDiaPath = instance.getCacheDir().toString();
        initTimber()
        initNetwork()
        initCrashHandler()
        repository = Repository(mApiComponent.retrofitHelper).getInstance(getFilesDir())
    }

    fun getRepository(): Repository {
        return repository!!
    }

    companion object {
        lateinit var instance: BaseApp
        lateinit var cacheDiaPath: String
    }

    fun initTimber(){
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(FileLoggingTree())
        }
    }

    private fun initNetwork() = NetworkUtils.startNetService(this)

    private fun initCrashHandler() = CrashHandler.init(this)

    class FileLoggingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {

            if (TextUtils.isEmpty(cacheDiaPath)) {
                return
            }
            val file = File(cacheDiaPath + "/ego_log.txt")
            Log.v("dyp", "file.path:" + file.getAbsolutePath() + ",message:" + message)
            var writer: FileWriter? = null
            var bufferedWriter: BufferedWriter? = null
            try {
                writer = FileWriter(file)
                bufferedWriter = BufferedWriter(writer)
                bufferedWriter.write(message)
                bufferedWriter.flush()

            } catch (e: IOException) {
                Log.v("dyp", "存储文件失败")
                e.printStackTrace()
            } finally {
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
        }
    }

}