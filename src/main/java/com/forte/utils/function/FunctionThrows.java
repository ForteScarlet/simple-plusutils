package com.forte.utils.function;

/**
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@FunctionalInterface
public interface FunctionThrows<T, R> {

    R apply(T t) throws Throwable;

}
