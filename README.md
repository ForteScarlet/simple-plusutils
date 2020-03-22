# simple-plusutils 使用手册

## 前言

这个工具包合集本身是早期我自己留着自用的来着...不过乱七八糟的想法越来越多，于是乎就有了这么一个大合集了。讲道理里面实用性的东西确实不少，于是开始写个文档试试。

首先，文档会对我认为 **值得一提** 的工具类优先进行介绍，而其他我认为可能并不是那么出彩的工具类则会介绍的比较简略。但是根据个人习惯，javadoc文档我会标注的比较全面，任何类的细节均可以查询javadoc文档或者通过下载源码查看。

东西时间跨度还挺大的，有好几年前写的，有最近才想到的，可能有些东西技术层面明显不足，还望谅解。



然后介绍顺序的话就是项目里从上到下的包结构。

如果我个人觉得某个工具包值得介绍，我会标注一个  **`*`** 

最后，我的介绍可能会有点散漫、随意，毕竟我也不知道该怎么正儿八经的介绍一个什么东西都有的工具箱项目，所以干脆就用半聊天的方式写文档了。



不过，假如真的有人想用一用试试的话，我已经将项目上传至了Maven仓库：

> 版本号以仓库中的最新版本号为准，仓库地址：
>
> https://mvnrepository.com/artifact/io.github.ForteScarlet.plusutils/simple-plusutils

```xml
<dependency>
    <groupId>io.github.ForteScarlet.plusutils</groupId>
    <artifactId>simple-plusutils</artifactId>
    <version>1.3.2</version>
</dependency>
```

码云生成的在线Javadoc文档：[javaDoc](https://apidoc.gitee.com/ForteScarlet/simple-plusutils)




[TOC]



###  MD5与String获取可能接口

> `com.forte.utils.ables`

#### StringAble  字符串接口

> `interface`



`StringAble` 接口定义三个接口以获取实现类的字符串。

#### MD5Able  MD5接口

>  `interface`



`MD5Able` 接口继承 `StringAble` , 提供多个`default`方法以在获取字符串的基础上获取其对应的`MD5`加密字符串。（MD5生成依赖`MD5Utils`）



### 基础数据工具

>  `com.forte.utils.basis`



#### CharSequenceUtils  CharSequence操作工具类

> `util class`



字符工具类，提供以下方法：

```java
    /**
     * 去除前后空字符
     */
    public static CharSequence trim(CharSequence s);

    /**
     * 是否为空或者null<br>
     * 此处如果仅空字符也会被当作empty<br>
     *     即会进行trim操作
     * @param s
     * @return
     */
    public static boolean isEmpty(CharSequence s);

    /**
     * 遍历所有字符
     * @param consumer 遍历操作
     * @param s         每一个字符
     */
    public static void foreach(CharConsumer consumer, CharSequence s);

    /**
     * 转化为 <code> char[] </code>
     */
    public static char[] charArray(CharSequence s);

    /**
     * 转化为{@link StringBuilder}
     */
    public static StringBuilder toBuilder(CharSequence s);
    /**
     * 转化为{@link StringBuilder}
     */
    public static StringBuilder toBuilder(int init, CharSequence s);
    /**
     * 转化为{@link StringBuilder}
     */
    public static StringBuilder toBuilder(String init, CharSequence s);

    /**
     * transform into{@link StringBuilder}
     */
    public static StringBuilder toBuilder(CharSequence init, CharSequence s);
```



#### ExString 字符串扩展工具类

> `util class`



`ExString` 继承 `CharSequenceUtils` 并提供、重写了一部分针对字符串对象的方法。

```java
    /**
     * 是否为空（包括全部为空字符的情况）
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s);

    /**
     * 是否为空（包括全部为空字符的情况）
     *
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s);

    /**
     * 将一个字符串等量的复制n倍长度<br>
     * @param base 要复制的字符串
     */
    public static String repeat(String base, int times);

    /**
     * @see #repeat(String, int)
     */
    public static String repeat(char base, int times);

    /**
     * @see #repeat(String, int)
     */
    public static String repeat(int base, int times);

    /**
     * @see #repeat(String, int)
     */
    public static String repeat(Object base, int times);





    /**
     * 获取一个单词的全部排列组合，数量一般为 word.length 的阶乘量
     *
     * @param word 要进行排列的字符串
     * @return 排列结果
     */
    public static String[] getPermutation(String word);

    /**
     * 递归获取字符串所有排列组合
     *
     * @param out  拼接用的StringBuilder
     * @param in   字符串
     * @param used 记录是否拼接过
     * @param s    每一个组合的输出
     */
    private static void getPermutation(StringBuilder out, String in, boolean[] used, Consumer<String> s);

    /**
     * 获取集合的所有切割可能。
     * <code>
     * abc:
     * - ab
     * - ac
     * - a
     * - b
     * - c
     * - bc
     * </code>
     * @param list 元素列表
     */
    public static <T> List<List<T>> getCombinations(List<T> list);

    /**
     * 获取集合的所有切割可能。
     * @param list 元素列表
     */
    public static <T> List<List<T>> getCombinations(T... list);

```







#### ExMath 数学扩展工具类

> `util class`



数学工具类提供了一些方法（貌似现在只有阶乘相关的东西）：

```java
/**
     * 计算某个数的阶乘,n!
     * @param n n!
     */
    public static long factorial(long n);

    /**
     * 计算某个数的阶乘,n! <br>
     * 使用bigInteger进行计算， 理论上可计算long范围内所有值的阶乘值
     * @param n n!
     */
    public static BigInteger factorialBig(BigInteger n);

    /**
     * 计算某个数的阶乘,n! <br>
     * 使用bigInteger进行计算
     * @param n n!
     */
    public static BigInteger factorialBig(long n);

    /**
     * 计算某个数的阶乘,n!
     * @param n n!
     */
    public static long factorialParallel(long n);

    /**
     * 计算某个数的阶乘,n! <br>
     * 使用bigInteger进行计算， 理论上可计算long范围内所有值的阶乘值
     * @param n n!
     */
    public static BigInteger factorialBigParallel(BigInteger n);

    /**
     * 计算某个数的阶乘,n! <br>
     * 使用bigInteger进行计算
     * @param n n!
     */
    public static BigInteger factorialBigParallel(long n);

```



#### MD5Utils MD5工具类

> `util class`



提供两个方法加密字符串为MD5，加密函数来自Java

```java
	public static String toMD5(String inStr);

    public static String toMD5(byte[] byteArray);
```



#### StringListReader  字符串读取流

> `class`



将一个字符串转化为Reader流对象。

```java
Reader reader = new StringListReader("string");
```



### 中文相关工具 **`*`**

> `com.forte.utils.chinese`



#### ChineseUtil 随机中文工具类

> `util class`



提供一些方法以生成随机的中文或者随机的姓名。不过随机的中文中生僻字可能会很多，这就间接导致了随机中文姓名看上去会有些别扭..

顺便一提，姓氏来自于百家姓。



```java
    /**
     * 获取一个随机姓名
     *
     * @param charsetName 字符编码
     * @return
     */
    public static String getName(String charsetName);

     /**
     * 获得多个随机姓氏
     *
     * @return
     */
    public static String[] getFamilyName(int nums);


    /**
     * 获得一个随机姓氏
     *
     * @return
     */
    public static String getFamilyName();

     /**
     * 获取一个随机姓名
     *
     * @return
     */
    public static String getName();

    /**
     * 获取一个汉字
     *
     * @param encoding 编码格式
     * @return
     */
    public static String getChinese(String encoding);

    /**
     * 获取一个随机汉字
     *
     * @return
     */
    public static String getChinese();

   /**
     * 获取一个随机汉字
     *
     * @return
     */
    public static String getChinese(int num);

    /**
     * 获取一个随机汉字
     *
     * @return
     */
    public static String getChinese(int num, String encoding) ;



```





#### CNumerUtil 中文数字转化工具包 

> `package com.forte.utils.chinese.chinesenumber`
>
> `util classes`



这就不是一个单个儿的工具类了，而是一组工具包，对外接口是CNumerUtil类。

此类对外的方法窗口只有两个：

```java
   /**
     * 将中文数字转化为真正的数字
     * @param chinese 中文数字
     * @return
     */
    public static <T extends Number> CNumber<T> toNumber(String chinese);

   /**
     * 解析可能存在运算符的中文字符并计算结果 <br>
     * 假如结果是布尔值，则true:1，false:0 <br>
     *
     */
    public static CNumber toCalculation(String chinese) throws ScriptException;

```



虽然窗口只有两个，但是总体来讲功能是很强的，对于toNumber方法基本上只要你不用什么奇怪的生僻数字写法或者什么地区方言的数字表达方式，基本上转化的准确率是很高的呢~

而对于第二个方法 toCalculation 来说，我的测试数据还是比较少，总的来说就是会把加减乘除啊之类的计算也考虑进去，（计算符号同JS中的运算符而不是现实中的数学运算符），同样的，只要不要太奇怪的都是可以解析出来的。





### 集合扩展工具 

> `com.forte.utils.collections`



#### CacheMap<K, V> 缓存Map

> `class`



这个Map在最初的想法中是可以实现使得存入的值有一定的时效性，当过期后再去获取就获取不到这个样子，让他有个“保质期”，目前的实现方式是懒判断，在获取的时候去判断是否过期，但是这个样子有一个弊端就是假如你这辈子都不再去获取他，那么他并不会在Map中消失，存在一定的资源浪费。

内部使用的Map是ConcurrentHashMap以保证一定的线程安全性。

除了Map原生接口以外提供的方法有：

```java
 /**
     * 添加一个，如果时间已经过期则不添加
     */
    public R put(T t, R r, LocalDateTime expireDate);
    /** 添加一个，指定过期规则 */
    public R put(T t, R r, TemporalAmount amount);

    /** 存值并指定过期规则 */
    public R put(T t , R r, long amountToAdd, TemporalUnit unit);

    /** 过xx纳秒之后过期 */
    public R putPlusNanos(T t , R r, long nanos);

    /** 过xx秒之后过期 */
    public R putPlusSeconds(T t , R r, long seconds);

    /** 过xx分钟后过期 */
    public R putPlusMinutes(T t , R r, long minutes);

    /** 过xx小时之后过期 */
    public R putPlusHours(T t , R r, long hours);

    /** 过xx天之后过期 */
    public R putPlusDays(T t , R r, long days);

    /** 过xx月之后过期 */
    public R putPlusMonth(T t , R r, long month);

    /** 过xx年之后过期，如果你能等到那时候的话 */
    public R putPlusYear(T t , R r, long year);

```





#### ExArrayIterable 数组元素迭代器

> class` 



顾名思义，给一个数组，还你一个迭代器。

暂时不支持基础数据类型。

```java
// 通过构造

```





#### ExCollections Collections扩展

> `util class`



提供一些我曾经想要但是没有在`Collections`找到的集合相关辅助操作方法。比如给Map排个序啥的。

```java
 /**
     * 通过流复制一个对象
     * @param collection    collection对象
     * @param collectionFactory collection工厂
     */
    public static <E, C extends Collection<E>> Collection<E> copy(Collection<E> collection, Supplier<C> collectionFactory);

    /**
     * 转为List集合
     * @param collection collection
     */
    public static <E> List<E> toList(Collection<E> collection);

    /**
     * 转为Set
     * @param collection collection
     */
    public static <E> Set<E> toSet(Collection<E> collection);

    /**
     * 为Map排序
     */
    public static <K extends Comparable<K>, V> Map<K, V> mapKeySorted(Map<K, V> map);

    /**
     * 为Map排序
     */
    public static <K, V extends Comparable<V>> Map<K, V> mapValueSorted(Map<K, V> map);
    /**
     * 为Map排序
     */
    public static <K extends Comparable<K>, V> Map<K, V> mapKeySorted(Stream<Map.Entry<K, V>> stream);

    /**
     * 为Map排序
     */
    public static <K, V extends Comparable<V>> Map<K, V> mapValueSorted(Stream<Map.Entry<K, V>> stream);

    /**
     * 为Map排序
     */
    public static <K, V> Map<K, V> mapKeySorted(Map<K, V> map, Comparator<K> keyCompared);

    /**
     * 为Map排序
     */
    public static <K, V> Map<K, V> mapValueSorted(Map<K, V> map, Comparator<V> valueCompared);
    /**
     * 为Map排序
     */
    public static <K, V> Map<K, V> mapKeySorted(Stream<Map.Entry<K, V>> stream, Comparator<K> keyCompared);

    /**
     * 为Map排序
     */
    public static <K, V> Map<K, V> mapValueSorted(Stream<Map.Entry<K, V>> stream, Comparator<V> valueCompared);

    /**
     * 转化为可变的List
     */
    public static <A> List<A> toList(A[] array);



```





#### Maputer Map存值工具类 **`*`**

> `util class`



关于这个工具类，我认为它值得一提的点不是功能，而是它的名字233，Maputer名称由来：Map 对map的操作，什么操作？put 操作，谁在操作？工具类这个角色( er ) 进行操作，于是Maputer = Map + put + er 

哈哈。

挺冷的。

```java
/**
     * 向map中存放一个值
     * 如果存在，将存在的值传入并返回一个要保存的值
     * 如果不存在，无参数，获取一个要保存的值<br>
     *     <code>
     *         Map<String, Integer> map = new HashMap<>(2);
     *         int value = 20;
     *         Maputer.put(map, "key", e -> e + value, () -> value);
     *         // 第一次的结果，放进去了value，key:20
     *         Maputer.put(map, "key", e -> e + value, () -> value);
     *         // 第二次的结果，本来是存在的，所以老值 + 放入值，key:40
     *     </code>
     * @param map       操作的map
     * @param key       操作的key
     * @param ifExist   如果值存在的函数
     * @param ifNull    如果值为null的函数
     */
    public static <K, V> V put(Map<K, V> map, K key, Function<V, V> ifExist, Supplier<V> ifNull);

    /**
     * 添加了 synchronized 关键字的{@link #put} 方法
     * @param map       操作的map
     * @param key       操作的key
     * @param ifExist   如果值存在的函数
     * @param ifNull    如果值为null的函数
     */
    public synchronized static <K, V> void putSynchronized(Map<K, V> map, K key, Function<V, V> ifExist, Supplier<V> ifNull);

    /**
     * 获取map中的某个值
     * 如果存在，操作这个值，否则存入一个值
     * 如果不存在，无参数，获取一个要保存的值<br>
     *     <code>
     *         Map<String, Integer> map = new HashMap<>(2);
     *         int value = 20;
     *         Maputer.put(map, "key", e -> e + value, () -> value);
     *         // 第一次的结果，放进去了value，key:20
     *         Maputer.put(map, "key", e -> e + value, () -> value);
     *         // 第二次的结果，本来是存在的，所以老值 + 放入值，key:40
     *     </code>
     * @param map       操作的map
     * @param key       操作的key
     * @param ifExist   如果值存在的函数
     * @param ifNull    如果值为null的函数
     */
    public static <K, V> void peek(Map<K, V> map, K key, Consumer<V> ifExist, Supplier<V> ifNull);

    /**
     * 添加了 synchronized 关键字的{@link #put} 方法
     */
    public synchronized static <K, V> void peekSynchronized(Map<K, V> map, K key, Consumer<V> ifExist, Supplier<V> ifNull);

    //**************************************
    //*             整合方法
    //**************************************


    /**
     * 给定一个value值为Colletion<E>集合类型的map集合，给定一个Collection<E>类型的值参数
     * 如果value值存在，添加所有，如果不存在则保存
     * @param map   map集合
     * @param key   键
     * @param collections   要保存或要添加的
     */
    public static <E, K, V extends Collection<E>> void addAll(Map<K, V> map, K key, V collections);

    /**
     * 线程安全的{@link #addAll} 方法
     */
    public static <E, K, V extends Collection<E>> void addAllSynchronized(Map<K, V> map, K key, V collections);

    /**
     * 给定一个value值为Colletion<E>集合类型的map集合，给定一个E类型的参数
     * 如果value值存在，添加，如果不存在则使用给定的参数填充
     * @param map   map集合
     * @param key   键
     * @param one   要保存或要添加的
     */
    public static <E, K, V extends Collection<E>> void add(Map<K, V> map, K key, E one, V ifNull);

    /**
     * 线程安全的{@link #add} 方法
     */
    public static <E, K, V extends Collection<E>> void addSynchronized(Map<K, V> map, K key, E one, V ifNull);


    /**
     * 给定一个value值为Colletion<E>集合类型的map集合，给定一个E类型的参数
     * 如果value值存在，添加，如果不存在则使用给定的方法获取值填充
     * @param map   map集合
     * @param key   键
     * @param one   要保存或要添加的
     */
    public static <E, K, V extends Collection<E>> void add(Map<K, V> map, K key, E one, Supplier<V> ifNull);

    /**
     * 线程安全的{@link #add(Map, Object, Object, Supplier)} 方法
     */
    public static <E, K, V extends Collection<E>> void addSynchronized(Map<K, V> map, K key, E one, Supplier<V> ifNull);

```





### 文件相关工具

> `com.forte.utils.file`



#### ExFileUtils 文件工具拓展

> `util class`



这个工具类讲道理有很多方法并不是怎么成功，也有很多东西使用起来并不是很友好（大部分为读写操作），但是你们肯定都是用common-io的吧所以读写这部分的毛病暂且先无视吧~

读的话只有读行，不过是通过流形式读的效率还可以，但是写的话参数是`File` 和 `CharStream` ，所以挺麻烦的。

先不提读写，除了读写它还提供了一些别的方法例如获取创建文件、创建临时文件、创建系统结束就会删除的文件、获取主文件夹（例如windows的桌面）等。

```java

    /**
     * 获取FileNameJoiner
     */
    public static FileNameJoiner nameJoiner();


    /**
     * 读取全部行（流对象
     */
    public static StringStream getLines(File file) throws FileNotFoundException;

    /**
     * 将字符流写入文件
     * @param file  文件对象
     * @param chars 字符流
     * @throws IOException
     */
    public static void write(File file, CharStream chars) throws IOException;

    /**
     * 将行写入文件
     * @param file  文件对象
     * @param lines 行数据
     * @throws IOException
     */
    public static void write(File file, String... lines) throws IOException;

    /**
     * 将行写入文件
     * @param file  文件对象
     * @param lines 行数据
     * @throws IOException
     */
    public static void write(File file, Collection<String> lines) throws IOException;

    /**
     * 读取文件的char流对象
     * 效率情况不是很乐观，遍历全部字符的速度是BufferedReader的lines流遍历所有行数据的3倍。
     * 啥？优化？懒。
     * @param file  文件对象
     * @return
     * @throws IOException
     */
    public static CharStream getChars(File file) throws IOException;

    /**
     * 如果文件不存在，创建一个新文件
     * @param file 文件对象
     * @return  是否创建
     * @throws IOException
     */
    public static boolean createFile(File file) throws IOException;

    /**
     * 获取主文件夹，例如window的桌面等
     * @return
     */
    public static File getHomeDirectory();

    /**
     * 获取文件在系统中的名称
     * @param file 文件对象
     */
    public static String getFileDisplayName(File file);

    /**
     * 获取{@link FileSystemView} 对象
     */
    public static FileSystemView getFileSystemView();

    /**
     * 获取一个临时文件
     * @param prefix        文件名前半部分，如果长度不足3则用'_'(下划线)进行补全
     * @param suffix        文件名后半部分，如果为null则默认为.tmp, nullable
     * @param directory     指定一个腹肌文件夹，nullable
     * @param deleteOnExit  是否在程序结束之后删除此文件, 默认不删除
     * @return 临时文件
     * @throws IOException
     */
    public static File getTempFile(String prefix, String suffix, File directory, boolean deleteOnExit) throws IOException;

    /**
     * @see #getTempFile(String, String, File, boolean)
     */
    public static File getTempFile(String fileName, File directory, boolean deleteOnExit) throws IOException;

    /**
     * @see #getTempFile(String, String, File, boolean)
     */
    public static File getTempFile(String fileName, boolean deleteOnExit) throws IOException;

    /**
     * @see #getTempFile(String, String, File, boolean)
     */
    public static File getTempFile(String fileName, File directory) throws IOException;


    /**
     * @see #getTempFile(String, String, File, boolean)
     */
    public static File getTempFile(String prefix, String suffix, boolean deleteOnExit) throws IOException;

    /**
     * @see #getTempFile(String, String, File, boolean)
     */
    public static File getTempFile(String prefix, String suffix, File directory) throws IOException;

    /**
     * @see #getTempFile(String, String, File, boolean)
     */
    public static File getTempFile(String prefix, String suffix) throws IOException;

```



关于第一个方法中的返回值`FileNameJoiner` , 就是一个拼接文件路径的小工具，主要是文件路径中它使用的分割符是`File`类中`separator` 以保证不同系统下可以兼容。具体方法就不提了。



### 函数接口扩展 **`*`**

> `com.forte.utils.function`



此包下提供了大量的Java中的函数接口的扩展，毕竟都是函数我就不基于方法的一一介绍了，直接介绍类了。

```
ByteConsumer	
ByteFunction
CharConsumer
CharFunction
ExConsumer
ExFunction
ExFunctionThrows
FunctionThrows
```



以上罗列的，是一些没有的基础数据类型函数、三参数函数(开头为Ex的)、自带Throws的函数。

所谓三参数就是相对于`Bi` 开头的函数再多一个参数。

什么？byte类型可以用int的函数替代？是啊，但是反正写都写了。





#### SerializableFunctions 序列化函数

> `util interfaces`



有时候，你的一个类中有一个字段是一个函数字段，且你想要它能够被 `Serializable` 序列化，那么除了使用强转的方式使其得以实例化，还有一种方法就是再写一个实现了接口`Serializable` 的函数去代替原本的函数。

注意：假如使用了这种函数，你需要在多加考虑以下，这有可能还涉及到了`serialVersionUID`这个东西。

这些函数在`SerializableFunctions `类下，Java自带的所有函数均有对应。（大概吧



然后在`SerializableFunctions`类中我又顺手提供了一个静态方法用于获取一个类的泛型字符串（如果有的话。）例如：Map就会获取到"<K,V>"

```java
 /**
     * Get the generic string of the Class ( if it exists )
     * @param c class
     * @return generic string
     */
    public static String getGenericString(Class<?> c)
```





### 随机工具

> `com.forte.utils.random`



#### MockUtil 假数据工具

> `util class`



这个工具类主要是用于我的另一个项目`Mock.java`(如果您有兴趣可以看一下~顺手点个start之类的)，内部提供很多生成假数据的方法。由于数量比较多，不在此罗列，总体上有那么一些内容：

- 名称、title等相关
- date相关
- number相关
- String character相关
- boolean 相关
- color相关
- text相关

- web相关



#### RandomUtil 随机值工具

> `util class`



与上面那个类似，不过`MockUtil`中大部分方法都是依赖于`RandomUtil`的。用于通过各种方式和需求生成各种随机值。例如根据0-1的概率或者百分比概率生成一个布尔值、随机整数小数、随机颜色、返回数组/集合中的随机元素等等。同样不再一一罗列。





### 反射工具 **`*`**

> `com.forte.utils.reflect`



逐渐步入正题的感觉...反射相关包下提供的几个工具类我觉得都还是挺实用的，也不怎么常见，所以我觉得这部分的工具还是蛮有价值的。

#### EnumUtils 枚举工具类

> `util class`



说实话，想要动态创建枚举实例的需求确实不怎么常见，但是从我个人角度来看却已经遇见了两次...于是便直接写了这个一个工具类，主要用于针对某种枚举类型来创建一个新的实例对象。

```java
/**
     * 获取一个枚举的新实例对象
     * @param enumType          枚举类型
     * @param name              枚举名称
     * @param constructorTypes  构造方法参数类型集
     * @param args              构造对应的参数列表
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     */
    public static <T extends Enum<T>> T newEnum(Class<T> enumType, String name, Class<?>[] constructorTypes, Object[] args) throws NoSuchMethodException, IllegalAccessException;

/**
     * 获取一个枚举的新实例对象
     * 默认尝试使用枚举的无参构造
     * @param enumType  枚举类型
     * @param name      枚举的name
     * @return          枚举新实例对象
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     */
    public static <T extends Enum<T>> T newEnum(Class<T> enumType, String name) throws NoSuchMethodException, IllegalAccessException;


 /**
     * 创建一个枚举新实例，会根据枚举构造参数数量尝试寻找唯一对应的构造。
     * 找不到或者有多个会抛出异常。
     * @param enumType  枚举类型
     * @param name      枚举名称
     * @param args      枚举的构造参数
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     */
    public static <T extends Enum<T>> T newEnum(Class<T> enumType, String name, Object... args) throws NoSuchMethodException, IllegalAccessException;


/**
     * 获取枚举的全部实例
     * 由于原生的values属于final字段无法修改，于是从此处提供一个方法获取values值
     * 为了使得效果与原生values一致，会进行排序
     * @param t     枚举类型
     * @return      枚举全部实例
     */
    public static <T extends Enum<T>> T[] values(Class<T> t, IntFunction<T[]> initArray);

  /**
     * 通过枚举的类型获取这个枚举类型的成员Map
     * 会进行copy
     * @param enumType  枚举类型
     * @return          枚举内部全部成员对应键值对
     */
    public static <T extends Enum<T>> Map<String, T> getEnumConstantDirectory(Class<T> enumType) throws IllegalAccessException ;

```



首先要提的是，毕竟是反射，使用之前先考虑考虑效率和安全问题。关于效率，内部的实例化流程并不会每次都从头来一遍，对于一些必要的资源有缓存，所以其实效率不会太低，个人测试生成1000_0000个枚举新实例对象用时大概25648ms ，但是反正你不可能会用到这么多枚举的对吧？

其次，枚举实例在被创建之后，是可以通过`valueO`f方法直接进行获取的。但是无法通过`values()` 方法获取，毕竟这两个方法的实现原理并不怎么一样。所以如果需要`values()` , 那么可以考虑使用 工具类中的`values`.

最后，别吐槽为啥一些类用的内部类，因为我只要让工具类只存在一个类而不是一个工具包。👌



#### FieldUtils & MethodUtils 反射工具类

> `util class`



为啥要放在一起说呢？因为他俩相互依赖..

`FieldUtils` 大概是这里面最大的一个工具类，至于为啥这么大，还是上面哪个原因：不想让一个工具类变成工具包，所以抛开单个类的大小不谈，谈谈内容。

FieldUtils内部提供了大量的方法用于获取：类的字段、方法，某个类是否是某个类的子类/父类、类的字段的Getter/setter 方法、通过getter/setter获取字段值等等一系列方法，并且在获取字段的时候是支持`field1.field2`这样子的多层级获取的。不过既有支持多层级的又有仅支持单层级的所以用的时候需要多加注意。（一般结尾有个getter的支持多层级的）



除了方法比较多，还有导致这个类这么大的原因之一就是我使用了一部分内部类来实现了反射的缓存，也就是说当你反射过一次之后，结果结果会被缓存，假如再次获取（例如获取一个字段）就不会再走一遍反射流程了。牺牲了资源换取效率。



由于方法比较多就不罗列了。



而关于`MethodUtil`，它内部的东西就比较少了，只有三个：

```java


    /**
     * 执行一个方法，可以为基本的数据类型进行转化
     *
     * @param obj
     * @param args
     * @param method
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object invoke(Object obj, Object[] args, Method method) throws InvocationTargetException, IllegalAccessException ;

    /**
     * 执行一个方法，可以为基本的数据类型进行转化
     *
     * @param obj
     * @param args
     * @param methodName 方法名
     * @return
     * @throws NoSuchMethodException
     */
    public static Object invoke(Object obj, Object[] args, String methodName) throws NoSuchMethodException;

    /**
     * js中的eval函数，应该是只能进行简单的计算
     * 只能执行js代码字符串
     * 利用js脚本完成
     *
     * @param str 需要进行eval执行的函数
     * @return 执行后的结果
     */
    public static Object eval(String str) throws ScriptException;
```



不过在invoke中，他会对填入的参数与方法的参数列表进行对应并尝试进行数据转化（使用BeanUtils的ConvertUtils.convert）







#### ObjectUtils 对象工具类

> `util class`

这个类目前只有一个方法，就是

```java
    /**
     * 通过反射，为一个对象生成toString字符串
     */
    public static String toString(Object object);
```

通过反射来获取一个toString字符串。当然，依旧无法避免递归问题，但是它基本上可以实现绝对的toString，不过说白了，就是字段的递归而已。





#### ProxyUtils 动态代理工具类

> `util class`

提供一些方法来使用java自带的动态代理机制来为某些注解、接口类型生成代理对象。其中，提供了方法使得生成的注解代理对象中，方法的返回值即为规定的默认值，或者为指定的没有默认返回值的方法指定默认的返回值。



当然，本质都是一样的，简单的动态代理而已。

```java
/**
     * 从某个类上获取注解的代理对象
     *
     * @param annotationType      此枚举的类型
     * @param type          从某个类身上拿到此枚举
     * @param invocationHandlerCreator 通过枚举对象和类自身来构建InvocationHandler接口对象
     */
    public static <T extends Annotation> T proxy(Class<T> annotationType, Class<?> type, BiFunction<T, Class, InvocationHandler> invocationHandlerCreator);


    /**
     * 从某个字段上获取注解的代理对象
     *
     * @param annotationType      此枚举的类型
     * @param field          从某个字段身上拿到此枚举
     * @param invocationHandlerCreator 通过枚举对象和类自身来构建InvocationHandler接口对象
     */
    public static <T extends Annotation> T proxy(Class<T> annotationType, Field field, BiFunction<T, Field, InvocationHandler> invocationHandlerCreator);

    /**
     * 从某个字段上获取注解的代理对象
     *
     * @param annotationType      此枚举的类型
     * @param method          从某个方法身上拿到此枚举
     * @param invocationHandlerCreator 通过枚举对象和类自身来构建InvocationHandler接口对象
     */
    public static <T extends Annotation> T proxy(Class<T> annotationType, Method method, BiFunction<T, Method, InvocationHandler> invocationHandlerCreator);

    /**
     * 从某个字段上获取注解的代理对象
     *
     * @param annotationType      此枚举的类型
     * @param parameter          从某个方法参数身上拿到此枚举
     * @param invocationHandlerCreator 通过枚举对象和类自身来构建InvocationHandler接口对象
     */
    public static <T extends Annotation> T proxy(Class<T> annotationType, Parameter parameter, BiFunction<T, Parameter, InvocationHandler> invocationHandlerCreator);


    /**
     * 为一个接口生成代理对象
     */
    public static <T> T proxy(Class<T> interfaceType, ExProxyHandler<Method, Object[], Object> proxyHandler);

    /**
     * 为一个注解类型生成代理对象
     * @param annotationType    注解类型
     * @param proxyHandler      函数，接收方法、参数，返回方法的执行返回值
     */
    public static <T extends Annotation> T annotationProxy(Class<T> annotationType, ExProxyHandler<Method, Object[], Object> proxyHandler);

    /**
     * 获取一个额外默认值Map
     * 就是个HashMap而已。
     * @return
     */
    public static Map<String, BiFunction<Method, Object[], Object>> getProxyDefaultReturnMap();

    /**
     * 默认返回注解的默认值，如果没用默认值则查询提供的默认值Map，如果没有则抛出异常
     * @param annotationType    注解类型
     * @param defaultReturn     额外的默认值映射，key为方法名，value为函数，接收方法、参数，返回一个执行值
     */
    public static <T extends Annotation> T annotationProxyByDefault(Class<T> annotationType, Map<String, BiFunction<Method, Object[], Object>> defaultReturn);

    /**
     * 默认返回注解的默认值，如果没用默认值则查询提供的默认值Map，如果没有则抛出异常
     * @param annotationType    注解类型
     */
    public static <T extends Annotation> T annotationProxyByDefault(Class<T> annotationType);
```



其中，`ExProxyHandler` 是一个函数接口，定义如下：

```java
    /**
     *  带着异常处理的BiFunction，用于构建动态代理的参数
     */
    @FunctionalInterface
    public interface ExProxyHandler<T, U, R> {
        /**
         * 函数接口
         * @param t 第一参数
         * @param u 第二参数
         * @return  返回值
         * @throws Throwable 任意异常
         */
        R apply(T t, U u) throws Throwable;
    }
```



### 正则工具

> `com.forte.utils.regex`



#### RegexUtil 正则工具类

> `util class`



说是正则工具类，实际上就是根据正则切割字符串、截取字符串以及获取`Pattern`和`Matcher`对象而已，以及一个不明意义的从一段SELECT的sql中提取出使用到的表名，而且还不支持JOIN的表连接。



```java
/**
     * 通过[aA]这样的形式实现简单粗暴的忽略大小写。
     * @param str 正则
     */
    public static String toIgnoreCaseRegex(String str);

    /**
     * 获取正则对应的pattern
     */
    public static Pattern getPattern(String regex, int flags);

    public static Pattern getPattern(String regex);

    public static Matcher getMatcher(String source, String regex);

    public static Matcher getMatcher(String source, String regex, int flags);

    public static Matcher getMatcher(String source, Pattern pattern);

    /**
     * 切割出匹配正则的字符串列表
     * <code>
     *     String s = "abaacaaad";
     *     List<String> list = getSplit(s, "a+");
     *     list -> [a, aa, aaa]
     * </code>
     */
    public static List<String> getSplit(String source, Pattern pattern);

    /**
     * 切割出匹配正则的字符串流对象
     */
    public static Stream<String> getSplitStream(String source, Pattern pattern);

    /**
     * 切割出匹配正则的字符串列表
     */
    public static List<String> getSplit(String source, String regex);

    /**
     * 切割出匹配正则的字符串列表
     */
    public static Stream<String> getSplitStream(String source, String regex);

    /**
     * 获取字符串
     */
    public static String[] getSplitArray(String source, String regex);

    /**
     * 获取字符串
     */
    public static String[] getMatcherArray(String source, Pattern pattern);


    /**
     * 获取sql查询语句中使用到的表名
     * 暂时不支持join类型的查询sql
     * @param selectSql
     * @return
     */
    public static List<String> getTableNameFromSql(String selectSql);
```





### 单例工具

> `com.forte.utils.single`



#### SingleFactory 单例工厂

> `util class`



这个单例工厂是我上学的时候刚刚看到了CAS乐观锁概念之后，又正好学到了单例相关的知识之后写出来的东西。现在回来看看其实写的并不怎么样，也基本上不完全是CAS的样子。不过至今尚未优化，毕竟功能也确实是单例工厂，或者说是一个单例仓库更为确切，就先当留着做个纪念吧。而且说是单例工厂，实际上就是用Map保存一下而已，想要真正的实现单例还是要靠自己啊。



```java
/** 工厂 */
    public synchronized static SingleFactory build(Object obj);

    /** 工厂 */
    public synchronized static SingleFactory build();

    /**
     * 清空所有保存的数据
     */
    public void clear();

     /**
     * 获取单例，如果没有此类的记录则返回空
     */
    public final <T> T get(Class<? extends T> clz);

/**
     * 获取单例，如果没有则尝试使用反射获取一个新的，将会被记录。
     * 如果创建失败将会抛出相应的异常
     * @param <T>
     * @return
     */
    public final <T> T getOrNew(Class<T> clz);

    public final <T> T getOrNew(Class<? extends T> clz , Object... params);


    /**
     * 如果存在则获取，不存在则赋值
     * @param clz
     * @param t
     * @param <T>
     * @return
     */
    public final <T> T getOrSet(Class<? extends T> clz, T t);

    /**
     * 如果存在则获取，不存在则赋值，不指定class对象
     * @param t
     * @param <T>
     * @return
     */
    public final <T> T getOrSet(T t);


    /**
     * 如果存在则获取，不存在则赋值
     * @param clz
     * @param supplier
     * @param <T>
     * @return
     */
    public final <T> T getOrSet(Class<? extends T> clz, Supplier<? extends T> supplier);

    /**
     * 如果存在则获取，不存在则赋值，不指定class对象
     * @param supplier
     * @param <T>
     * @return
     */
    public final <T> T getOrSet(Supplier<? extends T> supplier);

    /**
     * 重设一个单例
     * @param clz
     * @param t
     * @param <T>
     */
    public final <T> void reset(Class<? extends T> clz , T t);


    /**
     * 重设一个单例，不指定class
     * @param t
     * @param <T>
     */
    public final <T> void reset(T t);

    /**
     * 重设一个单例并获取单例实例
     * @param clz
     * @param t
     * @param <T>
     * @return
     */
    public final <T> T resetAndGet(Class<? extends T> clz , T t);


    /**
     * 重设一个单例并获取单例实例，不指定class
     * @param t
     * @param <T>
     * @return
     */
    public final <T> T resetAndGet(T t);

    /**
     * 重设一个单例
     * @param clz
     * @param supplier
     * @param <T>
     */
    public final <T> void reset(Class<? extends T> clz , Supplier<? extends T> supplier);


    /**
     * 重设一个单例，不指定class
     * @param supplier
     * @param <T>
     */
    public final <T> void reset(Supplier<? extends T> supplier);

    /**
     * 重设一个单例并获取单例实例
     * @param clz
     * @param supplier
     * @param <T>
     * @return
     */
    public final <T> T resetAndGet(Class<? extends T> clz , Supplier<? extends T> supplier);

    /**
     * 重设一个单例并获取单例实例，不指定class
     * @param supplier
     * @param <T>
     * @return
     */
    public final <T> T resetAndGet(Supplier<? extends T> supplier);


    /**
     * 记录一个单例
     * @param clz
     * @param t
     * @param <T>
     */
    public final <T> void set(Class<? extends T> clz , T t);

    /**
     * 记录一个单例，不指定class
     * @param t
     * @param <T>
     */
    public final <T> void set(T t);

    /**
     * 记录一个单例并获取单例实例
     * @param clz
     * @param t
     * @param <T>
     * @return
     */
    public final <T> T setAndGet(Class<? extends T> clz , T t);


    /**
     * 记录一个单例并获取单例实例，不指定class
     * @param t
     * @param <T>
     * @return
     */
    public final <T> T setAndGet(T t);

    /**
     * 记录一个单例
     * @param clz
     * @param supplier
     * @param <T>
     */
    public final <T> void set(Class<? extends T> clz , Supplier<? extends T> supplier);

    /**
     * 记录一个单例，不指定class
     * @param supplier
     * @param <T>
     */
    public final <T> void set(Supplier<? extends T> supplier);

    /**
     * 记录一个单例并获取单例实例
     * @param clz
     * @param supplier
     * @param <T>
     * @return
     */
    public final <T> T setAndGet(Class<? extends T> clz , Supplier<? extends T> supplier);

    /**
     * 记录一个单例，不指定class
     * @param supplier
     * @param <T>
     * @return
     */
    public final <T> T setAndGet(Supplier<? extends T> supplier);
```





### 流工具 **`*`**

> `com.forte.utils.stream`



这整个工具包下，都是对Java8中的`Stream`流的一个拓展，并提供了一个`ExStream`来作为类似于Stream流一样的入口，提供了大量的更加简化操作的方法。没有什么技术含量，就是简化以下操作而已。

提供的功能有例如：

通过一个迭代器对象创建流对象

```java
    /**
     * 一个基于迭代器的流对象
     * @param iter
     * @param <T>
     * @return
     */
    public static <T> ExStream<T> byIter(Iterator<T> iter, boolean parallel);

    /**
     * 基于迭代器的流对象，并使用ExStream封装
     */
    public static <T> ExStream<T> byIter(Iterator<T> iter);

```

以及其他一系列的工厂方法，且返回值均为此包下的扩展流对象(`ExStream`) 的相关实现类。

不过这部分当初懒了没写注释..虽然看方法名和返回值能猜个八九不离十

```java
    public static <T> ExStream<T> of(T t);
    public static <T> ExStream<T> of(T... ts) ;
    public static <T> ExStream<T> of(Stream<T> stream) ;
    public static <T> ExStream<T> of(Collection<T> collection);
    public static <K, V> ExMapStream<K, V> of(Map<K, V> map);
    public static <K, V> ExMapStream<K, V> of(Map.Entry<K, V>[] array);
    public static <T> ExStream<T> iterate(final T seed, final UnaryOperator<T> f);
    public static <T> ExStream<T> concat(Stream<? extends T> a, Stream<? extends T> b);
    public static <T> ExStream<T> empty() ;
    public static <T> ExStream<T> generate(Supplier<T> s) ;


    //**************** 整合其他 ****************//
    //**************** byte ****************//

    public static ByteStream ofByte(byte... values) ;
    public static ByteStream ofByte(byte value) ;
    public static ByteStream ofByte(String str);
    public static ByteStream ofByte(Charset charset, byte... values) ;
    public static ByteStream ofByte(Charset charset, byte value);
    public static ByteStream ofByte(String str, Charset charset;
    public static ByteStream ofByte(String str, String charsetName) throws UnsupportedEncodingException ;

    //**************** char ****************//
    public static CharStream ofChar(char c);
    public static CharStream ofChar(char... values);
    public static CharStream ofChar(String string;

    //**************** String ****************//

    public static CharSequenceStream ofString(String s);

    //**************** 转化 ****************//

    public CharStream mapToChar(ParseTo.ToChar<T> toChar);
    public ByteStream mapToByte(ParseTo.ToByte<T> toByte);
    public CharSequenceStream mapToCharSequence(Function<T, ? extends CharSequence> toString);
    public StringStream mapToString(Function<T, ? extends String> toString);
```



除了工厂方法，`ExStream`相对于原版`Stream`增加了一些整合性质的方法：

```java
//**************** 简化用的方法 ****************//
//*** 下面这4个用来循环打印控制台的 ***//
    public void forEachSysOut();
    public void forEachSysErr();
    public void forEachPrint(PrintStream printStream);
    public void forEachPrintln(PrintStream printStream);



/**
     * 转为list
     */
    public List<T> toList();
    /**
     * 转化为set
     */
    public Set<T> toSet();
    /**
     * 转化为Map
     */
    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> keyMapper,
                                  Function<? super T, ? extends V> valueMapper);
    /**
     * 转化为Map
     */
    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> keyMapper,
                                  Function<? super T, ? extends V> valueMapper,
                                  BinaryOperator<V> mergeFunction);
    /**
     * 转化为Map，如果出现键冲突则直接使用原版Stream的异常方法
     */
    public <K, V, M extends Map<K, V>> Map<K, V> toMap(Function<? super T, ? extends K> keyMapper,
                                                       Function<? super T, ? extends V> valueMapper,
                                                       Supplier<M> mapSupplier);
    /**
     * 转化为Map
     */
    public <K, U, M extends Map<K, U>>
    M toMap(Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends U> valueMapper,
            BinaryOperator<U> mergeFunction,
            Supplier<M> mapSupplier);
    /**
     * 转化为MapStream
     */
    public <K, V> ExMapStream<K, V> maptoMap(Function<? super T, ? extends K> keyMapper,
                                                Function<? super T, ? extends V> valueMapper);
    /**
     * 转化为MapStream
     */
    public <K, V> ExMapStream<K, V> maptoMap(Function<? super T, ? extends K> keyMapper,
                                                Function<? super T, ? extends V> valueMapper,
                                                BinaryOperator<V> mergeFunction);
    /**
     * 转化为MapStream，如果出现键冲突则直接使用原版Stream的异常方法
     */
    public <K, V, M extends Map<K, V>> ExMapStream<K, V> maptoMap(Function<? super T, ? extends K> keyMapper,
                                                                     Function<? super T, ? extends V> valueMapper,
                                                                     Supplier<M> mapSupplier);
    /**
     * 转化为MapStream
     */
    public <K, U, M extends Map<K, U>>
    ExMapStream<K, U> maptoMap(Function<? super T, ? extends K> keyMapper,
                                  Function<? super T, ? extends U> valueMapper,
                                  BinaryOperator<U> mergeFunction,
                                  Supplier<M> mapSupplier);

    /**
     * 转化后toList
     */
    public <R> List<R> toList(Function<T, R> mapper);
    /**
     * 转化后排序后toList
     */
    public <R> List<R> toListSorted(Function<T, R> mapper);
    /**
     * 排序后转化后tolist
     */
    public <R> List<R> sortedToList(Function<T, R> mapper);
    /**
     * 转化后排序后toList
     */
    public <R> List<R> toListSorted(Function<T, R> mapper, Comparator<R> comparator);
    /**
     * 排序后转化后tolist
     */
    public <R> List<R> sortedToList(Function<T, R> mapper, Comparator<T> comparator);
    /**
     * joining
     */
    public String joining(Function<T, String> mapper);
    /**
     * joining
     */
    public String joining(Function<T, String> mapper, CharSequence delimiter);
    /**
     * joining
     */
    public String joining(Function<T, String> mapper,
                          CharSequence delimiter,
                          CharSequence prefix,
                          CharSequence suffix);
    /**
     * groupBy
     */
    public <K> Map<? extends K, List<T>> groupBy(Function<? super T, ? extends K> classifier);
    /**
     * groupBy
     */
    public <K, A, D> Map<? extends K, D> groupBy(Function<? super T, ? extends K> classifier,
                                                 Collector<? super T, A, D> downstream);
    /**
     * groupBy
     */
    public <K, A, D, M extends Map<K, D>> M groupBy(Function<? super T, ? extends K> classifier,
                                                    Supplier<M> mapFactory,
                                                    Collector<? super T, A, D> downstream) ;
    /**
     * groupByConcurrent
     */
    public <K> Map<? extends K, List<T>> groupByConcurrent(Function<? super T, ? extends K> classifier);
    /**
     * groupByConcurrent
     */
    public <K, A, D> Map<? extends K, D> groupByConcurrent(Function<? super T, ? extends K> classifier,
                                                           Collector<? super T, A, D> downstream);
    /**
     * groupByConcurrent
     */
    public <K, A, D, M extends ConcurrentMap<K, D>> M groupByConcurrent(Function<? super T, ? extends K> classifier,
                                                                        Supplier<M> mapFactory,
                                                                        Collector<? super T, A, D> downstream);
    /**
     * concat
     */
    public ExStream<T> concat(Stream<T> concat);
    public ExStream<T> concat(T t);
    public ExStream<T> concat(T... t);
    public ExStream<T> concat(Collection<T> collection);

```



基本上也就是省略了中间的一步`collect(...)`而已。

除了`ExStream`中提供的这些额外的整合方法以外，还有一些再细一层的实现类：

```
ByteStream
CharSequenceStream
CharStream
ExMapStream
FileStream
StringStream
```



这些类中都或多或少的提供了一些针对某种特定类型的Stream的简化操作，例如`StringStream`中有trim() 方法进行去空等等，这里就不赘述了。



### 线程工具

> `com.forte.utils.thread`





#### BaseLocalThreadPool 线程池工厂

> `util class`



这个线程池工具类也是我在上学的时候，那时候看到了线程池这个东西，并仿照网上的教程写出了一个简单的线程池之后，萌生了写一个线程池工厂的想法。现在看看可能有些不足之处，不过功能依旧是可以实现的。



线程池工厂所使用的线程池框架是Java中自带的。

```java
/**
     * 创建线程池的工厂,无名称，使用默认
     * @return
     */
    public static Executor getThreadPool();

    /**
     * 创建线程池的工厂
     *
     * @param poolName 创建的线程池的名称
     * @return
     */
    public static Executor getThreadPool(String poolName);

    /**
     * 创建线程池的工厂,无名称，使用默认
     *
     * @return
     */
    public static Executor getThreadPool(PoolConfig poolConfig);

    /**
     * 创建线程池的工厂
     *
     * @param poolName 创建的线程池的名称
     * @return
     */
    public static Executor getThreadPool(String poolName, PoolConfig poolConfig);

    /**
     * 清除某指定的线程池
     *
     * @param poolName
     * @return
     */
    public static boolean removeThreadPool(String poolName);

    /**
     * 获取本线程中的线程池
     *
     * @return
     */
    public static Executor getLocalThreadPool();

    /**
     * 清除本线程中的线程池
     */
    public static boolean removeLocalThreadPool();

    /**
     * 获取线程工厂
     *
     * @return
     */
    public static ThreadFactory getFactory();


```



其中，`PoolConfig` 是一个对线程池创建所需要的参数的整合封装类。

```java
/**
     * 配置类，提供几个参数
     */
    public static class PoolConfig{
        /**
         * 核心池的大小
         */
        private int corePoolSize = 0;

        /**
         * 线程池最大线程数，这个参数也是一个非常重要的参数，它表示在线程池中最多能创建多少个线程；
         */
        private int maximumPoolSize = 500;

        /**
         * 表示线程没有任务执行时最多保持多久时间会终止。
         * 默认情况下，只有当线程池中的线程数大于corePoolSize时，keepAliveTime才会起作用，
         * 直到线程池中的线程数不大于corePoolSize，即当线程池中的线程数大于corePoolSize时，
         * 如果一个线程空闲的时间达到keepAliveTime，则会终止，直到线程池中的线程数不超过corePoolSize。
         * 但是如果调用了allowCoreThreadTimeOut(boolean)方法，在线程池中的线程数不大于corePoolSize时，keepAliveTime参数也会起作用，
         * 直到线程池中的线程数为0；
         */
        private long keepAliveTime = 5;

        /**
         * unit：参数keepAliveTime的时间单位，有7种取值，在TimeUnit类中有7种静态属性:
         * TimeUnit.DAYS;              //天
         * TimeUnit.HOURS;             //小时
         * TimeUnit.MINUTES;           //分钟
         * TimeUnit.SECONDS;           //秒
         * TimeUnit.MILLISECONDS;      //毫秒
         * TimeUnit.MICROSECONDS;      //微妙
         * TimeUnit.NANOSECONDS;       //纳秒
         */
        private TimeUnit timeUnit = TimeUnit.MILLISECONDS;

        /**
         * 一个阻塞队列，用来存储等待执行的任务，这个参数的选择也很重要，
         * 会对线程池的运行过程产生重大影响，一般来说，这里的阻塞队列有以下几种选择：
         * ArrayBlockingQueue;
         * LinkedBlockingQueue;
         * SynchronousQueue;
         * ArrayBlockingQueue和PriorityBlockingQueue使用较少，一般使用LinkedBlockingQueue和Synchronous。
         * 线程池的排队策略与BlockingQueue有关。
         */
        private BlockingQueue<Runnable> workQueue = new SynchronousQueue<>();

        /**
         * 线程工厂，主要用来创建线程；
         */
        private ThreadFactory defaultThreadFactory = Thread::new;

        //**************** 构造 & setter & getter ****************//

        public PoolConfig() {
        }
        
        public PoolConfig(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit timeUnit, BlockingQueue<Runnable> workQueue, ThreadFactory defaultThreadFactory) {
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
            this.timeUnit = timeUnit;
            this.workQueue = workQueue;
            this.defaultThreadFactory = defaultThreadFactory;
        }

       // getter & setter ....
    }

```









#### DelayUtil 、 ParallelUtil 延迟、循环、同步线程方法

> `util package`



这玩意儿也是我上学的时候写的..... 我发现我上学的时候是真的闲。



DelayUtil 中，提供了一系列方法来创建一个延迟一段时间之后执行的方法或者每隔一段时间就执行一次的方法。内部整合上面提到的线程池工厂。

```java
/**
	 * 循环执行方法
	 * 
	 * @param clazz
	 *            执行方法的class对象
	 * @param method
	 *            要执行的方法
	 * @param m
	 *            循环间隔
	 * @param args
	 *            方法参数
	 * @return ForteTimeBean对象，用于终止
	 */
	public static <T> DelayBean<T> interval(Class<T> clazz, String method, long m, Object... args);

	/**
	 * 循环执行方法 - 函数接口
	 * 
	 * @param f
	 *            函数式接口，在当需要循环调用一些并非特定对象的方法的时候可以使用此函数式接口
	 * @param m
	 *            延时时长
	 * @param args
	 *            方法参数，若没有则不填
	 * @return 延时对象，用于结束延时
	 */
	public static DelayBean interval(DelayFunc f, long m, Object... args);

	/**
	 * 设置方法延迟执行
	 * 
	 * @param object
	 *            执行方法的对象
	 * @param method
	 *            要执行的方法
	 * @param m
	 *            循环间隔
	 * @param args
	 *            方法参数
	 * @return ForteTimeBean对象，用于终止
	 */
	public static <T> DelayBean<T> timeout(Class<T> object, String method, long m, Object... args);

	/**
	 * 循环执行方法 - 函数接口
	 * 
	 * @param f
	 *            函数式接口，在当需要循环调用一些并非特定对象的方法的时候可以使用此函数式接口
	 * @param m
	 *            延时时长
	 * @param args
	 *            方法参数，若没有则不填
	 * @return 延时对象，用于结束延时
	 */
	@SafeVarargs
	public static DelayBean timeout(DelayFunc f, long m, Object... args);

	/**
	 * 移除延时执行
	 * 
	 * @param timeBean
	 *            ForteTimeBean对象
	 */
	public static void stop(DelayBean timeBean);

	/**
	 * 移除延时执行 - 延时millis后执行
	 * 
	 * @param TimeBean
	 * @param millis
	 */
	public static void stop(DelayBean TimeBean, long millis);

	/**
	 * 清除全部任务
	 */
	public static void clear();
```





ParallelUtil 中提供了一些方法以提供并行方法，即一个大任务中，只有当所有的任务执行完成后才会继续的任务。每个任务都是一个线程。

不过在实际应用中发现，似乎存在bug。不过，如果任务量比较少的话应该还是没问题的。当然，其实相对于用这个方法，还是首先推荐使用Stream流的并行流来的保险。

```java

    /**
     * 创建一个同步任务
     * @param taskName
     * 任务名称
     * @param singleListener
     * 任务单独的监听器
     * @param fs
     * 任务函数列表
     * @return
     */
    public static Parallel parallel(String taskName , ParallelSingleListener singleListener , ParallelTaskFunc[] fs);

    /**
     * 创建一个同步任务
     * @param taskName
     * 任务名称
     * @param fs
     * 任务函数列表
     * @return
     */
    public static Parallel parallel(String taskName , ParallelTaskFunc[] fs);

    /**
     * 创建一个同步任务
     * @param singleListener
     * 此任务单独的监听器
     * @param fs
     * 任务函数列表
     * @return
     */
    public static Parallel parallel(ParallelSingleListener singleListener , ParallelTaskFunc[] fs);

    /**
     * 创建一个同步任务
     * @param fs
     * 任务函数列表
     * @return
     */
    public static Parallel parallel(ParallelTaskFunc[] fs);


    /**
     * 获取一个多个同步任务同时进行的同步任务
     * @param taskName
     * 任务名称
     * @param singleListener
     * 同步任务单独监听器
     * @param parallels
     * 同步任务列表
     * @return
     */
    public static Parallel parallelList(String taskName , ParallelSingleListener singleListener , Parallel... parallels);

    /**
     * 获取一个多个同步任务同时进行的同步任务
     * @param singleListener
     * 同步任务单独监听器
     * @param parallels
     * 同步任务列表
     * @return
     */
    public static Parallel parallelList(ParallelSingleListener singleListener , Parallel... parallels);

    /**
     * 获取一个多个同步任务同时进行的同步任务
     * @param taskName
     * 任务名称
     * @param parallels
     * 同步任务列表
     * @return
     */
    public static Parallel parallelList(String taskName , Parallel... parallels);

    /**
     * 获取一个多个同步任务同时进行的同步任务
     * @param parallels
     * 同步任务列表
     * @return
     */
    public static Parallel parallelList(Parallel... parallels);




    /**
     * 设置全局监听器
     * @param listener
     * 全局监听器的实现对象
     */
    public static void setGlobalListener(ParallelGlobalListener listener);

    /**
     * 设置全局监听器
     * @param listenerClass
     * 全局监听器的class对象
     */
    public static void setGlobalListener(Class<? extends ParallelGlobalListener> listenerClass);
```







### 时间工具

> `com.forte.utils.time`



#### DateDifferenceUtils 时间差工具

> `util class`



这个是当初从网上嫖的代码自己改的..是计算两个时间中间相差的天、小时、分钟等等的信息。

```java
 /**
     * 获取两个时间之间相差的各个时间类型
     *
     * @param start  开始时间
     * @param end    结束时间
     * @param format 格式化方法
     * @return
     */
    public static DateDiff dateDiff(String start, String end, String format);

/**
     * 获取两个时间之间相差的各个时间类型
     *
     * @param start
     * @param end
     * @return
     */
    public static DateDiff dateDiff(Date start, Date end) ;
```



其中，DateDiff是返回值的封装类，其实现如下：

```java
/**
     * 时间差的封装类
     */
    public static class DateDiff {
        private long difference;
        private long day;
        private long hour;
        private long minute;
        private long second;
        private long millisecond;
        
        // getter & setter 
        
    }
```





#### SimpleDateUtils 简易时间工具类

> `com.forte.utils.time`



这个就不一样了，这是我实习的时候参加的那个项目一边工作一边根据一些烦人的需求写的。然后就顺手收录进来了。

```java
/**
     * 获取向前推n个月的每个月的月初日期
     * 返回值为yyyy-MM-dd
     * 例如：假如今天是2019-4-30，则lastMonthByDay(3,true)返回值就是：
     * [
     * 2019-04-01,
     * 2019-03-01,
     * 2019-02-01
     * ]
     * @param nums 向前推移多少个月
     * @param fromNow 是从今天开始还是上个月开始
     */
   public static List<LocalDate> beforeMonthByDay(int nums, boolean fromNow);

    /**
     * 获取向后推n个月的每个月的月初日期
     * 返回值为yyyy-MM-dd
     * 例如：假如今天是2019-4-30，则lastMonthByDay(3,true)返回值就是：
     * [
     * 2019-04-01,
     * 2019-03-01,
     * 2019-02-01
     * ]
     * @param nums 向前推移多少个月
     * @param fromNow 是从今天开始还是上个月开始
     */
   public static List<String> beforeMonthByDayToString(int nums, boolean fromNow);

    /**
     * 获取向前推n个月的每个月的月初日期
     * 返回值为yyyy-MM
     * 例如：假如今天是2019-4-30，则lastMonthByMonth(3,true)返回值就是：
     * [
     * 2019-04,
     * 2019-03,
     * 2019-02
     * ]
     * @param nums 向前推移多少个月
     * @param fromNow 是从今天开始还是上个月开始
     */
    public static List<YearMonth> beforeMonthByMonth(int nums, boolean fromNow);

    /**
     * 获取向后推n个月的每个月的月初日期
     * 返回值为yyyy-MM
     * 例如：假如今天是2019-4-30，则lastMonthByMonth(3,true)返回值就是：
     * [
     * 2019-04,
     * 2019-03,
     * 2019-02
     * ]
     * @param nums 向前推移多少个月
     * @param fromNow 是从今天开始还是上个月开始
     */
    public static List<String> beforeMonthByMonthToString(int nums, boolean fromNow);


    /**
     * 向前推n天的数组
     * 例如，今天是2019-01-10,beforeDays(3, false)
     * 就是
     * [2019-01-09, 2019-01-08, 2019-01-07]
     * @param days      推几天
     * @param fromNow   是否包括今天
     * @return
     */
    public static List<LocalDate> beforeDays(int days, boolean fromNow);

    /**
     * 向前推n天的数组
     * 例如，今天是2019-01-10,beforeDays(3, false)
     * 就是
     * [2019-01-09, 2019-01-08, 2019-01-07]
     * @param days      推几天
     * @param fromNow   是否包括今天
     * @return
     */
    public static List<String> beforeDaysToString(int days, boolean fromNow);

    /**
     * 向前推n天的数组
     * 例如，今天是2019-01-10,beforeDays(3, false)
     * 就是
     * [2019-01-09, 2019-01-08, 2019-01-07]
     *
     * beforeDaysToString(1, false, String[]::new)
     * @param days      推几天
     * @param fromNow   是否包括今天
     */
    public static String[] beforeDaysToString(int days, boolean fromNow, IntFunction<String[]> generator);

    /**
     * 向前推n年，类似于近5年、近6年这样子
     * @param years
     * @param fromNow
     * @return
     */
    public static List<Year> beforeYears(int years, boolean fromNow);

    /**
     * 向前推n年，类似于近5年、近6年这样子
     */
    public static List<String> beforeYearsToString(int years, boolean fromNow);

    /**
     * 将DayOfWeek转化为中文的星期字符串
     */
    public static String toChineseWeekDay(DayOfWeek dayOfWeek);

    /**
     * 将DayOfWeek转化为中文的星期字符串
     */
    public static String toChineseWeekDay();

    /**
     * 复制一个{@link #DAY_OF_WEEK_CHINESE_MAP}字段
     */
    public static Map<Integer, String> getDayOfWeekChineseMap();


    /**
     * 获取星期对应中文字符串数组
     */
    public static String[] getDayOfWeekChineseStr();

    /** 获取某年所有月份 -yyyy-MM */
    public static List<YearMonth> getAllYearMonth(Year year);

    /** 获取某年所有月份 - yyyy-MM */
    public static List<YearMonth> getAllYearMonth(String yearStr);

    /** 获取某年所有月份 - yyyy-MM */
    public static List<String> getAllYearMonthToString(Year year);

    /** 获取某年所有月份 - yyyy-MM */
    public static List<String> getAllYearMonthToString(String yearStr);

    /** 获取所有月份数组，01-12 */
    public static String[] getAllMonth();

    /** 获取所有月份数组，1-12，首部不补零 */
    public static String[] getAllMonthNo0();

    public static Year instantToYear(Instant instant);
```



#### Stopwatch 秒表

> `util class`



有时候，你想计算方法执行前后的用时，但是又嫌写两遍`System.currentTimeMillis()` 然后在算个减法太麻烦？这个工具类简单的给你封装了一下计时，且自动计算差值。并且计时的前后值在不同的线程中相互独立，(通过`ThreadLocal`控制)

```java
/**
     * 记录一个时间
     */
    public static long record();

/**
     * 获取上次计时时间
     */
    public static long getLastTime();

    /**
     * 获取上次计时时间并清除
     */
    public static long getLastTimeAndRemove();

    /**
     * 返回当前与上次的时间差<br>
     * 如果上次没有时间差，则根据当前计时后的计时时间做自定义处理
     */
    public static long difference(Function<Long, Long> orElse);

    /**
     * 返回当前与上次的时间差<br>
     * 如果没有计时，则默认返回-1
     */
    public static long difference();
    /**
     * 返回当前与上次的时间差<br>
     * 如果没有计时，则抛出异常
     */
    public static long differenceOrThrow() ;

```



一般使用的时候这么用就行了：

```java
		Stopwatch.difference();
        run();
        long d2 = Stopwatch.difference();

        // 这个d2 已经计算完了差值了
        System.out.println(d2);
```















## 结尾

没啥东西了，以上目前就是全部了。没有什么中心思想，没有什么核心内容，就是一个乱七八糟工具类大杂烩而已。

