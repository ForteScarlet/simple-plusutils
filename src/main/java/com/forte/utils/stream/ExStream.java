package com.forte.utils.stream;

import com.forte.utils.function.ParseTo;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.function.*;
import java.util.stream.*;

/**
 * 流拓展, 内部存在一个stream对象，此类基本上仅作为简化部分繁琐的步骤
 * 如果想操作原生Stream对象，可以尝试一下
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ExStream<T> implements Stream<T> {

    /**
     * 内部流对象
     */
    protected final Stream<T> stream;


    @Override
    public String toString(){
        return ExStream.class.getName() + "@" + Integer.toHexString(hashCode());
    }


    /**
     * 构造，以指定内部流对象
     */
    ExStream(Stream<T> stream) {
        this.stream = stream;
    }

    /**
     * 获取真实的stream
     */
    protected Stream<T> getStream() {
        return stream;
    }


    protected <R> ExStream<R> toEx(Stream<R> stream) {
        return of(stream);
    }


    //**************** 工厂方法 ****************//


    /**
     * 一个基于迭代器的流对象
     * @param iter
     * @param <T>
     * @return
     */
    public static <T> ExStream<T> byIter(Iterator<T> iter, boolean parallel){
        Stream<T> stream = StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(iter, Spliterator.ORDERED | Spliterator.NONNULL),
                parallel
        );
        return ExStream.of(stream);
    };

    /**
     * 基于迭代器的流对象，并使用ExStream封装
     */
    public static <T> ExStream<T> byIter(Iterator<T> iter){
        return byIter(iter, false);
    }


    //**************** 普通的工厂方法 ****************//


    public static <T> ExStream<T> of(T t) {
        return new ExStream<>(Stream.of(t));
    }

    public static <T> ExStream<T> of(T... ts) {
        return new ExStream<>(Stream.of(ts));
    }

    public static <T> ExStream<T> of(Stream<T> stream) {
        if (stream instanceof ExStream) {
            return (ExStream<T>) stream;

        }
        return new ExStream<>(stream);
    }

    public static <T> ExStream<T> of(Collection<T> collection) {
        return new ExStream<>(collection.stream());
    }

    public static <K, V> ExMapStream<K, V> of(Map<K, V> map) {
        return ExMapStream.ofStream(map.entrySet().stream());
    }

    public static <K, V> ExMapStream<K, V> of(Map.Entry<K, V>[] array) {
        return ExMapStream.ofStream(Arrays.stream(array));
    }

    public static <T> ExStream<T> iterate(final T seed, final UnaryOperator<T> f) {
        return new ExStream<>(Stream.iterate(seed, f));
    }

    public static <T> ExStream<T> concat(Stream<? extends T> a, Stream<? extends T> b) {
        return new ExStream<>(Stream.concat(a, b));
    }

    public static <T> ExStream<T> empty() {
        return new ExStream<>(Stream.empty());
    }

    public static <T> ExStream<T> generate(Supplier<T> s) {
        return new ExStream<>(Stream.generate(s));
    }


    //**************** 整合其他 ****************//

    //**************** byte ****************//

    public static ByteStream ofByte(byte... values) {
        return ByteStream.of(values);
    }

    public static ByteStream ofByte(byte value) {
        return ByteStream.of(value);
    }

    public static ByteStream ofByte(String str){
        return ByteStream.of(str);
    }
    public static ByteStream ofByte(Charset charset, byte... values) {
        return ByteStream.of(charset, values);
    }

    public static ByteStream ofByte(Charset charset, byte value) {
        return ByteStream.of(charset, value);
    }

    public static ByteStream ofByte(String str, Charset charset){
        return ByteStream.of(str, charset);
    }
    public static ByteStream ofByte(String str, String charsetName) throws UnsupportedEncodingException {
        return ByteStream.of(str, charsetName);
    }

    //**************** char ****************//

    public static CharStream ofChar(char c){
        return CharStream.of(c);
    }

    public static CharStream ofChar(char... values){
        return CharStream.of(values);
    }

    public static CharStream ofChar(String string){
        return CharStream.of(string);
    }

    //**************** String ****************//

    public static CharSequenceStream ofString(String s){
        return CharSequenceStream.ofCharSequence(s);
    }



    //**************** 转化 ****************//

    public CharStream mapToChar(ParseTo.ToChar<T> toChar){
        return CharStream.of(stream.mapToInt(t -> (int) toChar.apply(t)));
    }

    public ByteStream mapToByte(ParseTo.ToByte<T> toByte){
        return ByteStream.of(stream.mapToInt(t -> (int) toByte.apply(t)));
    }

    public CharSequenceStream mapToCharSequence(Function<T, ? extends CharSequence> toString){
        return CharSequenceStream.ofCharSequence(stream.map(toString));
    }

    public StringStream mapToString(Function<T, ? extends String> toString){
        return StringStream.ofString(stream.map(toString));
    }




    //**************** 简化用的方法 ****************//

    public void forEachSysOut(){
        this.forEach(System.out::println);
    }

    public void forEachSysErr(){
        this.forEach(System.out::println);
    }

    public void forEachPrint(PrintStream printStream){
        this.forEach(printStream::print);
    }

    public void forEachPrintln(PrintStream printStream){
        this.forEach(printStream::println);
    }

    /**
     * 转为list
     */
    public List<T> toList() {
        return getStream().collect(Collectors.toList());
    }

    /**
     * 转化为set
     */
    public Set<T> toSet() {
        return getStream().collect(Collectors.toSet());
    }

    /**
     * 转化为Map
     */
    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> keyMapper,
                                  Function<? super T, ? extends V> valueMapper) {
        return getStream().collect(Collectors.toMap(keyMapper, valueMapper));
    }

    /**
     * 转化为Map
     */
    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> keyMapper,
                                  Function<? super T, ? extends V> valueMapper,
                                  BinaryOperator<V> mergeFunction) {
        return getStream().collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction));
    }

    /**
     * 转化为Map，如果出现键冲突则直接使用原版Stream的异常方法
     */
    public <K, V, M extends Map<K, V>> Map<K, V> toMap(Function<? super T, ? extends K> keyMapper,
                                                       Function<? super T, ? extends V> valueMapper,
                                                       Supplier<M> mapSupplier) {
        return getStream().collect(Collectors.toMap(keyMapper, valueMapper, throwingMerger(), mapSupplier));
    }

    /**
     * 转化为Map
     */
    public <K, U, M extends Map<K, U>>
    M toMap(Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends U> valueMapper,
            BinaryOperator<U> mergeFunction,
            Supplier<M> mapSupplier) {
        return getStream().collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction, mapSupplier));
    }

    /**
     * 转化为MapStream
     */
    public <K, V> ExMapStream<K, V> maptoMap(Function<? super T, ? extends K> keyMapper,
                                                Function<? super T, ? extends V> valueMapper) {
        return ExMapStream.ofMap(getStream().collect(Collectors.toMap(keyMapper, valueMapper)));
    }

    /**
     * 转化为MapStream
     */
    public <K, V> ExMapStream<K, V> maptoMap(Function<? super T, ? extends K> keyMapper,
                                                Function<? super T, ? extends V> valueMapper,
                                                BinaryOperator<V> mergeFunction) {
        return ExMapStream.ofMap(getStream().collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction)));
    }

    /**
     * 转化为MapStream，如果出现键冲突则直接使用原版Stream的异常方法
     */
    public <K, V, M extends Map<K, V>> ExMapStream<K, V> maptoMap(Function<? super T, ? extends K> keyMapper,
                                                                     Function<? super T, ? extends V> valueMapper,
                                                                     Supplier<M> mapSupplier) {
        return ExMapStream.ofMap(getStream().collect(Collectors.toMap(keyMapper, valueMapper, throwingMerger(), mapSupplier)));
    }

    /**
     * 转化为MapStream
     */
    public <K, U, M extends Map<K, U>>
    ExMapStream<K, U> maptoMap(Function<? super T, ? extends K> keyMapper,
                                  Function<? super T, ? extends U> valueMapper,
                                  BinaryOperator<U> mergeFunction,
                                  Supplier<M> mapSupplier) {
        return ExMapStream.ofMap(getStream().collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction, mapSupplier)));
    }


    /**
     * 转化后toList
     */
    public <R> List<R> toList(Function<T, R> mapper) {
        return getStream().map(mapper).collect(Collectors.toList());
    }

    /**
     * 转化后排序后toList
     */
    public <R> List<R> toListSorted(Function<T, R> mapper) {
        return getStream().map(mapper).sorted().collect(Collectors.toList());
    }

    /**
     * 排序后转化后tolist
     */
    public <R> List<R> sortedToList(Function<T, R> mapper) {
        return getStream().sorted().map(mapper).collect(Collectors.toList());
    }

    /**
     * 转化后排序后toList
     */
    public <R> List<R> toListSorted(Function<T, R> mapper, Comparator<R> comparator) {
        return getStream().map(mapper).sorted(comparator).collect(Collectors.toList());
    }

    /**
     * 排序后转化后tolist
     */
    public <R> List<R> sortedToList(Function<T, R> mapper, Comparator<T> comparator) {
        return getStream().sorted(comparator).map(mapper).collect(Collectors.toList());
    }

    /**
     * joining
     */
    public String joining(Function<T, String> mapper) {
        return getStream().map(mapper).collect(Collectors.joining());
    }

    /**
     * joining
     */
    public String joining(Function<T, String> mapper, CharSequence delimiter) {
        return getStream().map(mapper).collect(Collectors.joining(delimiter));
    }

    /**
     * joining
     */
    public String joining(Function<T, String> mapper,
                          CharSequence delimiter,
                          CharSequence prefix,
                          CharSequence suffix) {
        return getStream().map(mapper).collect(Collectors.joining(delimiter, prefix, suffix));
    }

    /**
     * groupBy
     */
    public <K> Map<? extends K, List<T>> groupBy(Function<? super T, ? extends K> classifier) {
        return getStream().collect(Collectors.groupingBy(classifier));
    }

    /**
     * groupBy
     */
    public <K, A, D> Map<? extends K, D> groupBy(Function<? super T, ? extends K> classifier,
                                                 Collector<? super T, A, D> downstream) {
        return getStream().collect(Collectors.groupingBy(classifier, downstream));
    }

    /**
     * groupBy
     */
    public <K, A, D, M extends Map<K, D>> M groupBy(Function<? super T, ? extends K> classifier,
                                                    Supplier<M> mapFactory,
                                                    Collector<? super T, A, D> downstream) {
        return getStream().collect(Collectors.groupingBy(classifier, mapFactory, downstream));
    }

    /**
     * groupByConcurrent
     */
    public <K> Map<? extends K, List<T>> groupByConcurrent(Function<? super T, ? extends K> classifier) {
        return getStream().collect(Collectors.groupingByConcurrent(classifier));
    }

    /**
     * groupByConcurrent
     */
    public <K, A, D> Map<? extends K, D> groupByConcurrent(Function<? super T, ? extends K> classifier,
                                                           Collector<? super T, A, D> downstream) {
        return getStream().collect(Collectors.groupingByConcurrent(classifier, downstream));
    }

    /**
     * groupByConcurrent
     */
    public <K, A, D, M extends ConcurrentMap<K, D>> M groupByConcurrent(Function<? super T, ? extends K> classifier,
                                                                        Supplier<M> mapFactory,
                                                                        Collector<? super T, A, D> downstream) {
        return getStream().collect(Collectors.groupingByConcurrent(classifier, mapFactory, downstream));
    }

    /**
     * concat
     */
    public ExStream<T> concat(Stream<T> concat) {
        return toEx(Stream.concat(getStream(), concat));
    }

    public ExStream<T> concat(T t) {
        return toEx(Stream.concat(getStream(), Stream.of(t)));
    }

    public ExStream<T> concat(T... t) {
        return toEx(Stream.concat(getStream(), Stream.of(t)));
    }

    public ExStream<T> concat(Collection<T> collection) {
        return toEx(Stream.concat(getStream(), collection.stream()));
    }


    //**************** 对Stream流的接口实现 ****************//

    @Override
    public ExStream<T> filter(Predicate<? super T> predicate) {
        return toEx(getStream().filter(predicate));
    }

    /**
     * 过滤出不是null的
     */
    public ExStream<T> filterNonNull() {
        return toEx(getStream().filter(Objects::nonNull));
    }

    /**
     * 过滤出是null的
     */
    public ExStream<T> filterNull() {
        return toEx(getStream().filter(Objects::isNull));
    }

    @Override
    public <R> ExStream<R> map(Function<? super T, ? extends R> mapper) {
        return toEx(getStream().map(mapper));
    }

    public <K, V, R extends Map.Entry<K, V>> ExMapStream<K, V> mapToEntry(Function<? super T, ? extends R> mapper) {
        return ExMapStream.ofStream(getStream().map(mapper));
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        return getStream().mapToInt(mapper);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        return getStream().mapToLong(mapper);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return getStream().mapToDouble(mapper);
    }

    @Override
    public <R> ExStream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return toEx(getStream().flatMap(mapper));
    }

    public <K, V, R extends Map.Entry<K, V>> ExMapStream<K, V> flatMapToEntry(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return ExMapStream.ofStream(getStream().flatMap(mapper));
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return getStream().flatMapToInt(mapper);
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return getStream().flatMapToLong(mapper);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return getStream().flatMapToDouble(mapper);
    }

    @Override
    public ExStream<T> distinct() {
        return toEx(getStream().distinct());
    }

    @Override
    public ExStream<T> sorted() {
        return toEx(getStream().sorted());
    }

    @Override
    public ExStream<T> sorted(Comparator<? super T> comparator) {
        return toEx(getStream().sorted(comparator));
    }

    @Override
    public ExStream<T> peek(Consumer<? super T> action) {
        return toEx(getStream().peek(action));
    }

    @Override
    public ExStream<T> limit(long maxSize) {
        return toEx(getStream().limit(maxSize));
    }

    @Override
    public ExStream<T> skip(long n) {
        return toEx(getStream().skip(n));
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        getStream().forEach(action);
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        getStream().forEachOrdered(action);
    }

    @Override
    public Object[] toArray() {
        return getStream().toArray();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return getStream().toArray(generator);
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return getStream().reduce(identity, accumulator);
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return getStream().reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return getStream().reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return getStream().collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return getStream().collect(collector);
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return getStream().min(comparator);
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return getStream().max(comparator);
    }

    @Override
    public long count() {
        return getStream().count();
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return getStream().anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return getStream().allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return getStream().noneMatch(predicate);
    }

    @Override
    public Optional<T> findFirst() {
        return getStream().findFirst();
    }

    @Override
    public Optional<T> findAny() {
        return getStream().findAny();
    }


    //**************** baseStream方法实现 ****************//

    @Override
    public Iterator<T> iterator() {
        return getStream().iterator();
    }

    @Override
    public Spliterator<T> spliterator() {
        return getStream().spliterator();
    }

    @Override
    public boolean isParallel() {
        return getStream().isParallel();
    }

    @Override
    public ExStream<T> sequential() {
        return toEx(getStream().sequential());
    }

    @Override
    public ExStream<T> parallel() {
        return toEx(getStream().parallel());
    }

    @Override
    public ExStream<T> unordered() {
        return toEx(getStream().unordered());
    }

    @Override
    public ExStream<T> onClose(Runnable closeHandler) {
        return toEx(getStream().onClose(closeHandler));
    }

    @Override
    public void close() {
        getStream().close();
    }

    protected static <T> BinaryOperator<T> throwingMerger() {
        return (u, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", u));
        };
    }
}
