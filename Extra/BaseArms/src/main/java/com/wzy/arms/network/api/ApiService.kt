package com.wzy.arms.network.api

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query
import com.wzy.arms.network.model.User
import retrofit2.http.Headers


/**
 * @author: wzy
 * date: 2019/11/11
 * desc: Api接口类
 */
interface ApiService {

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/users")
    fun getUsers(@Query("since") lastIdQueried: Int, @Query("per_page") perPage: Int): Flowable<MutableList<User>>
}
