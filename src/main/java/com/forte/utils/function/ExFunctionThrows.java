package com.forte.utils.function;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@FunctionalInterface
public interface ExFunctionThrows<P1, P2, P3, R> {

    R apply(P1 p1, P2 p2, P3 p3) throws Throwable;
}
