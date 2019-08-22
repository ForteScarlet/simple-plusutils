package com.forte.utils.stream;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.BaseStream;

/**
 * 抽象类
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 * @param <T> 数据类型
 * @param <S> BaseExStream类型
 * @param <R> 真正的Stream类型
 **/
public interface ExBaseStream<T, S extends ExBaseStream<T, S, R>, R extends BaseStream<T, R>> extends BaseStream<T, S> {

    /** 获取真实的stream */
    R getStream();
    /** 通过真实的stream转化为ExStream */

    S toEx(R r);

    /**
     * Returns an iterator for the elements of this stream.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">terminal
     * operation</a>.
     *
     * @return the element iterator for this stream
     */
    @Override
    default Iterator<T> iterator() {
        return getStream().iterator();
    }

    /**
     * Returns a spliterator for the elements of this stream.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">terminal
     * operation</a>.
     *
     * @return the element spliterator for this stream
     */
    @Override
    default Spliterator<T> spliterator() {
        return getStream().spliterator();
    }

    /**
     * Returns whether this stream, if a terminal operation were to be executed,
     * would execute in parallel.  Calling this method after invoking an
     * terminal stream operation method may yield unpredictable results.
     *
     * @return {@code true} if this stream would execute in parallel if executed
     */
    @Override
    default boolean isParallel() {
        return getStream().isParallel();
    }

    /**
     * Returns an equivalent stream that is sequential.  May return
     * itself, either because the stream was already sequential, or because
     * the underlying stream state was modified to be sequential.
     *
     * <p>This is an <a href="package-summary.html#StreamOps">intermediate
     * operation</a>.
     *
     * @return a sequential stream
     */
    @Override
    default S sequential() {
        return toEx(getStream().sequential());
    }

    /**
     * Returns an equivalent stream that is parallel.  May return
     * itself, either because the stream was already parallel, or because
     * the underlying stream state was modified to be parallel.
     *
     * <p>This is an <a href="package-summary.html#StreamOps">intermediate
     * operation</a>.
     *
     * @return a parallel stream
     */
    @Override
    default S parallel() {
        return toEx(getStream().parallel());
    }

    /**
     * Returns an equivalent stream that is
     * <a href="package-summary.html#Ordering">unordered</a>.  May return
     * itself, either because the stream was already unordered, or because
     * the underlying stream state was modified to be unordered.
     *
     * <p>This is an <a href="package-summary.html#StreamOps">intermediate
     * operation</a>.
     *
     * @return an unordered stream
     */
    @Override
    default S unordered() {
        return toEx(getStream().unordered());
    }

    /**
     * Returns an equivalent stream with an additional close handler.  Close
     * handlers are run when the {@link #close()} method
     * is called on the stream, and are executed in the order they were
     * added.  All close handlers are run, even if earlier close handlers throw
     * exceptions.  If any close handler throws an exception, the first
     * exception thrown will be relayed to the caller of {@code close()}, with
     * any remaining exceptions added to that exception as suppressed exceptions
     * (unless one of the remaining exceptions is the same exception as the
     * first exception, since an exception cannot suppress itself.)  May
     * return itself.
     *
     * <p>This is an <a href="package-summary.html#StreamOps">intermediate
     * operation</a>.
     *
     * @param closeHandler A task to execute when the stream is closed
     * @return a stream with a handler that is run if the stream is closed
     */
    @Override
    default S onClose(Runnable closeHandler) {
        return toEx(getStream().onClose(closeHandler));
    }

    /**
     * Closes this stream, causing all close handlers for this stream pipeline
     * to be called.
     *
     * @see AutoCloseable#close()
     */
    @Override
    default void close() {
        getStream().close();
    }
}
