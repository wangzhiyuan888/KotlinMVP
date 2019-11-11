package com.wzy.arm.di.module

import android.app.Activity

import com.wzy.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

/**
 * @author: wzy
 * date: 2019/11/11
 * desc: Activity模型
 */
@Module
class ActivityModule(val activity: Activity) {

    @Provides
    @ActivityScope
    fun provideActivity(): Activity {
        return activity
    }
}
