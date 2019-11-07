package com.forte.utils.basis;

import java.lang.reflect.Array;
import java.util.function.Function;

/**
 *
 * 数组工具类
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ExArrayUtils {

    /**
     * 将一个数组转化为另外一个类型的数据，转化规则需要自定义
     * 此方法通过Array的方法进行创建而可以无视泛型等限制，仅需要指定数据类型即可
     * @param array     原数组
     * @param arrayType 新数组类型
     * @param caseTo    转化规则
     * @param <T>       原数据类型
     * @param <E>       新数据类型
     * @return
     */
    public static <T, E> T[] caseTo(E[] array, Class<T> arrayType, Function<E, T> caseTo){
        // 新建
        T[] newArray = newArray(arrayType, array.length);
        // 遍历
        for (int i = 0; i < array.length; i++) {
            newArray[i] = caseTo.apply(array[i]);
        }

        return newArray;
    }

    /**
     * 根据数据类型与长度新建一个数组对象
     * @param arrayType 数组的数据类型
     * @param length    长度
     */
    public static <T> T[] newArray(Class<T> arrayType, int length){
        return (T[]) Array.newInstance(arrayType, length);
    }

}
