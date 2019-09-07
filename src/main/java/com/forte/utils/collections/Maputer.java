package com.forte.utils.collections;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Map的操作工具类
 * 使用函数接口，在某先情况下可以极大的缩减代码量
 * 不保证线程安全 x
 * 现在已经增加 synchronized 相关方法，尝试实现线程安全
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/1 15:16
 * @since JDK1.8
 **/
public class Maputer {

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
    public static <K, V> V put(Map<K, V> map, K key, Function<V, V> ifExist, Supplier<V> ifNull){
        V value = map.get(key);
        if(value != null){
            //如果能获取到，参数传入并获取一个放入值
            return map.put(key, ifExist.apply(value));
        }else{
            //如果没有，获取参数
            return map.put(key, ifNull.get());
        }
    }

    /**
     * 添加了 synchronized 关键字的{@link #put} 方法
     * @param map       操作的map
     * @param key       操作的key
     * @param ifExist   如果值存在的函数
     * @param ifNull    如果值为null的函数
     */
    public synchronized static <K, V> void putSynchronized(Map<K, V> map, K key, Function<V, V> ifExist, Supplier<V> ifNull){
        put(map, key, ifExist, ifNull);
    }

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
    public static <K, V> void peek(Map<K, V> map, K key, Consumer<V> ifExist, Supplier<V> ifNull){
        V value = map.get(key);
        if(value != null){
            //如果能获取到，操作存在的参数
            ifExist.accept(value);
        }else{
            //如果没有，获取参数
            map.put(key, ifNull.get());
        }
    }

    /**
     * 添加了 synchronized 关键字的{@link #put} 方法
     */
    public synchronized static <K, V> void peekSynchronized(Map<K, V> map, K key, Consumer<V> ifExist, Supplier<V> ifNull){
        peek(map, key, ifExist, ifNull);
    }

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
    public static <E, K, V extends Collection<E>> void addAll(Map<K, V> map, K key, V collections){
        peek(map, key, l -> l.addAll(collections), () -> collections);
    }

    /**
     * 线程安全的{@link #addAll} 方法
     */
    public static <E, K, V extends Collection<E>> void addAllSynchronized(Map<K, V> map, K key, V collections){
        peekSynchronized(map, key, l -> l.addAll(collections), () -> collections);
    }

    /**
     * 给定一个value值为Colletion<E>集合类型的map集合，给定一个E类型的参数
     * 如果value值存在，添加，如果不存在则使用给定的参数填充
     * @param map   map集合
     * @param key   键
     * @param one   要保存或要添加的
     */
    public static <E, K, V extends Collection<E>> void add(Map<K, V> map, K key, E one, V ifNull){
        peek(map, key, l -> l.add(one), () -> ifNull);
    }

    /**
     * 线程安全的{@link #add} 方法
     */
    public static <E, K, V extends Collection<E>> void addSynchronized(Map<K, V> map, K key, E one, V ifNull){
        peekSynchronized(map, key, l -> l.add(one), () -> ifNull);
    }


    /**
     * 给定一个value值为Colletion<E>集合类型的map集合，给定一个E类型的参数
     * 如果value值存在，添加，如果不存在则使用给定的方法获取值填充
     * @param map   map集合
     * @param key   键
     * @param one   要保存或要添加的
     */
    public static <E, K, V extends Collection<E>> void add(Map<K, V> map, K key, E one, Supplier<V> ifNull){
        peek(map, key, l -> l.add(one), ifNull);
    }

    /**
     * 线程安全的{@link #add(Map, Object, Object, Supplier)} 方法
     */
    public static <E, K, V extends Collection<E>> void addSynchronized(Map<K, V> map, K key, E one, Supplier<V> ifNull){
        peekSynchronized(map, key, l -> l.add(one), ifNull);
    }



}
