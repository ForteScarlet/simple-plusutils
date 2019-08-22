package com.forte.utils.stream;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ExMapStream<K, V> extends ExStream<Map.Entry<K, V>> {

    /**
     * 构造，以指定内部流对象
     *
     * @param stream
     */
    private ExMapStream(Stream<Map.Entry<K, V>> stream) {
        super(stream);
    }

    private <EB extends ExBaseStream<Map.Entry<K, V>, EB, Stream<Map.Entry<K, V>>>>
    ExMapStream(EB base){
        super(base.getStream());
    }


    public static <K, V> ExMapStream<K, V> ofStream(Stream<Map.Entry<K, V>> stream){
        if(stream instanceof ExMapStream){
            return (ExMapStream<K, V>) stream;
        }
        return new ExMapStream<>(stream);
    }

    public static <K, V> ExMapStream<K, V> ofStream(ExStream<Map.Entry<K, V>> stream){
        if(stream instanceof ExMapStream){
            return (ExMapStream<K, V>) stream;
        }
        return new ExMapStream<>(stream);
    }

    public static <K, V> ExMapStream<K, V> ofMap(Map<K, V> map){
        return ofStream(map.entrySet().stream());
    }

    public static <K, V> ExMapStream<K, V> ofCollection(Collection<Map.Entry<K, V>> collection){
        return ofStream(collection.stream());
    }

    public static <K, V> ExMapStream<K, V> ofArray(Map.Entry<K, V>[] array){
        return ofStream(Arrays.stream(array));
    }

    @Override
    public ExMapStream<K, V> concat(Stream<Map.Entry<K, V>> concat){
        return ofStream(Stream.concat(stream, concat));
    }


    public ExMapStream<K, V> concat(Map<K, V> map){
        return ofStream(Stream.concat(stream, map.entrySet().stream()));
    }

    @Override
    public ExMapStream<K, V> concat(Collection<Map.Entry<K, V>> collection){
        return concat(Stream.concat(stream, collection.stream()));
    }

    @Override
    public ExMapStream<K, V> concat(Map.Entry<K, V>[] entries){
        return concat(Stream.concat(stream, Arrays.stream(entries)));
    }


    /**
     * 转化为Map
     */
    public Map<K, V> toMap() {
        return toMap(defaultKeyMapper(), defaultValueMapper());
    }

    /**
     * 转化为Map
     */
    public <M extends Map<K, V>> Map<K, V> toMap(Supplier<M> mapSupplier){
        return toMap(defaultKeyMapper(), defaultValueMapper(), throwingMerger(), mapSupplier);
    }

    /**
     * 转化为Map
     */
    public <M extends Map<K, V>> Map<K, V> toMap(BinaryOperator<V> mergeFunction, Supplier<M> mapSupplier){
        return toMap(defaultKeyMapper(), defaultValueMapper(), mergeFunction, mapSupplier);
    }

    /**
     * 转化为顺序map
     */
    public Map<K, V> toLinkedMap(){
        return toMap(LinkedHashMap::new);
    }

    /**
     * 转化为顺序map
     */
    public Map<K, V> toLinkedMap(BinaryOperator<V> mergeFunction){
        return toMap(mergeFunction, LinkedHashMap::new);
    }


    private Function<? super Map.Entry<K, V>, ? extends K> defaultKeyMapper(){
        return Map.Entry::getKey;
    }


    private Function<? super Map.Entry<K, V>, ? extends V> defaultValueMapper(){
        return Map.Entry::getValue;
    }


    @Override
    public ExMapStream<K, V> filter(Predicate<? super Map.Entry<K, V>> predicate) {
        return ofStream(super.filter(predicate));
    }

    /**
     * 转化为ExMap
     */
    @Override
    public <C, U, E extends Map.Entry<C, U>> ExMapStream<C, U> mapToEntry(Function<? super Map.Entry<K, V>, ? extends E> mapper){
        return ofStream(super.map(mapper));
    }

    @Override
    public ExMapStream<K, V> distinct() {
        return ofStream(super.distinct());
    }

    @Override
    public ExMapStream<K, V> sorted() {
        return ofStream(super.sorted());
    }

    @Override
    public ExMapStream<K, V> sorted(Comparator<? super Map.Entry<K, V>> comparator) {
        return ofStream(super.sorted(comparator));
    }

    @Override
    public ExMapStream<K, V> peek(Consumer<? super Map.Entry<K, V>> action) {
        return ofStream(super.peek(action));
    }

    @Override
    public ExMapStream<K, V> limit(long maxSize) {
        return ofStream(super.limit(maxSize));
    }

    @Override
    public ExMapStream<K, V> skip(long n) {
        return ofStream(super.skip(n));
    }


    public void forEach(BiConsumer<K, V> consumer){
        super.forEach(e -> consumer.accept(e.getKey(), e.getValue()));
    }

    public void forEachOrdered(BiConsumer<K, V> consumer){
        super.forEachOrdered(e -> consumer.accept(e.getKey(), e.getValue()));
    }


}
