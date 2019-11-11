package com.wzy.arm.di.component

import android.app.Activity
import com.wzy.arm.ui.activity.MainActivity
import com.wzy.arm.di.module.ActivityModule
import com.wzy.arms.di.component.ApiComponent
import com.wzy.arms.di.scope.ActivityScope


import dagger.Component

/**
 * @author: wzy
 * date: 2019/11/11
 * desc: Activity模型
 */
@ActivityScope
@Component(dependencies = [ApiComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    val activity: Activity

    fun inject(activity: MainActivity)
}
