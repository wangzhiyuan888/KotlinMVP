# <h1>KotlinMVP Dagger2+RXJAVA+Kotlin+OKHttp+MVP </h1> <br/>
这个我最近这一周学习到的kotlin相关知识，其中有一下知识点 <br/>
1、Daggger2实现Activity，Fragment的配置 <br/>
2、RXJAVA的应用 <br/>
3、尽量使用了kotlin的相关语言 <br/>
4、实现了MVP架构 <br/>
5、通过(mActivity as MainActivity).sendMsg(action:String, value: Any)发送命令,
   并通过实现onReceive进行监听，类似于广播的结构 <br/>
6、实现半透明状态栏 <br/>
7、通过StubView实现加载失败显示友好页 <br/>
8、提供了接口可以通过实现onTopViewCreated实现顶层栏目 <br/>
9、在com.wzy.arms.utils包中提供了基本的公共方法 <br/>
10、对显示的图片实现了圆形、缺角的动态设置，
    参考com.wzy.arms.widget中的GlideCircleTransform、GlideRoundTransform类 <br/>
11、实现了OKHttp，并实现了OKhttp的基本配置，
    参考com.wzy.arms.network.helper中的OkHttpHelper类 <br/>
12、实现SpringView的分页效果 <br/>
13、实现底部导航栏 <br/>
