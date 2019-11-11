package com.wzy.arms.integration

import android.content.Context

interface IRepositoryManager{

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param service
     * @param <T>
     * @return
    </T> */
    abstract fun <T> obtainRetrofitService(service: Class<T>): T

    /**
     * 根据传入的 Class 获取对应的 RxCache service
     *
     * @param cache
     * @param <T>
     * @return
    </T> */
    abstract fun <T> obtainCacheService(cache: Class<T>): T

    /**
     * 清理所有缓存
     */
    abstract fun clearAllCache()

    abstract fun getContext(): Context

}