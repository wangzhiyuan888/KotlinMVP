#   查出错误原因
#./gradlew -q dependencies app:dependencies --configuration compile
# gradlew compileDebugSources --stacktrace -info

# kotlin基础总结，小白一看就懂

# lateinit 延迟初始化,修饰变量以后变量不用马上赋值

# var 修饰可变参数

# val 修饰不可变参数，相当于final

# const 必须修饰val、const 只允许在top-level级别和object中声明，const val 修饰后相当于public final static， val 修饰后相当于private final static

# object 修改class相当于静态类，companion object{} 包含的方法为静态方法

# "?"加在变量名后，系统在任何情况不会报它的空指针异常。

# "!!"加在变量名后，如果对象为null，那么系统一定会报异常！

# 没有a>b?a:b这样的三目运算符，替代的为if(a>b) a else b

# 中List、Set、Map要进行添加修改操作需使用Mutable(可变的)前缀(MutableList、MutableSet、MutableMap),否则不会有反应

# 使用constructor()操作类方法同时可以使用init{}定义需要处理的数据

# 为类增加open，class就可以被继承,为方法增加open，那么方法就可以被重写

# Delegate.notNull()代理主要用于可以不在构造器初始化时候初始化而是可以延迟到之后再初始化这个var修饰的属性,它和lateinit功能类似

# data来修饰数据类
# 编译器会自动的从主构造函数中根据所有声明的属性提取，如果这些函数在类中已经被明确定义了，或者从超类中继承而来，就不再会生成。
# 1、equals() / hashCode() 2、toString() 格式如 "User(name=John, age=42)"
# 3、componentN() functions 对应于属性，按声明顺序排列，4、copy() 函数
# 为了保证生成代码的一致性以及有意义，数据类需要满足以下条件
# 1、主构造函数至少包含一个参数。2、所有的主构造函数的参数必须标识为val 或者 var ;
# 3、数据类不可以声明为 abstract, open, sealed 或者 inner; 4、数据类不能继承其他类 (但是可以实现接口)。
# 以下结构为
# User(name=Jack, age=1)
# User(name=Jack, age=2)
# data class User(val name: String, val age: Int)
# val jack = User(name = "Jack", age = 1)
#    val olderJack = jack.copy(age = 2)
#    println(jack)
#    println(olderJack)

# 结构申明
# val jane = User("Jane", 35)
# val (name, age) = jane
# println("$name, $age years of age") // prints "Jane, 35 years of age"

# 密封类用来表示受限的类继承结构：当一个值为有限几种的类型, 而不能有任何其他类型时。在某种意义上，他们是枚举类的扩展：枚举类型的值集合 也是受限的，但每个枚举常量只存在一个实例，而密封类 的一个子类可以有可包含状态的多个实例
# 声明一个密封类，使用 sealed 修饰类，密封类可以有子类，但是所有的子类都必须要内嵌在密封类中。
# sealed 不能修饰 interface ,abstract class(会报 warning,但是不会出现编译错误)
# sealed class Expr
# data class Const(val number: Double) : Expr()
# data class Sum(val e1: Expr, val e2: Expr) : Expr()
# object NotANumber : Expr()
# fun eval(expr: Expr): Double = when (expr) {
#     is Const -> expr.number
#     is Sum -> eval(expr.e1) + eval(expr.e2)
#     NotANumber -> Double.NaN
# }

# 延迟属性 Lazy   lazy() 是一个函数, 接受一个 Lambda 表达式作为参数, 返回一个 Lazy <T> 实例的函数，返回的实例可以作为实现延迟属性的委托： 第一次调用 get() 会执行已传递给 lazy() 的 lamda 表达式并记录结果， 后续调用 get() 只是返回记录的结果。
# 结果为
# computed!
# Hello
# Hello
# val lazyValue: String by lazy {
#    println("computed!")     // 第一次调用输出，第二次调用不执行
#    "Hello"
# }
# fun main(args: Array<String>) {
#    println(lazyValue)   // 第一次执行，执行两次输出表达式
#    println(lazyValue)   // 第二次执行，只输出返回值
# }

# let扩展函数的实际上是一个作用域函数，当你需要去定义一个变量在一个特定的作用域范围内，let函数的是一个不错的选择；let函数另一个作用就是可以避免写一些判断null的操作。
# let签名加?则是先判断person是否为空，为空则不执行let中的内容
# person?.let { println(it.name) //输出name}

# inline 的工作原理就是将内联函数的函数体复制到调用处实现内联,如果我们的方法比较大或者调用的比较多的话，那么编译器生成的代码量就会变大,这时候我们就可以用inline了

# with 将某对象作为函数的参数，在函数块内可以通过 this 指代该对象。返回值为函数块的最后一行或指定return表达式。
# val result = with(user, {
#        println("my name is $name, I am $age years old, my phone number is $phoneNum")
#        1000
#    })

# run函数实际上可以说是let和with两个函数的结合体，run函数只接收一个lambda函数为参数，以闭包形式返回，返回值为最后一行的值或者指定的return的表达式。
# 这里result返回了1000
# val result = user.run {
#        println("my name is $name, I am $age years old, my phone number is $phoneNum")
#        1000
#    }
#    println("result: $result")

# apply 从结构上来看apply函数和run函数很像，唯一不同点就是它们各自返回的值不一样，run函数是以闭包形式返回最后一行代码的值，而apply函数的返回的是传入对象的本身
# 这里result返回了user对象本身
# val user = User("Kotlin", 1, "1111111")
#    val result = user.apply {
#        println("my name is $name, I am $age years old, my phone number is $phoneNum")
#        1000
#    }
#    println("result: $result")

# also also函数的结构实际上和let很像唯一的区别就是返回值的不一样，let是以闭包的形式返回，返回函数体内最后一行的值，如果最后一行为空就返回一个Unit类型的默认值。而also函数返回的则是传入对象的本身
# 这里result 返回了"testLet"本身
# val result = "testLet".also {
#        println(it.length)
#        1000
#    }
#    println(result)

# 函数名   函数体内使用的对象            返回值        是否是扩展函数     适用的场景
#   let       it指代当前对象           闭包形式返回         是              适用于处理不为null的操作场景
#   with  this指代当前对象或者省略     闭包形式返回         否              适用于调用同一个类的多个方法时，可以省去类名重复，直接调用类的方法即可，经常用于Android中RecyclerView中onBinderViewHolder中，数据model的属性映射到UI上
#   run   this指代当前对象或者省略     闭包形式返回         是              适用于let,with函数任何场景
#   apply this指代当前对象或者省略     返回this             是              1、适用于run函数的任何场景，一般用于初始化一个对象实例的时候，操作对象属性，并最终返回这个对象。2、动态inflate出一个XML的View的时候需要给View绑定数据也会用到.3、一般可用于多个扩展函数链式调用 4、数据model多层级包裹判空处理的问题
#   also  it指代当前对象               返回this             是              适用于let,with函数任何场景

# as和as?进行强制类型转换, 区别是as?转换失败则会返回null，而不会抛出异常

# is 是某种类型，!is不是某种类型

# int 类型不能通过as转换为String类型，不然会报错

# when 中使用 in 运算符来判断集合内是否包含某实例
# val items = setOf("apple", "banana", "kiwi")
#    when {
#        "orange" in items -> println("juicy")
#        "apple" in items -> println("apple is fine too")
#    }

# For 循环
# val items = listOf("apple", "banana", "kiwi")
#    for (item in items) {
#        println(item)
#    }

# 常用设计模式
  1.单例设计模式（饿汉式、懒汉式）
  2.工厂设计模式（工厂方法模式、抽象工厂模式）
  3.建造者模式
  4.适配器模式（类的适配器模式、对象的适配器模式、接口的适配器模式
  5.装饰器模式
  6.策略模式
  7.代理模式（静态代理、动态代理）
  8.观察者设计模式
  9.原型模式
  10.桥接模式
  11.过滤器模式
  12.组合模式
  13.外观模式
  14.享元模式
  15.责任链模式
  
  
  
# volatile关键字，有一下几个作用
  1 保证内存可见性
  2 禁止指令重排序
  3 不保证原子性
  所以volatile修饰的时候，要用上synchronized或者为int的时候要用上AtomicInteger，来保证原子性



	  

