package com.forte.utils.basis;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.IntFunction;

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
     * 复制一个数组
     * @param src 原数组
     * @return 复制的数组
     */
    public static <T> T[] fullCopy(T[] src){
        return copy(src, 0, 0, src.length);
    }

    /**
     * 从原数组的某个索引开始拷贝
     * @param src       原数组
     * @param srcPos    起始索引
     */
    public static <T> T[] copyFrom(T[] src, int srcPos){
        return copy(src, srcPos, 0, src.length - srcPos);
    }

    /**
     * 从原数组的某个索引开始拷贝，拷贝制定长度
     * @param src       原数组
     * @param srcPos    起始索引
     * @param length    长度
     */
    public static <T> T[] copyRange(T[] src, int srcPos, int length){
        if(srcPos + length > src.length){
             throw new IndexOutOfBoundsException("out index: " + (srcPos + length) + ", but array length: " + (src.length));
        }
        return copy(src, srcPos, 0, length);
    }


    /**
     * 复制一个数组
     * @param src 原数组
     * @return 复制的数组
     */
    public static <T> T[] copy(T[] src, int srcPos, int destPos, int newLength){
        Class<T> type = (Class<T>) src.getClass().getComponentType();
        T[] newArray = newArray(type, newLength);
        System.arraycopy(src, srcPos, newArray, destPos, newLength);
        return newArray;
    }


    /**
     * 获取一个函数，函数代表：接受一个长度参数，返回一个对应类型的数组。
     * @param type 数组类型
     */
    public static <T> IntFunction<T[]> toArrayFunction(Class<T> type){
        return l -> newArray(type, l);
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
