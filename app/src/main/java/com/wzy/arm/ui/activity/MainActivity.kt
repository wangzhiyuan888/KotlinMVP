package com.wzy.arm.ui.activity

import android.os.Build
import android.view.View
import com.wzy.arm.base.BaseInjectActivity
import com.wzy.arm.di.contract.MainContract
import com.wzy.arm.di.presenter.MainPresenter
import com.wzy.arms.iter.ReceiverAction
import kotlinx.android.synthetic.main.activity_main.*
import com.wzy.arms.widget.BottomTabBar
import com.wzy.arm.ui.fragment.HomeFragment
import com.wzy.arms.base.BaseFragment
import com.wzy.arm.R

/**
 * Crtl+F9 自动生成DaggerMainComponent代码
 */
class MainActivity : BaseInjectActivity<MainPresenter>(), MainContract.View{

    override fun initInject() = activityComponent.inject(this)

    override fun initPresenter() = mPresenter.attachView(this)

    override fun initWidget() {
        // 作用：我的账户的状态栏延伸至最顶部
        /*StatusBarUtil.setTranslucentForImageView(this, 0, null)
        StatusBarUtil.setLightMode(this)*/

        var selectColor: Int?
        var unSelectColor: Int?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            selectColor = resources.getColor(R.color.tab_checked, theme)
            unSelectColor = resources.getColor(R.color.tab_unchecked, theme)
        }else {
            selectColor = resources.getColor(R.color.tab_checked)
            unSelectColor = resources.getColor(R.color.tab_unchecked)
        }

        bottom_bar.init(getSupportFragmentManager())
                .setImgSize(23F,23F)
                .setFontSize(12F)
                .setTabPadding(10F,3F,10F)
                .setChangeColor(selectColor, unSelectColor)
                .addTabItem("首页", R.mipmap.home_selected, R.mipmap.home_unselected, HomeFragment().javaClass)
                .addTabItem("分类", R.mipmap.classification_selected, R.mipmap.classification_unselected, HomeFragment().javaClass)
                .addTabItem("我的", R.mipmap.my_selected, R.mipmap.my_unselected, HomeFragment().javaClass)
                .isShowDivider(true)
                .setOnTabChangeListener(object : BottomTabBar.OnTabChangeListener{
                    override fun onTabChange(position: Int, name: String) {

                    }
                })

    }

    override fun loadData() {
        setCenterText("首页")

    }

    override fun receiverActions(): MutableList<String> {
        return mutableListOf(ReceiverAction.RECEIVER_ACTION_REQUEST_USER)
    }

    override fun contentLayoutId(): Int {
        return R.layout.activity_main
    }

    /**
     * 接收信息并向Fragment中传递数据
     */
    override fun onReceive(action: String, value: Any) {
        for (i in 0 until bottom_bar.tabIdList.size){
            val fragment = (bottom_bar!!.getmTabHost()!!.getWantFragment(i))
            if(null != fragment)
                (fragment as BaseFragment).sendMessageByActivity(action,(value as String)+":"+i)
        }
    }

    /*override fun showError(tag: String, msg: String) {

    }*/

    override fun onOtherClick(v: View) {

    }

}
