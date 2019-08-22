package com.forte.utils.function;

/**
 * this is a functionInterface like {@link java.util.function.Consumer} but there are 3 parameters
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@FunctionalInterface
public interface ExConsumer<A1, A2, A3> {

    void accept(A1 a1, A2 a2, A3 a3);
}
