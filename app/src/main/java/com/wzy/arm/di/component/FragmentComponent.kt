package com.wzy.arm.di.component

import android.app.Activity
import com.wzy.arm.ui.fragment.HomeFragment
import com.wzy.arms.di.component.ApiComponent
import com.wzy.arms.di.module.FragmentModule
import com.wzy.arms.di.scope.FragmentScope

import dagger.Component

/**
 * @author: wzy
 * date: 2019/11/11
 * desc: FragmentComponent
 */
@FragmentScope
@Component(dependencies = [ApiComponent::class], modules = [FragmentModule::class])
interface FragmentComponent {
    val activity: Activity

    fun inject(homeFragment: HomeFragment)
}
