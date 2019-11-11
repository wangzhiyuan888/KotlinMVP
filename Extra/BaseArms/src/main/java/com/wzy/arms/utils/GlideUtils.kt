package com.wzy.arms.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.wzy.arms.R
import com.wzy.arms.base.BaseApp
import com.wzy.arms.widget.GlideCircleTransform
import com.wzy.arms.widget.GlideRoundTransform

object GlideUtils{

    /**
     * 设置默认边角的图片，默认边角为4的的矩形框
     */
    fun glideRoundTransform(path:String?, imageView: ImageView?, placeImg:Int?) {
        glideRoundTransform(path, imageView, placeImg, 4)
    }

    /**
     * 设置边角的矩形框
     */
    fun glideRoundTransform(path:String?, imageView: ImageView?, placeImg:Int?, radius:Int?) {
        Glide.with(BaseApp.instance).load(path!!).placeholder(if (placeImg == 0 || null == placeImg) R.mipmap.ic_launcher else placeImg)
                .centerCrop().transform(GlideRoundTransform(radius!!)).into(imageView!!)
    }

    /**
     * 设置边角的圆形框
     */
    fun glideCircleTransform(path:String?, imageView: ImageView?, placeImg:Int?) {
        Glide.with(BaseApp.instance).load(path!!).placeholder(if (placeImg == 0 || null == placeImg) R.mipmap.ic_launcher else placeImg)
                .centerCrop().transform(GlideCircleTransform()).into(imageView!!)
    }

}
/*

class GlideUtils{

    companion object {

        */
/**
         * 设置默认边角的图片，默认边角为4的的矩形框
         *//*

        fun glideRoundTransform(path:String?, imageView: ImageView?, placeImg:Int?) {
            glideRoundTransform(path, imageView, placeImg, 4)
        }

        */
/**
         * 设置边角的矩形框
         *//*

        fun glideRoundTransform(path:String?, imageView: ImageView?, placeImg:Int?, radius:Int?) {
            Glide.with(BaseApplication.instance).load(path!!).placeholder(if (placeImg == 0 || null == placeImg) R.mipmap.ic_launcher else placeImg)
                    .centerCrop().transform(GlideRoundTransform(radius!!)).into(imageView!!)
        }

        */
/**
         * 设置边角的圆形框
         *//*

        fun glideCircleTransform(path:String?, imageView: ImageView?, placeImg:Int?) {
            Glide.with(BaseApplication.instance).load(path!!).placeholder(if (placeImg == 0 || null == placeImg) R.mipmap.ic_launcher else placeImg)
                    .centerCrop().transform(GlideCircleTransform()).into(imageView!!)
        }
    }
}*/
