package com.wzy.arms.widget.util

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.support.annotation.RequiresApi

object ContextCompatApi23 {

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun getColorStateList(context: Context, id: Int): ColorStateList {
        return context.getColorStateList(id)
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun getColor(context: Context, id: Int): Int {
        return context.getColor(id)
    }
}
