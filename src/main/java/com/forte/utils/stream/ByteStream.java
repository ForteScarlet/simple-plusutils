package com.forte.utils.stream;

import com.forte.utils.ables.MD5Able;
import com.forte.utils.function.ByteConsumer;
import com.forte.utils.function.ByteFunction;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * 基础数据类型中的byte类型Stream
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ByteStream implements IntStream, MD5Able {

    /**
     * 真正的Stream
     */
    private IntStream stream;

    /**
     * 编码格式
     */
    private Charset charset;

    private ByteStream(IntStream stream) {
        this.stream = stream;
        this.charset = Charset.defaultCharset();
    }

    private ByteStream(IntStream stream, Charset charset) {
        this.stream = stream;
        this.charset = charset == null ? Charset.defaultCharset() : charset;
    }

    private ByteStream(IntStream stream, String charsetName){
        this.stream = stream;
        this.charset = charsetName == null ? Charset.defaultCharset() : Charset.forName(charsetName);
    }

    static ByteStream of(IntStream intStream, Charset charset) {
        return new ByteStream(intStream, charset);
    }

    static ByteStream of(IntStream intStream) {
        return new ByteStream(intStream);
    }

    /**
     * of
     */
    public static ByteStream empty() {
        return of(IntStream.empty());
    }

    /**
     * of
     */
    public static ByteStream of(int t) {
        return of(IntStream.of(t));
    }

    /**
     * of
     */
    public static ByteStream of(int t, Charset charset) {
        return of(IntStream.of(t), charset);
    }

       /**
     * of
     */
    public static ByteStream of(byte b) {
        return of(IntStream.of(b));
    }

       /**
     * of
     */
    public static ByteStream of(byte b, Charset charset) {
        return of(IntStream.of(b), charset);
    }

    /**
     * of
     */
    public static ByteStream of(int... values) {
        return of(IntStream.of(values));
    }

    /**
     * of
     */
    public static ByteStream of(Charset charset, int... values) {
        return of(IntStream.of(values), charset);
    }

    /**
     * of
     */
    public static ByteStream of(byte... values) {
        int[] array = new int[values.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = values[i];
        }

        return of(array);
    }

    /**
     * of
     */
    public static ByteStream of(Charset charset, byte... values) {
        int[] array = new int[values.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = values[i];
        }

        return of(charset, array);
    }


    public static ByteStream of(String str){
        return of(str.getBytes());
    }

    public static ByteStream of(String str, Charset charset){
        return of(charset, str.getBytes(charset));
    }

    public static ByteStream of(String str, String charsetName) throws UnsupportedEncodingException {
        return of(Charset.forName(charsetName), str.getBytes(charsetName));
    }



    //**************** 扩展方法 ****************//

    /**
     * 转为Byte类型封装类
     */
    public ExStream<Byte> box(){
        return ExStream.of(mapToObj(i -> (byte) i));
    }

    @Override
    public String toStr(Charset charset){
        return new String(toByteArray(), charset);
    }

    @Override
    public String toStr(String charsetName) throws UnsupportedEncodingException {
        return new String(toByteArray(), charsetName);
    }

    @Override
    public String toStr(){
        return new String(toByteArray(), charset);
    }


    /**
     * 转化为int类型的流
     */
    public IntStream mapToInt(){
        return stream;
    }

    /**
     * 根据字符串转化为char流
     */
    public CharStream mapToChar(){
        return CharStream.of(toStr());
    }
    /**
     * 根据字符串转化为char流
     */
    public CharStream mapToChar(String charsetName) throws UnsupportedEncodingException {
        return CharStream.of(toStr(charsetName));
    }
    /**
     * 根据字符串转化为char流
     */
    public CharStream mapToChar(Charset charset){
        return CharStream.of(toStr(charset));
    }




    //**************** 实现接口方法 ****************//


    @Override
    public ByteStream filter(IntPredicate predicate) {
        return of(stream.filter(predicate), charset);
    }

    @Override
    public ByteStream map(IntUnaryOperator mapper) {
        return of(stream.map(mapper), charset);
    }

    @Override
    public <U> ExStream<U> mapToObj(IntFunction<? extends U> mapper) {
        return ExStream.of(stream.mapToObj(mapper));
    }

    public <U> ExStream<U> mapToObj(ByteFunction<? extends U> mapper) {
        return ExStream.of(stream.mapToObj(mapper));
    }

    @Override
    public LongStream mapToLong(IntToLongFunction mapper) {
        return stream.mapToLong(mapper);
    }

    @Override
    public DoubleStream mapToDouble(IntToDoubleFunction mapper) {
        return stream.mapToDouble(mapper);
    }

    @Override
    public ByteStream flatMap(IntFunction<? extends IntStream> mapper) {
        return of(stream.flatMap(mapper), charset);
    }

    public ByteStream flatMap(ByteFunction<? extends IntStream> mapper) {
        return of(stream.flatMap(mapper), charset);
    }

    @Override
    public ByteStream distinct() {
        return of(stream.distinct(), charset);
    }

    @Override
    public ByteStream sorted() {
        return of(stream.sorted(), charset);
    }

    @Override
    public ByteStream peek(IntConsumer action) {
        return of(stream.peek(action), charset);
    }

    @Override
    public ByteStream limit(long maxSize) {
        return of(stream.limit(maxSize), charset);
    }

    @Override
    public ByteStream skip(long n) {
        return of(stream.skip(n), charset);
    }

    @Override
    public void forEach(IntConsumer action) {
        stream.forEach(action);
    }

    @Override
    public void forEachOrdered(IntConsumer action) {
        stream.forEachOrdered(action);
    }

    public void forEach(ByteConsumer action) {
        stream.forEach(action);
    }

    public void forEachOrdered(ByteConsumer action) {
        stream.forEachOrdered(action);
    }

    @Override
    public int[] toArray() {
        return stream.toArray();
    }

    public byte[] toByteArray() {
        int[] ints = stream.toArray();
        byte[] result = new byte[ints.length];
        for (int i = 0; i < ints.length; i++) {
            result[i] = (byte) ints[i];
        }
        return result;
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        return stream.reduce(identity, op);
    }

    @Override
    public OptionalInt reduce(IntBinaryOperator op) {
        return stream.reduce(op);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, ObjIntConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        return stream.collect(supplier, accumulator, combiner);
    }

    @Override
    @Deprecated
    public int sum() {
        return stream.sum();
    }

    @Override
    public OptionalInt min() {
        return stream.min();
    }

    @Override
    public OptionalInt max() {
        return stream.max();
    }

    @Override
    public long count() {
        return stream.count();
    }

    @Override
    @Deprecated
    public OptionalDouble average() {
        return stream.average();
    }

    @Override
    public IntSummaryStatistics summaryStatistics() {
        return stream.summaryStatistics();
    }

    @Override
    public boolean anyMatch(IntPredicate predicate) {
        return stream.anyMatch(predicate);
    }

    @Override
    public boolean allMatch(IntPredicate predicate) {
        return stream.allMatch(predicate);
    }

    @Override
    public boolean noneMatch(IntPredicate predicate) {
        return stream.noneMatch(predicate);
    }

    @Override
    public OptionalInt findFirst() {
        return stream.findFirst();
    }

    @Override
    public OptionalInt findAny() {
        return stream.findAny();
    }

    @Override
    public LongStream asLongStream() {
        return stream.asLongStream();
    }

    @Override
    public DoubleStream asDoubleStream() {
        return stream.asDoubleStream();
    }

    @Override
    public ExStream<Integer> boxed() {
        return ExStream.of(stream.boxed());
    }

    @Override
    public ByteStream sequential() {
        return of(stream.sequential(), charset);
    }

    @Override
    public ByteStream parallel() {
        return of(stream.parallel());
    }

    @Override
    public ByteStream unordered() {
        return of(stream.unordered());
    }

    @Override
    public ByteStream onClose(Runnable closeHandler) {
        return of(stream.onClose(closeHandler));
    }

    @Override
    public void close() {
        stream.close();
    }

    @Override
    public PrimitiveIterator.OfInt iterator() {
        return stream.iterator();
    }

    @Override
    public Spliterator.OfInt spliterator() {
        return stream.spliterator();
    }

    @Override
    public boolean isParallel() {
        return stream.isParallel();
    }
}
