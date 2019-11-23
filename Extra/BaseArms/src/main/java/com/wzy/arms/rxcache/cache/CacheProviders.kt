package com.wzy.arms.rxcache.cache

import com.wzy.arms.network.model.UserInfo
import io.reactivex.Flowable
import io.rx_cache2.*
import java.util.concurrent.TimeUnit

interface CacheProviders {

    /**
     * 如果不设置DynamicKey，EvictProvider 则一直使用第一个缓存
     */
    @LifeCache(duration = 3, timeUnit = TimeUnit.MINUTES)
    abstract fun requestUsers(users: Flowable<MutableList<UserInfo>>, idLastUserQueried: DynamicKey, evictProvider: EvictProvider): Flowable<MutableList<UserInfo>>
}