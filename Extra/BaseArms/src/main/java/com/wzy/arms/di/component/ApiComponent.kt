package com.wzy.arms.di.component

import com.wzy.arms.di.module.ApiModule
import com.wzy.arms.network.helper.RetrofitHelper
import dagger.Component
import javax.inject.Singleton

/**
 * @author: wzy
 * date: 2019/11/11
 * desc: ApiComponent
 */
@Singleton
@Component(modules = [ApiModule::class])
interface ApiComponent {
    val retrofitHelper: RetrofitHelper
}
