package com.forte.utils.collections;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Collections扩展
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ExCollections {

    /**
     * 通过流复制一个对象
     * @param collection    collection对象
     * @param collectionFactory collection工厂
     */
    public static <E, C extends Collection<E>> Collection<E> copy(Collection<E> collection, Supplier<C> collectionFactory){
        return collection.stream().collect(Collectors.toCollection(collectionFactory));
    }

    /**
     * 转为List集合
     * @param collection collection
     */
    public static <E> List<E> toList(Collection<E> collection){
        return ExStream.of(collection).toList();
    }

    /**
     * 转为Set
     * @param collection collection
     */
    public static <E> Set<E> toSet(Collection<E> collection){
        return ExStream.of(collection).toSet();
    }

    /**
     * 为Map排序
     */
    public static <K extends Comparable<K>, V> Map<K, V> mapKeySorted(Map<K, V> map){
        return ExStream.of(map).sorted(Comparator.comparing(Map.Entry::getKey))
                .toLinkedMap();
    }

    /**
     * 为Map排序
     */
    public static <K, V extends Comparable<V>> Map<K, V> mapValueSorted(Map<K, V> map){
        return ExStream.of(map).sorted(Comparator.comparing(Map.Entry::getValue))
                .toLinkedMap();
    }
    /**
     * 为Map排序
     */
    public static <K, V> Map<K, V> mapKeySorted(Map<K, V> map, Comparator<K> keyCompared){
        return ExStream.of(map).sorted((e1, e2) -> keyCompared.compare(e1.getKey(), e2.getKey()))
                .toLinkedMap();
    }

    /**
     * 为Map排序
     */
    public static <K, V> Map<K, V> mapValueSorted(Map<K, V> map, Comparator<V> valueCompared){
        return ExStream.of(map).sorted((e1, e2) -> valueCompared.compare(e1.getValue(), e2.getValue()))
                .toLinkedMap();
    }

}
