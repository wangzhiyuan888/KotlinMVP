package com.wzy.arms.widget.util

import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable

/**
 * Created by wzy
 * 2019/11/11
 * 切记：ImageView一定要设为可点击的
 */
object TintUtil {

    fun getStateListDrawable(drawable: Drawable, states: Array<IntArray>): StateListDrawable {
        val stateListDrawable = StateListDrawable()
        for (state in states) {
            stateListDrawable.addState(state, drawable)
        }
        return stateListDrawable
    }
}
