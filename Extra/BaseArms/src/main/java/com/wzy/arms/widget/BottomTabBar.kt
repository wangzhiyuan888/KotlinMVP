package com.wzy.arms.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.wzy.arms.R
import com.wzy.arms.widget.custom.CustomFragmentTabHost

import java.util.ArrayList

/**
 * Created by wzy
 * 2019/11/11
 */
class BottomTabBar : LinearLayout {

    private var mContext: Context? = null
    private var mLayout: LinearLayout? = null
    //BottomTabBar整体布局，这里使用FragmentTabHost
    private var mTabHost: CustomFragmentTabHost? = null
    //分割线
    private var mDivider: View? = null

    //图片的宽高
    private var imgWidth = 0f
    private var imgHeight = 0f
    //文字尺寸
    private var fontSize = 14f
    //文字图片的间隔
    private var fontImgPadding: Float = 0.toFloat()
    //上边距和下边距
    private var paddingTop: Float = 0.toFloat()
    private var paddingBottom: Float = 0.toFloat()
    //选中未选中的颜色
    private var selectColor = Color.parseColor("#F1453B")
    private var unSelectColor = Color.parseColor("#626262")
    //分割线高度
    private var dividerHeight: Float = 0.toFloat()
    //是否显示分割线
    private var isShowDivider = false
    //分割线背景
    private var dividerBackgroundColor = Color.parseColor("#CCCCCC")
    //BottomTabBar的整体背景
    private var tabBarBackgroundColor = Color.parseColor("#FFFFFF")
    //tabId集合
    public val tabIdList = ArrayList<String>()

    private var listener: OnTabChangeListener? = null

    fun getmTabHost(): CustomFragmentTabHost? {
        return mTabHost
    }

    fun getTabIdList(): List<String> {
        return tabIdList
    }

    constructor(context: Context) : super(context) {
        this.mContext = context
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.mContext = context

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.BottomTabBar)
        if (attributes != null) {
            //图片宽度
            imgWidth = attributes.getDimension(R.styleable.BottomTabBar_tab_img_width, 0f)
            //图片高度
            imgHeight = attributes.getDimension(R.styleable.BottomTabBar_tab_img_height, 0f)
            //文字尺寸
            fontSize = attributes.getDimension(R.styleable.BottomTabBar_tab_font_size, 14f)
            //上边距
            paddingTop = attributes.getDimension(R.styleable.BottomTabBar_tab_padding_top, dp2px(2f).toFloat())
            //图片文字间隔
            fontImgPadding = attributes.getDimension(R.styleable.BottomTabBar_tab_img_font_padding, dp2px(3f).toFloat())
            //下边距
            paddingBottom = attributes.getDimension(R.styleable.BottomTabBar_tab_padding_bottom, dp2px(5f).toFloat())
            //分割线高度
            dividerHeight = attributes.getDimension(R.styleable.BottomTabBar_tab_divider_height, dp2px(1f).toFloat())
            //是否显示分割线
            isShowDivider = attributes.getBoolean(R.styleable.BottomTabBar_tab_isshow_divider, false)
            //选中的颜色
            selectColor = attributes.getColor(R.styleable.BottomTabBar_tab_selected_color, Color.parseColor("#626262"))
            //未选中的颜色
            unSelectColor = attributes.getColor(R.styleable.BottomTabBar_tab_unselected_color, Color.parseColor("#F1453B"))
            //BottomTabBar的整体背景
            tabBarBackgroundColor = attributes.getColor(R.styleable.BottomTabBar_tab_bar_background, Color.parseColor("#FFFFFF"))
            //分割线背景
            dividerBackgroundColor = attributes.getColor(R.styleable.BottomTabBar_tab_divider_background, Color.parseColor("#CCCCCC"))

            attributes.recycle()
        }
    }

    /**
     * 初始化，主要是需要传入一个FragmentManager
     *
     *
     * 此方法必须在所有的方法之前调用
     *
     * @param manager
     * @return
     */
    fun init(manager: FragmentManager): BottomTabBar {
        mLayout = LayoutInflater.from(context).inflate(R.layout.bottom_tab_bar, null) as LinearLayout
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        mLayout!!.layoutParams = layoutParams
        addView(mLayout)

        mTabHost = mLayout!!.findViewById<View>(android.R.id.tabhost) as CustomFragmentTabHost
        mTabHost!!.setup(context, manager, R.id.realtabcontent)
        mTabHost!!.setBackgroundColor(tabBarBackgroundColor)
        mTabHost!!.tabWidget.dividerDrawable = null
        tabIdList.clear()
        mTabHost!!.setOnTabChangedListener { tabId ->
            if (listener != null) {
                listener!!.onTabChange(tabIdList.indexOf(tabId), tabId)
            }
        }

        mDivider = mLayout!!.findViewById(R.id.split)
        if (isShowDivider) {
            val dividerParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dividerHeight.toInt())
            mDivider!!.layoutParams = dividerParams
            mDivider!!.setBackgroundColor(dividerBackgroundColor)
            mDivider!!.visibility = View.VISIBLE
        } else {
            mDivider!!.visibility = View.GONE
        }

        return this
    }

    /**
     * Tab标签切换监听
     */
    interface OnTabChangeListener {
        fun onTabChange(position: Int, name: String)
    }

    fun setOnTabChangeListener(listener: OnTabChangeListener?): BottomTabBar {
        if (listener != null) {
            this.listener = listener
        }
        return this
    }

    /**
     * 添加TabItem
     *
     * @param name          文字
     * @param imgId         图片id
     * @param fragmentClass fragment
     * @return
     */
    fun addTabItem(name: String, imgId: Int, fragmentClass: Class<*>): BottomTabBar {
        return addTabItem(name, ContextCompat.getDrawable(context!!, imgId), fragmentClass)
    }

    fun addTabItem(name: String, imgIdSelect: Int, imgIdUnSelect: Int, fragmentClass: Class<*>): BottomTabBar {
        return addTabItem(name, ContextCompat.getDrawable(context!!, imgIdSelect), ContextCompat.getDrawable(context!!, imgIdUnSelect), fragmentClass)
    }

    /**
     * 添加TabItem
     *
     * @param name          文字
     * @param drawable      图片
     * @param fragmentClass fragment
     * @return
     */
    fun addTabItem(name: String, drawable: Drawable?, fragmentClass: Class<*>): BottomTabBar {
        tabIdList.add(if (TextUtils.isEmpty(name)) fragmentClass.name else name)
        mTabHost!!.addTab(mTabHost!!.newTabSpec(if (TextUtils.isEmpty(name)) fragmentClass.name else name)
                .setIndicator(getTabItemView(if (TextUtils.isEmpty(name)) fragmentClass.name else name, drawable)), fragmentClass, null)
        return this
    }

    fun addTabItem(name: String, drawableSelect: Drawable?, drawableUnSelect: Drawable?, fragmentClass: Class<*>): BottomTabBar {
        tabIdList.add(if (TextUtils.isEmpty(name)) fragmentClass.name else name)
        mTabHost!!.addTab(mTabHost!!.newTabSpec(if (TextUtils.isEmpty(name)) fragmentClass.name else name)
                .setIndicator(getTabItemView(if (TextUtils.isEmpty(name)) fragmentClass.name else name, drawableSelect, drawableUnSelect)), fragmentClass, null)
        return this
    }

    private fun getTabItemView(name: String, drawable: Drawable?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.tab_item, null)

        val iv = view.findViewById<View>(R.id.tab_item_iv) as ImageView
        val ivParams = LinearLayout.LayoutParams(if (imgWidth == 0f) LinearLayout.LayoutParams.WRAP_CONTENT else imgWidth.toInt(), if (imgHeight == 0f) LinearLayout.LayoutParams.WRAP_CONTENT else imgHeight.toInt())
        ivParams.topMargin = paddingTop.toInt()
        ivParams.gravity = Gravity.CENTER_HORIZONTAL
        iv.layoutParams = ivParams
        /*iv.setImageDrawable(TintUtil.setStateListTintColor(drawable, unSelectColor, selectColor));*/

        val tv = view.findViewById<View>(R.id.tab_item_tv) as TextView
        tv.text = name
        tv.setPadding(0, fontImgPadding.toInt(), 0, paddingBottom.toInt())
        tv.textSize = fontSize
        val states = arrayOfNulls<IntArray>(3)
        states[0] = intArrayOf(android.R.attr.state_pressed)
        states[1] = intArrayOf(android.R.attr.state_selected)
        states[2] = intArrayOf()
        val colors = intArrayOf(selectColor, selectColor, unSelectColor)//colorSelect跟states[0]对应，color跟states[2]对应，以此类推
        val csl = ColorStateList(states, colors)
        tv.setTextColor(csl)

        return view
    }

    private fun getTabItemView(name: String, drawableSelect: Drawable?, drawableUnSelect: Drawable?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.tab_item, null)

        val iv = view.findViewById<View>(R.id.tab_item_iv) as ImageView
        val ivParams = LinearLayout.LayoutParams(if (imgWidth == 0f) LinearLayout.LayoutParams.WRAP_CONTENT else imgWidth.toInt(), if (imgHeight == 0f) LinearLayout.LayoutParams.WRAP_CONTENT else imgHeight.toInt())
        ivParams.topMargin = paddingTop.toInt()
        ivParams.gravity = Gravity.CENTER_HORIZONTAL
        iv.layoutParams = ivParams

        val drawable = StateListDrawable()
        drawable.addState(intArrayOf(android.R.attr.state_pressed),
                drawableSelect)
        drawable.addState(intArrayOf(android.R.attr.state_selected),
                drawableSelect)
        drawable.addState(intArrayOf(),
                drawableUnSelect)
        iv.setImageDrawable(drawable)

        val tv = view.findViewById<View>(R.id.tab_item_tv) as TextView
        tv.text = name
        tv.setPadding(0, fontImgPadding.toInt(), 0, paddingBottom.toInt())
        tv.textSize = fontSize
        val states = arrayOfNulls<IntArray>(3)
        states[0] = intArrayOf(android.R.attr.state_pressed)
        states[1] = intArrayOf(android.R.attr.state_selected)
        states[2] = intArrayOf()
        val colors = intArrayOf(selectColor, selectColor, unSelectColor)//colorSelect跟states[0]对应，color跟states[2]对应，以此类推
        val csl = ColorStateList(states, colors)
        tv.setTextColor(csl)

        return view
    }

    //=========================参数设置START========================================

    /**
     * 设置图片的尺寸
     *
     *
     * 此方法必须在addTabItem()之前调用
     *
     * @param width  宽度 px
     * @param height 高度 px
     * @return
     */
    fun setImgSize(width: Float, height: Float): BottomTabBar {
        this.imgWidth = dp2px(width).toFloat()
        this.imgHeight = dp2px(width).toFloat()
        return this
    }

    /**
     * 设置文字的尺寸
     *
     *
     * 此方法必须在addTabItem()之前调用
     *
     * @param textSize 文字的尺寸 sp
     * @return
     */
    fun setFontSize(textSize: Float): BottomTabBar {
        this.fontSize = textSize
        return this
    }

    /**
     * 设置Tab的padding值
     *
     *
     * 此方法必须在addTabItem()之前调用
     *
     * @param top    上边距 px
     * @param middle 文字图片的距离 px
     * @param bottom 下边距 px
     * @return
     */
    fun setTabPadding(top: Float, middle: Float, bottom: Float): BottomTabBar {
        this.paddingTop = top
        this.fontImgPadding = middle
        this.paddingBottom = bottom
        return this
    }

    /**
     * 设置选中未选中的颜色
     *
     *
     * 此方法必须在addTabItem()之前调用
     *
     * @param selectColor   选中的颜色
     * @param unSelectColor 未选中的颜色
     * @return
     */
    fun setChangeColor(@ColorInt selectColor: Int, @ColorInt unSelectColor: Int): BottomTabBar {
        this.selectColor = selectColor
        this.unSelectColor = unSelectColor
        return this
    }

    /**
     * 设置BottomTabBar的整体背景
     *
     * @param color 背景颜色
     * @return
     */
    fun setTabBarBackgroundColor(@ColorInt color: Int): BottomTabBar {
        this.tabBarBackgroundColor = color
        mTabHost!!.setBackgroundColor(color)
        return this
    }

    /**
     * 设置BottomTabBar的整体背景
     *
     * @param resid 背景图片id
     * @return
     */
    fun setTabBarBackgroundResource(@DrawableRes resid: Int): BottomTabBar {
        mTabHost!!.setBackgroundResource(resid)
        return this
    }
    //    /**
    //     * 设置BottomTabBar的整体背景
    //     * api 16开始才支持
    //     *
    //     * @param drawable 背景图片
    //     * @return
    //     */
    //    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    //    public BottomTabBar setTabBarBackgroundResource(Drawable drawable){
    //        mTabHost.setBackground(drawable);
    //        return this;
    //    }

    /**
     * 是否显示分割线
     *
     * @param isShowDivider
     * @return
     */
    fun isShowDivider(isShowDivider: Boolean): BottomTabBar {
        this.isShowDivider = isShowDivider
        if (isShowDivider) {
            val dividerParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dividerHeight.toInt())
            mDivider!!.layoutParams = dividerParams
            mDivider!!.setBackgroundColor(dividerBackgroundColor)
            mDivider!!.visibility = View.VISIBLE
        } else {
            mDivider!!.visibility = View.GONE
        }
        return this
    }

    /**
     * 设置分割线的高度
     *
     * @param height
     * @return
     */
    fun setDividerHeight(height: Float): BottomTabBar {
        this.dividerHeight = height
        if (isShowDivider) {
            val dividerParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dividerHeight.toInt())
            mDivider!!.layoutParams = dividerParams
        }
        return this
    }

    /**
     * 设置分割线的颜色
     *
     * @param color
     * @return
     */
    fun setDividerColor(@ColorInt color: Int): BottomTabBar {
        this.dividerBackgroundColor = color
        if (isShowDivider) {
            mDivider!!.setBackgroundColor(dividerBackgroundColor)
        }
        return this
    }

    //=========================参数设置END===========================================

    //=========================工具类START========================================

    /**
     * dp转px
     *
     * @param dpValue
     * @return
     */
    private fun dp2px(dpValue: Float): Int {
        val scale = context!!.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
    //=========================工具类END===========================================
}

