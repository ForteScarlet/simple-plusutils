package com.forte.utils.stream;

import com.forte.utils.ables.MD5Able;
import com.forte.utils.file.ExFileUtils;
import com.forte.utils.function.CharConsumer;
import com.forte.utils.function.CharFunction;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * 和{@link ByteStream} 类似，只不过是变为了char
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class CharStream implements IntStream, MD5Able {

    /**
     * 真正的Stream
     */
    private IntStream stream;

    /**
     * 编码格式
     */
    private Charset charset;

    private CharStream(IntStream stream) {
        this.stream = stream;
        this.charset = Charset.defaultCharset();
    }

    private CharStream(IntStream stream, Charset charset) {
        this.stream = stream;
        this.charset = charset == null ? Charset.defaultCharset() : charset;
    }

    private CharStream(IntStream stream, String charsetName){
        this.stream = stream;
        this.charset = charsetName == null ? Charset.defaultCharset() : Charset.forName(charsetName);
    }


    //**************** 工厂方法 ****************//

    /**
     * of
     */
    public static CharStream empty() {
        return of(IntStream.empty());
    }


    public static CharStream of(IntStream intStream, Charset charset) {
        return new CharStream(intStream, charset);
    }

    public static CharStream of(IntStream intStream) {
        return new CharStream(intStream);
    }

    public static CharStream of(Stream<Integer> intStream){
        return new CharStream(intStream.mapToInt(i -> i));
    }

    public static CharStream of(int t){
        return of(IntStream.of(t));
    }

    public static CharStream of(char c){
        return of(IntStream.of(c));
    }

    public static CharStream of(int... values){
        return of(IntStream.of(values));
    }

    public static CharStream of(char... values){
        int[] array = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            array[i] = values[i];
        }
        return of(IntStream.of(array));
    }

    public static CharStream of(String s){
        return of(s.toCharArray());
    }


    public static CharStream read(File file) throws IOException {
        return ExFileUtils.getChars(file);
    }

    //**************** 扩展方法 ****************//

    /**
     * 数据写入文件
     */
    public void write(File file) throws IOException {
        ExFileUtils.write(file, this);
    }

    @Override
    public String toStr() {
//        StringBuilder b = new StringBuilder();
//        forEach(b::append);
//        return b.toString();
        int[] ints = stream.toArray();
        return new String(ints, 0, ints.length);
//        int[] ints = stream.toArray();
//        char[] chars = new char[ints.length];
//        for (int i = 0; i < ints.length; i++) {
//            chars[i] = (char) ints[i];
//        }
//        return new String(chars, 0, ints.length);
    }

    @Override
    @Deprecated
    public String toStr(Charset charset) {
        return toStr();
    }

    @Override
    @Deprecated
    public String toStr(String charsetName) {
        return toStr();
    }

    public IntStream mapToInt(){
        return stream;
    }


    public ByteStream mapToByte(){
        return ByteStream.of(toStr());
    }

    public ByteStream mapToByte(String charsetName) throws UnsupportedEncodingException {
        return ByteStream.of(toStr(), charsetName);
    }

    public ByteStream mapToByte(Charset charset){
        return ByteStream.of(toStr(), charset);
    }






    //**************** 对IntStream的实现 ****************//



    @Override
    public CharStream filter(IntPredicate predicate) {
        return of(stream.filter(predicate));
    }

    @Override
    public CharStream map(IntUnaryOperator mapper) {
        return of(stream.map(mapper));
    }

    @Override
    public <U> ExStream<U> mapToObj(IntFunction<? extends U> mapper) {
        return ExStream.of(stream.mapToObj(mapper));
    }

    public <U> ExStream<U> mapToObj(CharFunction<? extends U> mapper) {
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
    public CharStream flatMap(IntFunction<? extends IntStream> mapper) {
        return of(stream.flatMap(mapper));
    }

    @Override
    public CharStream distinct() {
        return of(stream.distinct());
    }

    @Override
    public CharStream sorted() {
        return of(stream.sorted());
    }

    @Override
    public CharStream peek(IntConsumer action) {
        return of(stream.peek(action));
    }

    public CharStream peek(CharConsumer action) {
        return of(stream.peek(action));
    }

    @Override
    public CharStream limit(long maxSize) {
        return of(stream.limit(maxSize));
    }

    @Override
    public CharStream skip(long n) {
        return of(stream.skip(n));
    }

    @Override
    public void forEach(IntConsumer action) {
        stream.forEach(action);
    }

    public void forEach(CharConsumer action){
        stream.forEach(action);
    }

    @Override
    public void forEachOrdered(IntConsumer action) {
        stream.forEach(action);
    }

    public void forEachOrdered(CharConsumer action){
        stream.forEachOrdered(action);
    }

    @Override
    public int[] toArray() {
        return stream.toArray();
    }

    public char[] toCharArray(){
        int[] ints = stream.toArray();
        char[] result = new char[ints.length];
        for (int i = 0; i < ints.length; i++) {
            result[i] = (char) ints[i];
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
        return stream.anyMatch(predicate);
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

    public ExStream<Character> box(){
        return ExStream.of(stream.mapToObj(i -> (char) i));
    }


    @Override
    public CharStream sequential() {
        return of(stream.sequential());
    }

    @Override
    public CharStream parallel() {
        return of(stream.parallel());
    }

    @Override
    public CharStream unordered() {
        return of(stream.unordered());
    }

    @Override
    public CharStream onClose(Runnable closeHandler) {
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
