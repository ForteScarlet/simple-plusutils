package com.forte.utils.function;

import java.util.function.IntConsumer;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@FunctionalInterface
public interface CharConsumer extends IntConsumer {


    /**
     * Performs this operation on the given argument.
     *
     * @param value the input argument
     */
    void accept(char value);

    /**
     * Performs this operation on the given argument.
     *
     * @param value the input argument
     */
    @Override
    default void accept(int value){
        accept((char) value);
    }



}
