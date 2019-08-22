package com.forte.utils.function;

import java.util.function.IntFunction;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@FunctionalInterface
public interface ByteFunction<R> extends IntFunction<R> {


    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    R apply(byte value);

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    @Override
    default R apply(int value){
        return apply((byte) value);
    }


}
