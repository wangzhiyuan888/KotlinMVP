package com.wzy.arms.rxcache

import com.wzy.arms.rxcache.cache.CacheProviders
import com.wzy.arms.network.helper.RetrofitHelper
import com.wzy.arms.network.model.UserInfo
import io.reactivex.Flowable
import io.rx_cache2.internal.RxCache
import io.victoralbertos.jolyglot.GsonSpeaker
import java.io.File
import javax.inject.Inject
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictProvider

class Repository
@Inject
constructor(private val mRetrofitHelper: RetrofitHelper){

    private var cacheProviders: CacheProviders? = null
    fun getInstance(cacheDir: File): Repository {
        cacheProviders = RxCache.Builder()
                .persistence(cacheDir, GsonSpeaker())
                .using(CacheProviders::class.java)
        return this

    }

    //参数update：是否加载最新数据
    fun requestUsers(perPage: Int?, pageNumber: Int?, update: Boolean): Flowable<MutableList<UserInfo>> {
        return cacheProviders!!.requestUsers(mRetrofitHelper.requestUsers(perPage, pageNumber), DynamicKey(perPage), EvictProvider(update))
    }

}

