package com.forte.utils.stream;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 *
 * 流相关操作的工具类
 * 命名方式不同于Ex{$name}, 而是使用常见的{$name}s 的方式
 * 这个工具类将面向对于普通的流对象进行操作而非{@link ExStream}
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class Streams {

    /**
     * 获取{@link ExStream} 中保存的真正Stream对象
     * @param ex {@link ExStream} 的实例对象
     */
    public static <T, EX extends ExStream<T>> Stream<T> realStream(EX ex){
        return ex.getStream();
    }

    /**
     * 根据迭代器对象创建一个流对象
     * @param iter      迭代器
     * @param parallel  是否异步
     * @return  流对象
     */
    public static <T> Stream<T> iter(Iterator<T> iter, boolean parallel){
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                iter, Spliterator.ORDERED | Spliterator.NONNULL), parallel);
    }

    /**
     * 根据迭代器对象创建一个流对象, 默认非异步
     * @param iter  迭代器对象
     * @return  流对象
     */
    public static <T> Stream<T> iter(Iterator<T> iter){
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                iter, Spliterator.ORDERED | Spliterator.NONNULL), false);
    }

    /**
     * 获取toList的{@link Collector}
     */
    public static <T> Collector<T, ?, List<T>> toList(){
        return Collectors.toList();
    }

    /**
     * 获取toSet的{@link Collector}
     */
    public static <T> Collector<T, ?, Set<T>> toSet(){
        return Collectors.toSet();
    }

    /**
     * 当值为{@link Map.Entry} 的时候，获取一个toMap
     */
    public static <T extends Map.Entry<K, V>, K, V> Collector<T, ?, Map<K, V>> toMap(){
        return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
    }

    /**
     * 当值为{@link Map.Entry} 的时候，获取一个toMap
     * @param mergeFunction 键冲突函数
     */
    public static <T extends Map.Entry<K, V>, K, V> Collector<T, ?, Map<K, V>> toMap(BinaryOperator<V> mergeFunction){
        return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, mergeFunction);
    }

    /**
     * 当值为{@link Map.Entry} 的时候，获取一个toMap
     * @param mergeFunction 键冲突函数
     * @param mapSupplier   map实例获取器
     *
     */
    public static <T extends Map.Entry<K, V>, K, V> Collector<T, ?, Map<K, V>> toMap(BinaryOperator<V> mergeFunction,
                                                                                     Supplier<Map<K, V>> mapSupplier){
        return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, mergeFunction, mapSupplier);
    }

    /**
     * 当值为{@link Map.Entry} 的时候，获取一个toMap
     * @param mapSupplier map实例获取器
     */
    public static <T extends Map.Entry<K, V>, K, V> Collector<T, ?, Map<K, V>> toMap(Supplier<Map<K, V>> mapSupplier){
        return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, throwingMerger(), mapSupplier);
    }



    /**
     * 当值为{@link Map.Entry} 的时候，获取一个toConcurrentMap
     */
    public static <T extends Map.Entry<K, V>, K, V> Collector<T, ?, ConcurrentMap<K, V>> toConcurrentMap(){
        return Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue);
    }

    /**
     * 当值为{@link Map.Entry} 的时候，获取一个toConcurrentMap
     * @param mergeFunction 键冲突函数
     */
    public static <T extends Map.Entry<K, V>, K, V> Collector<T, ?, ConcurrentMap<K, V>> toConcurrentMap(BinaryOperator<V> mergeFunction){
        return Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue, mergeFunction);
    }

    /**
     * 当值为{@link Map.Entry} 的时候，获取一个toConcurrentMap
     * @param mergeFunction 键冲突函数
     * @param mapSupplier   map实例获取器
     *
     */
    public static <T extends Map.Entry<K, V>, K, V> Collector<T, ?, ConcurrentMap<K, V>> toConcurrentMap(BinaryOperator<V> mergeFunction,
                                                                                     Supplier<ConcurrentMap<K, V>> mapSupplier){
        return Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue, mergeFunction, mapSupplier);
    }

    /**
     * 当值为{@link Map.Entry} 的时候，获取一个toConcurrentMap
     * @param mapSupplier map实例获取器
     */
    public static <T extends Map.Entry<K, V>, K, V> Collector<T, ?, ConcurrentMap<K, V>> toConcurrentMap(Supplier<ConcurrentMap<K, V>> mapSupplier){
        return Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue, throwingMerger(), mapSupplier);
    }


    /**
     * 当键冲突的时候，直接抛出异常
     * @param <T>
     * @return
     */
    private static <T> BinaryOperator<T> throwingMerger() {
        return (u, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", u));
        };
    }

}
