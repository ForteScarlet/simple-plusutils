package com.forte.utils.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.IntFunction;

/**
 * 拓展迭代器
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ExArrayIterable<T> implements Iterable<T> {

    private T[] elements;

    @Override
    public Iterator<T> iterator() {
        return new ExArrayIteratorImpl<>(elements);
    }

    @SafeVarargs
    public ExArrayIterable(T... elements){
        this.elements = Arrays.copyOf(elements, elements.length);
    }

    public ExArrayIterable(Collection<T> elements, IntFunction<T[]> generator){
        this.elements = elements.stream().toArray(generator);
    }

    public static <T> ExArrayIterable<T> of(T... elements){
        return new ExArrayIterable<>(elements);
    }

    public static <T> ExArrayIterable<T> of(Collection<T> elements, IntFunction<T[]> generator){
        return new ExArrayIterable<>(elements, generator);
    }

    /**
     * 数组元素迭代器
     */
    public static class ExArrayIteratorImpl<T> implements Iterator<T> {

        /** 元素 */
        private T[] elements;
        /** 索引 */
        private int index = 0;

        @Override
        public boolean hasNext() {
            boolean b = index < elements.length;
            if(!b) {
                elements = Arrays.copyOf(elements, 0);
            }
            return b;
        }

        @Override
        public T next() {
            System.out.println(Arrays.toString(elements));
            System.out.println("\t>>\t" + index);
            if (hasNext()) {
                T element = elements[index];
                //移除元素
                elements[index++] = null;
                return element;
            }
            else {
                throw new NoSuchElementException();
            }
        }

        /** 构造，指定元素数组 */
        ExArrayIteratorImpl(T[] elements){
            this.elements = Arrays.copyOf(elements, elements.length);
        }

    }

}
