package com.wzy.arms.widget

import android.content.res.Resources
import android.graphics.*

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation

import java.security.MessageDigest
import kotlin.properties.Delegates

/**
 * @author: wzy
 * date: 2019/11/11
 * desc: 矩形图片
 */
class GlideRoundTransform(dp: Int) : BitmapTransformation() {

    var radius by Delegates.notNull<Float>()

    constructor() : this(4) {}

    init {
        /*super(context);*/
        this.radius = Resources.getSystem().displayMetrics.density * dp
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap? {
        return roundCrop(pool, toTransform)
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {

    }

    companion object {

        private val radius = 0f

        private fun roundCrop(pool: BitmapPool, source: Bitmap?): Bitmap? {
            if (source == null) return null

            var result: Bitmap? = pool.get(source.width, source.height, Bitmap.Config.ARGB_8888)
            if (result == null) {
                result = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
            }

            val canvas = Canvas(result!!)
            val paint = Paint()
            paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            paint.isAntiAlias = true
            val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
            canvas.drawRoundRect(rectF, radius, radius, paint)
            return result
        }
    }
}
