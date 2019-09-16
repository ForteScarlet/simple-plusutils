package com.forte.utils.collections;

import java.util.Iterator;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 *
 * 简易迭代器实现
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ExSimpleIterator<T> implements Iterator<T> {

    private final BooleanSupplier hasNextSup;
    private final Supplier<T> nextSup;

    @Override
    public boolean hasNext() {
        return hasNextSup.getAsBoolean();
    }

    @Override
    public T next() {
        return nextSup.get();
    }

    public ExSimpleIterator(BooleanSupplier hasNext, Supplier<T> next){
        this.hasNextSup = hasNext;
        this.nextSup = next;
    }

}
