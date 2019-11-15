package com.wzy.arm.base

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.wzy.arm.R
import com.wzy.arm.di.component.ActivityComponent
import com.wzy.arm.di.component.DaggerActivityComponent
import com.wzy.arm.di.module.ActivityModule
import com.wzy.arms.enum.BaseTitleStyle
import com.wzy.arms.base.BaseActivity
import com.wzy.arms.base.BaseApp
import com.wzy.arms.di.contract.BaseContract
import com.wzy.arms.utils.BtnClickHelper
import javax.inject.Inject

/**
 * @author: wzy
 * date: 2019/11/11
 * desc: Activity模型
 */
abstract class BaseInjectActivity<T : BaseContract.BasePresenter<*>> : BaseActivity(), BaseContract.BaseView, View.OnClickListener {

    protected var leftTv:TextView? = null
    protected var leftIBtn:ImageButton? = null
    protected var centerTv:TextView? = null
    protected var centerIBtn:ImageButton? = null
    protected var rightTv:TextView? = null
    protected var rightIBtn:ImageButton? = null

    @Inject
    lateinit var mPresenter: T

    protected val activityModule: ActivityModule get() = ActivityModule(this)

    protected val activityComponent: ActivityComponent
        get() = DaggerActivityComponent.builder()
                .apiComponent(BaseApp.instance.mApiComponent)
                .activityModule(activityModule)
                .build()

    override fun onDestroy() {
        mPresenter.detachView()
        BaseApp.instance.removeActivity(this)
        super.onDestroy()
    }

    override fun showError(tag:String, msg: String) {
    }

    override fun complete() {
    }

    /**
     * 顶部 布局文件ID
     **/
    open fun topTitleStyle(): BaseTitleStyle{
        return BaseTitleStyle.IBTN_TV_IBTN_STYLE
    }

    override fun onTopViewCreated(rootView: View?) {
        if(null == rootView){
            return
        }
        var baseTitleStyle:BaseTitleStyle = topTitleStyle()
        when(baseTitleStyle){
            BaseTitleStyle.TV_TV_TV_STYLE ->{
                leftIBtn = rootView.findViewById(R.id.header_layout_left_operate_v)
                centerTv = rootView.findViewById(R.id.header_layout_center_operate_v)
                rightTv = rootView.findViewById(R.id.header_layout_right_operate_v)

            }
            BaseTitleStyle.TV_TV_IBTN_STYLE ->{
                leftIBtn = rootView.findViewById(R.id.header_layout_left_operate_v)
                centerTv = rootView.findViewById(R.id.header_layout_center_operate_v)
                rightIBtn = rootView.findViewById(R.id.header_layout_right_operate_v)
            }
            BaseTitleStyle.TV_IBTN_TV_STYLE ->{
                leftIBtn = rootView.findViewById(R.id.header_layout_left_operate_v)
                centerIBtn = rootView.findViewById(R.id.header_layout_center_operate_v)
                rightTv = rootView.findViewById(R.id.header_layout_right_operate_v)
            }
            BaseTitleStyle.IBTN_TV_TV_STYLE ->{
                leftIBtn = rootView.findViewById(R.id.header_layout_left_operate_v)
                centerTv = rootView.findViewById(R.id.header_layout_center_operate_v)
                rightTv = rootView.findViewById(R.id.header_layout_right_operate_v)
            }
            BaseTitleStyle.TV_IBTN_IBTN_STYLE ->{
                leftIBtn = rootView.findViewById(R.id.header_layout_left_operate_v)
                centerIBtn = rootView.findViewById(R.id.header_layout_center_operate_v)
                rightIBtn = rootView.findViewById(R.id.header_layout_right_operate_v)
            }
            BaseTitleStyle.IBTN_TV_IBTN_STYLE ->{
                leftIBtn = rootView.findViewById(R.id.header_layout_left_operate_v)
                centerTv = rootView.findViewById(R.id.header_layout_center_operate_v)
                rightIBtn = rootView.findViewById(R.id.header_layout_right_operate_v)
            }
            BaseTitleStyle.IBTN_IBTN_TV_STYLE ->{
                leftIBtn = rootView.findViewById(R.id.header_layout_left_operate_v)
                centerIBtn = rootView.findViewById(R.id.header_layout_center_operate_v)
                rightTv = rootView.findViewById(R.id.header_layout_right_operate_v)
            }
            BaseTitleStyle.IBTN_IBTN_IBTN_STYLE ->{
                leftIBtn = rootView.findViewById(R.id.header_layout_left_operate_v)
                centerIBtn = rootView.findViewById(R.id.header_layout_center_operate_v)
                rightIBtn = rootView.findViewById(R.id.header_layout_right_operate_v)
            }
        }

    }

    override fun topLayoutId(): Int {
        var baseTitleStyle:BaseTitleStyle = topTitleStyle()
        when(baseTitleStyle){
            BaseTitleStyle.TV_TV_TV_STYLE ->{
                return R.layout.layout_tv_tv_tv_style
            }
            BaseTitleStyle.TV_TV_IBTN_STYLE ->{
                return R.layout.layout_tv_tv_ibtn_style
            }
            BaseTitleStyle.TV_IBTN_TV_STYLE ->{
                return R.layout.layout_tv_ibtn_tv_style
            }
            BaseTitleStyle.IBTN_TV_TV_STYLE ->{
                return R.layout.layout_ibtn_tv_tv_style
            }
            BaseTitleStyle.TV_IBTN_IBTN_STYLE ->{
                return R.layout.layout_tv_ibtn_ibtn_style
            }
            BaseTitleStyle.IBTN_TV_IBTN_STYLE ->{
                return R.layout.layout_ibtn_tv_ibtn_style
            }
            BaseTitleStyle.IBTN_IBTN_TV_STYLE ->{
                return R.layout.layout_ibtn_ibtn_tv_style
            }
            BaseTitleStyle.IBTN_IBTN_IBTN_STYLE ->{
                return R.layout.layout_ibtn_ibtn_ibtn_style
            }
            BaseTitleStyle.OTHERS ->{
                return 0
            }


        }
        return 0
    }

    override fun onClick(v: View?) {
        // 防止重复多次点击
        if (BtnClickHelper.isFastDoubleClick()) {
            return
        }
        if (v?.getId() == R.id.header_layout_left_operate_v) {
            finish()
        } else {
            onOtherClick(v!!)

        }
    }

    /**
     * 由于这里已经实现了OnClickListener接口，所以继承此类后，不需要再实现OnClickListener接口，所有的View点击事件️
     * 都在这里操作即可
     */
    abstract fun onOtherClick(v: View)

    /**
     * 左侧点击
     */
    fun setLeftOnClick():BaseInjectActivity<T>{
        setLeftVisible(true)
        leftTv?.setOnClickListener(this)
        leftIBtn?.setOnClickListener(this)
        return this
    }

    /**
     * 右侧点击
     */
    fun setRightOnClick():BaseInjectActivity<T>{
        setRightVisible(true)
        rightTv?.setOnClickListener(this)
        rightIBtn?.setOnClickListener(this)
        return this
    }

    /**
     * 设置左侧图片
     */
    fun setLeftTvResource(leftResource:Int):BaseInjectActivity<T>{
        setLeftVisible(true)
        leftIBtn?.setImageResource(leftResource)
        return this

    }

    /**
     * 设置居中图片
     */
    fun setCenterTvResource(centerResource:Int):BaseInjectActivity<T>{
        centerIBtn?.setImageResource(centerResource)
        return this

    }

    /**
     * 设置右侧图片
     */
    fun setRightTvResource(rightResource:Int):BaseInjectActivity<T>{
        setRightVisible(true)
        rightIBtn?.setImageResource(rightResource)
        return this

    }

    /**
     * 设置左侧文字
     */
    fun setLeftText(leftText:String):BaseInjectActivity<T>{
        setLeftVisible(true)
        leftTv?.text = leftText
        return this
    }

    /**
     * 设置居中标题
     */
    fun setCenterText(centerText:String):BaseInjectActivity<T>{
        setLeftVisible(true)
        centerTv?.text = centerText
        return this
    }

    /**
     * 设置右侧文字
     */
    fun setRightText(rightText:String):BaseInjectActivity<T>{
        setLeftVisible(true)
        rightTv?.text = rightText
        return this
    }

    /**
     * 设置左侧是否可见
     */
    fun setLeftVisible(isVisible:Boolean):BaseInjectActivity<T>{
        leftTv?.visibility = if(isVisible) View.VISIBLE else View.INVISIBLE
        leftIBtn?.visibility = if(isVisible) View.VISIBLE else View.INVISIBLE
        return this
    }

    /**
     * 设置居中是否可见
     */
    fun setCenterVisible(isVisible:Boolean):BaseInjectActivity<T>{
        centerTv?.visibility = if(isVisible) View.VISIBLE else View.INVISIBLE
        centerIBtn?.visibility = if(isVisible) View.VISIBLE else View.INVISIBLE
        return this
    }

    /**
     * 设置右侧是否可见
     */
    fun setRightVisible(isVisible:Boolean):BaseInjectActivity<T>{
        rightTv?.visibility = if(isVisible) View.VISIBLE else View.INVISIBLE
        rightIBtn?.visibility = if(isVisible) View.VISIBLE else View.INVISIBLE
        return this
    }
}