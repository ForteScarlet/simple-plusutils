package com.forte.utils.function;

import java.util.Objects;
import java.util.function.IntConsumer;

/**
 *
 * this like {@link java.util.function.IntConsumer} but parameter is byte
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@FunctionalInterface
public interface ByteConsumer extends IntConsumer {

    /**
     * Performs this operation on the given argument.
     * @param value the input argument
     */
    void accept(byte value);

    @Override
    default void accept(int value){
        accept((byte) value);
    }

    default void accept(Byte b){
        accept(b.byteValue());
    }

    /**
     * Returns a composed {@code IntConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code IntConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default ByteConsumer andThen(ByteConsumer after) {
        Objects.requireNonNull(after);
        return (byte t) -> { accept(t); after.accept(t); };
    }

    /**
     * Returns a composed {@code IntConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code IntConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    @Override
    default ByteConsumer andThen(IntConsumer after) {
        Objects.requireNonNull(after);
        return (byte t) -> { accept(t); after.accept(t); };
    }



}
