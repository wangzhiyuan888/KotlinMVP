package com.wzy.arms.utils

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.support.annotation.ArrayRes
import android.support.annotation.StringRes
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * @author: wzy
 * date: 2019/11/11
 * desc: App工具类
 */
object AppUtils {

    lateinit var appContext: Context
    private var mUiThread: Thread? = null

    private val sHandler = Handler(Looper.getMainLooper())

    val assets: AssetManager
        get() = appContext.assets

    val resource: Resources
        get() = appContext.resources

    val isUIThread: Boolean
        get() = Thread.currentThread() === mUiThread

    /**
     * 获取屏幕高度
     */
    val screenWidth: Int
        get() {
            val wm = appContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val outMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(outMetrics)
            return outMetrics.widthPixels
        }

    /**
     * 获取屏幕宽度
     */
    val screenHeight: Int
        get() {
            val wm = appContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val outMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(outMetrics)
            return outMetrics.heightPixels
        }

    /**
     * 获取屏幕分辨率比例
     */
    val screenScale: Float?
        get() = appContext.resources.displayMetrics.density

    /**
     * 获取字体缩放比例
     */
    val screenScaleDensity: Float?
        get() = appContext.resources.displayMetrics.scaledDensity

    val displayMetrics: DisplayMetrics
        get() = appContext.resources.displayMetrics

    fun init(context: Context) { //在Application中初始化
        appContext = context
        mUiThread = Thread.currentThread()
    }

    fun getDimension(id: Int): Float {
        return resource.getDimension(id)
    }

    fun getDrawable(resId: Int): Drawable {
        return appContext.resources.getDrawable(resId)
    }

    fun getColor(resId: Int): Int {
        return appContext.resources.getColor(resId)
    }

    fun getString(@StringRes resId: Int): String {
        return appContext.resources.getString(resId)
    }

    fun getStringArray(@ArrayRes resId: Int): Array<String> {
        return appContext.resources.getStringArray(resId)
    }

    fun runOnUI(r: Runnable) {
        sHandler.post(r)
    }

    fun runOnUIDelayed(r: Runnable, delayMills: Long) {
        sHandler.postDelayed(r, delayMills)
    }

    fun removeRunnable(r: Runnable?) {
        if (r == null) {
            sHandler.removeCallbacksAndMessages(null)
        } else {
            sHandler.removeCallbacks(r)
        }
    }
}
