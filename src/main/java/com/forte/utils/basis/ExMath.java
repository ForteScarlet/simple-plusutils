package com.forte.utils.basis;

import java.math.BigInteger;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ExMath {


    /**
     * 计算某个数的阶乘,n!
     * @param n n!
     */
    public static long factorial(long n){
        return LongStream
                .iterate(n, i -> i - 1)
                .limit(n - 1)
                .reduce((o, i) -> o * i).orElse(-1);
    }

    /**
     * 计算某个数的阶乘,n! <br>
     * 使用bigInteger进行计算， 理论上可计算long范围内所有值的阶乘值
     * @param n n!
     */
    public static BigInteger factorialBig(BigInteger n){
        return Stream
                .iterate(n, i -> i.subtract(BigInteger.ONE))
                .limit(n.longValue())
                .reduce(BigInteger::multiply).orElse(null);
    }

    /**
     * 计算某个数的阶乘,n! <br>
     * 使用bigInteger进行计算
     * @param n n!
     */
    public static BigInteger factorialBig(long n){
        return Stream
                .iterate(BigInteger.valueOf(n), i -> i.subtract(BigInteger.ONE))
                .limit(n)
                .reduce(BigInteger::multiply).orElse(null);
    }

    /**
     * 计算某个数的阶乘,n!
     * @param n n!
     */
    public static long factorialParallel(long n){
        return LongStream
                .iterate(n, i -> i - 1)
                .limit(n - 1)
                .parallel()
                .reduce((o, i) -> o * i).orElse(-1);
    }

    /**
     * 计算某个数的阶乘,n! <br>
     * 使用bigInteger进行计算， 理论上可计算long范围内所有值的阶乘值
     * @param n n!
     */
    public static BigInteger factorialBigParallel(BigInteger n){
        return Stream
                .iterate(n, i -> i.subtract(BigInteger.ONE))
                .limit(n.longValue())
                .parallel()
                .reduce(BigInteger::multiply).orElse(null);
    }

    /**
     * 计算某个数的阶乘,n! <br>
     * 使用bigInteger进行计算
     * @param n n!
     */
    public static BigInteger factorialBigParallel(long n){
        return Stream
                .iterate(BigInteger.valueOf(n), i -> i.subtract(BigInteger.ONE))
                .limit(n)
                .parallel()
                .reduce(BigInteger::multiply).orElse(null);
    }

}
