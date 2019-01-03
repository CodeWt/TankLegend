# 1.[ConcurrentHashMap](https://my.oschina.net/mononite/blog/144329?p=1)  

# 2.[compare and set](http://www.blogjava.net/mstar/archive/2013/04/24/398351.html)
##  Atomic 
    底层使用到了compareAndSet() 而它的函数体中包含compareAndSwapXXX()方法,
    该方法是通过JNI实现的.也就是说是通过JNI调用操作系统的原生程序cmpxchg方法,该汇编是与操
    作系统有关,同时也和cpu有关. 也就是说CAS的原子性实际上是有CPU实现的.其实在这一点上还是
    有排它锁的.只是与Synchronized相比,使用的时间少很多.所以多线程情况下性能会好很多.

##  unsafe
    互斥同步最主要的问题就是进行线程阻塞和唤醒所带来的性能问题，因此这种同步也称
    为阻塞同步（Blocking Synchronization）。从处理问题的方式上说，互斥同步属于一种悲观的
    并发策略，总是认为只要不去做正确的同步措施（例如加锁），那就肯定会出现问题，无论
    共享数据是否真的会出现竞争，它都要进行加锁（这里讨论的是概念模型，实际上虚拟机会
    优化掉很大一部分不必要的加锁）、用户态核心态转换、维护锁计数器和检查是否有被阻塞
    的线程需要唤醒等操作。随着硬件指令集的发展，我们有了另外一个选择：基于冲突检测的
    乐观并发策略，通俗地说，就是先进行操作，如果没有其他线程争用共享数据，那操作就成
    功了；如果共享数据有争用，产生了冲突，那就再采取其他的补偿措施（最常见的补偿措施
    就是不断地重试，直到成功为止），这种乐观的并发策略的许多实现都不需要把线程挂起，
    因此这种同步操作称为非阻塞同步（Non-Blocking Synchronization）。
    为什么笔者说使用乐观并发策略需要“硬件指令集的发展”才能进行呢？因为我们需要操
    作和冲突检测这两个步骤具备原子性，靠什么来保证呢？如果这里再使用互斥同步来保证就
    失去意义了，所以我们只能靠硬件来完成这件事情，硬件保证一个从语义上看起来需要多次
    操作的行为只通过一条处理器指令就能完成.
    
    这类指令常用的有：
    测试并设置（Test-and-Set）。
    获取并增加（Fetch-and-Increment）。
    交换（Swap）。
    比较并交换（Compare-and-Swap，下文称CAS）。
    加载链接/条件存储（Load-Linked/Store-Conditional，下文称LL/SC）。
    
    
    其中，前面的3条是20世纪就已经存在于大多数指令集之中的处理器指令，后面的两条
    是现代处理器新增的，而且这两条指令的目的和功能是类似的。在IA64、x86指令集中有
    cmpxchg指令完成CAS功能，在sparc-TSO也有casa指令实现，而在ARM和PowerPC架构下，
    则需要使用一对ldrex/strex指令来完成LL/SC的功能。
    CAS指令需要有3个操作数，分别是内存位置（在Java中可以简单理解为变量的内存地
    址，用V表示）、旧的预期值（用A表示）和新值（用B表示）。CAS指令执行时，当且仅当
    V符合旧预期值A时，处理器用新值B更新V的值，否则它就不执行更新，但是无论是否更新
    了V的值，都会返回V的旧值，上述的处理过程是一个原子操作。
    在JDK  1.5之后，Java程序中才可以使用CAS操作，该操作由sun.misc.Unsafe类里面的
    compareAndSwapInt（）和compareAndSwapLong（）等几个方法包装提供，虚拟机在内部对
    这些方法做了特殊处理，即时编译出来的结果就是一条平台相关的处理器CAS指令，没有方
    法调用的过程，或者可以认为是无条件内联进去了 [2] 。
    
    由于Unsafe类不是提供给用户程序调用的类（Unsafe.getUnsafe（）的代码中限制了只有
    启动类加载器（Bootstrap  ClassLoader）加载的Class才能访问它），因此，如果不采用反射
    手段，我们只能通过其他的Java  API来间接使用它，如J.U.C包里面的整数原子类，其中的
    compareAndSet（）和getAndIncrement（）等方法都使用了Unsafe类的CAS操作。

# 3.[netty断电重连](http://blog.csdn.net/zero__007/article/details/74355240)

# 4.[redis的学习](https://blog.csdn.net/weixin_37998647/article/details/79113855 )
	 

# 5.[反射](https://www.jianshu.com/p/381ec446a318?utm_campaign=hugo&utm_medium=reader_share&utm_content=note&utm_source=weixin-friends)
    方法	                   作用
    getDeclaredMethods()	获取所有的方法
    getReturnType()	获得方法的返回类型
    getParameterTypes()	获得方法的传入参数类型
    getDeclaredMethod("",.class,……)	获得特定的方法
    getDeclaredConstructors()	获取所有的构造方法
    getDeclaredConstructor(.class,……)	获取特定的构造方法
    getSuperclass()	获取某类的父类
    getInterfaces()	获取某类实现的接口
    
    作者：LightningDC
    链接：https://www.jianshu.com/p/381ec446a318
    來源：简书
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    
# 6.[jvm调优 工具介绍](https://www.jianshu.com/p/c6a04c88900a)

# 7.[自定义解释器](https://www.2cto.com/kf/201207/138637.html)

# 8.[Jython的使用](https://blog.csdn.net/dreamjava9213/article/details/51628341)

# 9.[python中对象与dict的转化](https://blog.csdn.net/leilba/article/details/50654256)
    
## 10.[python序列化与反序列](https://www.cnblogs.com/madq-py/p/5595897.html)

匹配池的作用：
    匹配池中用户的状态
建立线程池   

未完成部分：
扫描器
    扫描地图中 tank能够扫描到的数据，并整理成python需要的数据，然后进行返回处理。

匹配池：
    有一个线程池进行用户玩家的匹配，需要匹配时，调用该线程池进行匹配。

