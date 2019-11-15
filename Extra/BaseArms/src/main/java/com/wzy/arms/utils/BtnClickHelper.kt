package com.wzy.arms.utils

object BtnClickHelper{

    private var mLastClickTime: Long = 0
    fun isFastDoubleClick(): Boolean {
        val time = System.currentTimeMillis()
        val timeD = time - mLastClickTime
        if (0 < timeD && timeD < 800) {
            return true
        }

        mLastClickTime = time

        return false
    }
}