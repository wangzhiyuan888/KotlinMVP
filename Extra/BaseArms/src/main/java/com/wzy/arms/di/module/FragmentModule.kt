package com.wzy.arms.di.module

import android.app.Activity
import android.support.v4.app.Fragment
import com.wzy.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

/**
 * @author: wzy
 * date: 2019/11/11
 * desc: Fragment模型
 */
@Module
class FragmentModule(val fragment: Fragment) {

    @Provides
    @FragmentScope
    fun provideActivity(): Activity = fragment.activity!!



}
