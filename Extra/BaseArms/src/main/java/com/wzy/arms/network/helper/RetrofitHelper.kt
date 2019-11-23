package com.wzy.arms.network.helper

import com.wzy.arms.network.api.ApiService
import com.wzy.arms.network.model.UserInfo
import io.reactivex.Flowable

/**
 * @author: wzy
 * date: 2019/11/11
 * desc: RetrofitHelper
 */
class RetrofitHelper(private val mApiService: ApiService) {

    /*******************************ApiService *********************************/

    // 获取精选内容
    fun requestUsers(perPage: Int?, pageNumber: Int?): Flowable<MutableList<UserInfo>> = mApiService.getUsers(perPage!!, pageNumber!!)
}

