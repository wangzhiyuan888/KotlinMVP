package com.wzy.arms.helper

import com.wzy.arms.base.BaseActivity
import java.util.*

object ActivityCollector{
    var activities = LinkedList<BaseActivity>()
    /**
     * 添加Activity
     */
    fun addActivity(activity: BaseActivity) {
        activities.add(activity)
    }

    /**
     * 移除Activity
     */
    fun removeActivity(activity: BaseActivity) {
        activities.remove(activity)
    }

    fun sendMessage(action: String, value: Any){
        for (activity in activities) {
            if (action in activity.receiverActions()) {
                activity.onReceive(action, value)

            }

        }
    }
}