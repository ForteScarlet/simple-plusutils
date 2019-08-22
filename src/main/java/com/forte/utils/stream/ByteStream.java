package com.forte.utils.stream;

import com.forte.utils.basis.MD5Utils;
import com.forte.utils.function.ExFunction;
import com.forte.utils.function.FunctionThrows;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * 基础数据类型中的byte类型Stream
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ByteStream implements IntStream {

    /**
     * 真正的Stream
     */
    private IntStream stream;

    private Charset charset;

    private ByteStream(IntStream stream) {
        this.stream = stream;
        this.charset = Charset.defaultCharset();
    }

    private ByteStream(IntStream stream, Charset charset) {
        this.stream = stream;
        this.charset = charset;
    }

    private ByteStream(IntStream stream, String charsetName){
        this.stream = stream;
        this.charset = Charset.forName(charsetName);
    }

    private static ByteStream of(IntStream intStream, Charset charset) {
        return new ByteStream(intStream, charset);
    }

    private static ByteStream of(IntStream intStream) {
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


    public static ByteStream ofBytes(String str){
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

    public String toStr(Charset charset){
        return new String(toByteArray(), charset);
    }

    public String toStr(String charsetName) throws UnsupportedEncodingException {
        return new String(toByteArray(), charsetName);
    }

    public String toStr(){
        return new String(toByteArray());
    }

    /**
     * 转化为MD5
     */
    public String toMD5() throws NoSuchAlgorithmException {
        return MD5Utils.toMD5(toStr());
    }
    /**
     * 转化为MD5
     */
    public String toMD5(String charsetName) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return MD5Utils.toMD5(toStr(charsetName));
    }
    /**
     * 转化为MD5
     */
    public String toMD5(Charset charset) throws NoSuchAlgorithmException {
        return MD5Utils.toMD5(toStr(charset));
    }

    /**
     * 转化为MD5，加盐并自定义盐函数
     * @param salt          盐
     * @param saltFunction
     * 盐函数<br>
     * 盐函数中，第一个参数为MD5字符串获取函数，参数为一个字符串，返回一个MD5字符串<br>
     * 第二个参数为原本的字符串<br>
     * 第三个参数为盐<br>
     * 返回值为加密好的MD5<br>
     *     <code>
     *              String md = "hello!hahahahahahh";
     *              String s = ByteStream.of(md).toMD5("salt", (f, str, salt) -> {
     *             try {
     *                 return f.apply(str + '.' + salt);
     *             } catch (Throwable throwable) {
     *                 return null;
     *             }
     *         });
     *     </code>
     * @return
   */
    public String toMD5(String salt, ExFunction<FunctionThrows<String, String>, String, String, String> saltFunction) {
        return saltFunction.apply(MD5Utils::toMD5, toStr(), salt);
    }
    public String toMD5(String salt, Charset charset, ExFunction<FunctionThrows<String, String>, String, String, String> saltFunction) {
        return saltFunction.apply(MD5Utils::toMD5, toStr(charset), salt);
    }
    public String toMD5(String salt, String charsetName, ExFunction<FunctionThrows<String, String>, String, String, String> saltFunction) throws UnsupportedEncodingException {
        return saltFunction.apply(MD5Utils::toMD5, toStr(charsetName), salt);
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
    public IntStream parallel() {
        return of(stream.parallel());
    }

    @Override
    public IntStream unordered() {
        return of(stream.unordered());
    }

    @Override
    public IntStream onClose(Runnable closeHandler) {
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
