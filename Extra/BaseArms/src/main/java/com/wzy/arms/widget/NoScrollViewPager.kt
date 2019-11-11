package com.wzy.arms.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet

/**
 * @author: wzy
 * date: 2019/11/11
 * desc: 重写Viewpager解决点击tab去除滑动动画效果的问题
 */
class NoScrollViewPager : ViewPager {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun scrollTo(x: Int, y: Int) {
        super.scrollTo(x, y)
    }

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        super.setCurrentItem(item, smoothScroll)
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, false)
    }
}