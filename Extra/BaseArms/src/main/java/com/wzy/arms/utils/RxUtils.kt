package com.wzy.arms.utils

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author: wzy
 * date: 2019/11/11
 * desc:
 */

/**
 * 统一线程处理
 *
 * @param <T>
 * @return
</T> */
fun <T> rxSchedulerFlowableHelper(): FlowableTransformer<T, T> =
        FlowableTransformer { observable ->
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        } // compose简化线程 统一处理线程

/**
 * 统一线程处理
 *
 * @param <T>
 * @return
</T> */
fun <T> rxSchedulerObservableHelper(): ObservableTransformer<T, T> =
        ObservableTransformer { observable ->
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        } // compose简化线程 统一处理线程

/**
 * 生成Flowable
 *
 * @param <T>
 * @return
</T> */
fun <T> createData(t: T): Flowable<T> {
    return Flowable.create({ emitter ->
        try {
            emitter.onNext(t)
            emitter.onComplete()
        } catch (e: Exception) {
            emitter.onError(e)
        }
    }, BackpressureStrategy.BUFFER)
}

/**
 * 生成Flowable
 *
 * @param <T>
 * @return
</T> */
fun <T> createData(t: List<T>): Flowable<List<T>> {
    return Flowable.create({ emitter ->
        try {
            emitter.onNext(t)
            emitter.onComplete()
        } catch (e: Exception) {
            emitter.onError(e)
        }
    }, BackpressureStrategy.BUFFER)
}

