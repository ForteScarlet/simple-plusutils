# simple-plusutils 使用手册

## 前言

这个工具包合集本身是早期我自己留着自用的来着...不过乱七八糟的想法越来越多，于是乎就有了这么一个大合集了。讲道理里面实用性的东西确实不少，于是开始写个文档试试。

首先，文档会对我认为 **值得一提** 的工具类优先进行介绍，而其他我认为可能并不是那么出彩的工具类则会介绍的比较简略。但是根据个人习惯，javadoc文档我会标注的比较全面，任何类的细节均可以查询javadoc文档或者通过下载源码查看。

东西时间跨度还挺大的，有好几年前写的，有最近才想到的，可能有些东西技术层面明显不足，还望谅解。



然后介绍顺序的话就是项目里从上到下的包结构。

如果我个人觉得某个工具包值得介绍，我会标注一个  **`*`** 

最后，我的介绍可能会有点散漫、随意，毕竟我也不知道该怎么正儿八经的介绍一个什么东西都有的工具箱项目，所以干脆就用半聊天的方式写文档了。







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





（施工中。



