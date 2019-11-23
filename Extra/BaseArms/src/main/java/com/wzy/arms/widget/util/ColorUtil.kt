package com.wzy.arms.widget.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build

object ColorUtil {

    @SuppressLint("NewApi")
    fun getColor(context: Context, id: Int): Int {
        val version = Build.VERSION.SDK_INT
        return if (version >= 23) {
            ContextCompatApi23.getColor(context, id)
        } else {
            context.resources.getColor(id)
        }
    }
}
