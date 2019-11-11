# KotlinMVP（Dagger2+RXJAVA+Kotlin+OKHttp+MVP）
这个我最近这一周学习到的kotlin相关知识，其中有一下知识点
1、Daggger2实现Activity，Fragment的配置
2、RXJAVA的应用
3、尽量使用了kotlin的相关语言
4、实现了MVP架构
5、通过(mActivity as MainActivity).sendMsg(action:String, value: Any)发送命令,并通过实现onReceive进行监听，类似于广播的结构
6、实现半透明状态栏
7、通过StubView实现加载失败显示友好页
8、提供了接口可以通过实现onTopViewCreated实现顶层栏目
9、在com.wzy.arms.utils包中提供了基本的公共方法
10、对显示的图片实现了圆形、缺角的动态设置，参考com.wzy.arms.widget中的GlideCircleTransform、GlideRoundTransform类
11、实现了OKHttp，并实现了OKhttp的基本配置，参考com.wzy.arms.network.helper中的OkHttpHelper类
12、实现SpringView的分页效果
13、实现底部导航栏
