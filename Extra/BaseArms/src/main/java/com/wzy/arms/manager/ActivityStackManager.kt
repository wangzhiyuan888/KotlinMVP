package com.wzy.arms.manager

import android.app.Activity
import com.wzy.arms.base.BaseActivity

/**
 * 管理Activity的基类
 */
class ActivityStackManager{

    private var allActivities: HashSet<Activity>? = null

    companion object {
        var instance: ActivityStackManager = ActivityStackManager()
    }

    /**
     * 增加Activity
     * @param act Activity
     */
    fun addActivity(act: Activity) {
        if (allActivities == null) {
            allActivities = HashSet()
        } else {
            allActivities?.add(act)
        }
    }

    /**
     * 移除Activity
     * @param act Activity
     */
    fun removeActivity(act: Activity) {
        allActivities?.remove(act)
    }

    /**
     * 退出程序
     */
    @Synchronized
    fun exitApp() {
        allActivities?.let {
            it.forEach { activity ->
                activity.finish()
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(0)
    }

    /***
     * 获取栈顶Activity（堆栈中最后一个压入的）
     * @return Activity
     */
    fun topActivity(): Activity? {
        if (allActivities!!.isEmpty()) {
            return null
        }
        return allActivities!!.last()

    }

    /**
     * 结束栈顶Activity（堆栈中最后一个压入的）
     */
    fun killTopActivity(){
        try {
            killActivity(topActivity())
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /***
     * 结束指定的Activity
     * @param activity
     */
    @Synchronized
    fun killActivity(posActivity: Activity?) {
        try {
            val iter = allActivities!!.iterator()
            while (iter.hasNext()) {
                val activity:Activity = iter.next()
                if (null == activity) {
                    iter.remove()
                    continue
                }
                if (null != activity && activity.javaClass.name == posActivity!!.javaClass.name) {
                    iter.remove()
                    activity!!.finish()
                    break
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 结束除目标Activity以外的所有Activity
     */
    @Synchronized
    fun killAllExitPosActivity(activityClass: Class<*>) {
        try {
            val iter = allActivities!!.iterator()
            while (iter.hasNext()) {
                val activity:Activity = iter.next()
                if (null != activity && activity.javaClass.name != activityClass.name) {
                    activity.finish()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 发送数据
     */
    fun sendMessage(action: String, value: Any){
        try {
            val iter = allActivities!!.iterator()
            while (iter.hasNext()) {
                val activity: BaseActivity = iter.next() as BaseActivity
                if (action in activity.receiverActions()) {
                    activity.onReceive(action, value)

                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

}