package com.forte.utils.stream;

import com.forte.utils.basis.CharSequenceUtils;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 *
 * 字符串类型的流对象
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class CharSequenceStream<T extends CharSequence> extends ExStream<T> {
    /**
     * 构造，以指定内部流对象
     *
     * @param stream
     */
    CharSequenceStream(Stream<T> stream) {
        super(stream);
    }

    //**************** of ****************//

    protected static <T extends CharSequence> CharSequenceStream toCharSeEx(Stream<T> stream){
        return ofCharSequence(stream);
    }

    public static <T extends CharSequence> CharSequenceStream ofCharSequence(Stream<T> s){
        return new CharSequenceStream(s);
    }

    public static CharSequenceStream ofCharSequence(String s){
        return new CharSequenceStream(Stream.of(s));
    }

    public static CharSequenceStream ofCharSequence(String... s){
        return new CharSequenceStream(Stream.of(s));
    }

    public static <T extends CharSequence> CharSequenceStream ofCharSequence(Collection<T> c){
        return new CharSequenceStream(c.stream());
    }



    //**************** 拓展方法 ****************//


    public String joining(){
        return getStream().collect(Collectors.joining());
    }

    public String joining(T delimiter){
        return getStream().collect(Collectors.joining(delimiter));
    }

    public String joining(T delimiter,
                          T prefix,
                          T suffix){
        return getStream().collect(Collectors.joining(delimiter, prefix, suffix));
    }

    public CharSequenceStream trim(){
        return toCharSeEx(getStream().map(CharSequenceUtils::trim));
    }


    //**************** to builder/joiner/String ****************//

    @Override
    public String toString(){
        return joining();
    }

    /**
     * 转化为builder
     * @param cb 函数，参数1为builder，2为CharSequence对象
     */
    public StringBuilder toBuilder(BiConsumer<StringBuilder, CharSequence> cb){
        StringBuilder builder = new StringBuilder();
        forEach(c -> cb.accept(builder, c));
        return builder;
    }

    /**
     * 转化为builder
     * @param cb 函数，参数1为builder，2为CharSequence对象
     */
    public StringBuilder toBuilder(int init, BiConsumer<StringBuilder, CharSequence> cb){
        StringBuilder builder = new StringBuilder(init);
        forEach(c -> cb.accept(builder, c));
        return builder;
    }

    /**
     * 转化为builder
     * @param cb 函数，参数1为builder，2为CharSequence对象
     */
    public StringBuilder toBuilder(String init, BiConsumer<StringBuilder, CharSequence> cb){
        StringBuilder builder = new StringBuilder(init);
        forEach(c -> cb.accept(builder, c));
        return builder;
    }

    /**
     * 转化为builder
     * @param cb 函数，参数1为builder，2为CharSequence对象
     */
    public StringBuilder toBuilder(CharSequence init, BiConsumer<StringBuilder, CharSequence> cb){
        StringBuilder builder = new StringBuilder(init);
        forEach(c -> cb.accept(builder, c));
        return builder;
    }

    /**
     * 转化为builder
     */
    public StringBuilder toBuilder(){
        StringBuilder builder = new StringBuilder();
        forEach(builder::append);
        return builder;
    }

    /**
     * 转化为builder
     */
    public StringBuilder toBuilder(int init){
        StringBuilder builder = new StringBuilder(init);
        forEach(builder::append);
        return builder;
    }

    /**
     * 转化为builder
     */
    public StringBuilder toBuilder(String init){
        StringBuilder builder = new StringBuilder(init);
        forEach(builder::append);
        return builder;
    }

    /**
     * 转化为builder
     */
    public StringBuilder toBuilder(CharSequence init){
        StringBuilder builder = new StringBuilder(init);
        forEach(builder::append);
        return builder;
    }

    /**
     * 转化为Joiner
     * @param delimiter 切割符
     * @param cj        函数，参数1为joiner，2为CharSequence对象
     */
    public StringJoiner toJoiner(CharSequence delimiter, BiConsumer<StringJoiner, CharSequence> cj){
        StringJoiner joiner = new StringJoiner(delimiter);
        forEach(c -> cj.accept(joiner, c));
        return joiner;
    }

    /**
     * 转化为Joiner
     * @param delimiter 切割符
     * @param cj        函数，参数1为joiner，2为CharSequence对象
     */
    public StringJoiner toJoiner(CharSequence delimiter,
                                 CharSequence prefix,
                                 CharSequence suffix,
                                 BiConsumer<StringJoiner, CharSequence> cj){
        StringJoiner joiner = new StringJoiner(delimiter, prefix, suffix);
        forEach(c -> cj.accept(joiner, c));
        return joiner;
    }

    /**
     * 转化为Joiner
     * @param delimiter 切割符
     */
    public StringJoiner toJoiner(CharSequence delimiter){
        StringJoiner joiner = new StringJoiner(delimiter);
        forEach(joiner::add);
        return joiner;
    }

    /**
     * 转化为Joiner
     * @param delimiter 切割符
     */
    public StringJoiner toJoiner(CharSequence delimiter,
                                 CharSequence prefix,
                                 CharSequence suffix){
        StringJoiner joiner = new StringJoiner(delimiter, prefix, suffix);
        forEach(joiner::add);
        return joiner;
    }






    // 重写ExStream的流方法
    /**
     * concat
     */
    @Override
    public CharSequenceStream concat(Stream<T> concat) {
        return toCharSeEx(Stream.concat(getStream(), concat));
    }

    @Override
    public CharSequenceStream concat(T t) {
        return toCharSeEx(Stream.concat(getStream(), Stream.of(t)));
    }

    @Override
    public CharSequenceStream concat(T... t) {
        return toCharSeEx(Stream.concat(getStream(), Stream.of(t)));
    }

    @Override
    public CharSequenceStream concat(Collection<T> collection) {
        return toCharSeEx(Stream.concat(getStream(), collection.stream()));
    }


    //**************** 对Stream流的接口实现 ****************//

    @Override
    public CharSequenceStream filter(Predicate<? super T> predicate) {
        return toCharSeEx(getStream().filter(predicate));
    }

    /**
     * 过滤出不是null的
     */
    @Override
    public CharSequenceStream filterNonNull() {
        return toCharSeEx(getStream().filter(Objects::nonNull));
    }

    /**
     * 过滤出是null的
     */
    @Override
    public CharSequenceStream filterNull() {
        return toCharSeEx(getStream().filter(Objects::isNull));
    }

    /**
     * 过滤掉不是空的字符串，只留下空的
     */
    public CharSequenceStream filterEmpty(){
        return toCharSeEx(getStream().filter(s -> s == null || s.length() == 0));
    }

    /**
     * 过滤出不是空的字符串
     */
    public CharSequenceStream filterNotEmpty(){
        return toCharSeEx(getStream().filter(s -> s != null && s.length() > 0));
    }

    public CharSequenceStream flatChangelessMap(Function<? super T, ? extends Stream<? extends String>> mapper) {
        return toCharSeEx(getStream().flatMap(mapper));
    }

    @Override
    public CharSequenceStream distinct() {
        return toCharSeEx(getStream().distinct());
    }

    @Override
    public CharSequenceStream sorted() {
        return toCharSeEx(getStream().sorted());
    }

    @Override
    public CharSequenceStream sorted(Comparator<? super T> comparator) {
        return toCharSeEx(getStream().sorted(comparator));
    }

    @Override
    public CharSequenceStream peek(Consumer<? super T> action) {
        return toCharSeEx(getStream().peek(action));
    }

    @Override
    public CharSequenceStream limit(long maxSize) {
        return toCharSeEx(getStream().limit(maxSize));
    }

    @Override
    public CharSequenceStream skip(long n) {
        return toCharSeEx(getStream().skip(n));
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return getStream().toArray(generator);
    }

    public Optional<T> minByLength(){
        return getStream().min(Comparator.comparing(T::length));
    }

    public Optional<T> maxByLength(){
        return getStream().max(Comparator.comparing(T::length));
    }

    @Override
    public long count() {
        return getStream().count();
    }


    //**************** baseStream方法实现 ****************//

    @Override
    public CharSequenceStream sequential() {
        return toCharSeEx(getStream().sequential());
    }

    @Override
    public CharSequenceStream parallel() {
        return toCharSeEx(getStream().parallel());
    }

    @Override
    public CharSequenceStream unordered() {
        return toCharSeEx(getStream().unordered());
    }

    @Override
    public CharSequenceStream onClose(Runnable closeHandler) {
        return toCharSeEx(getStream().onClose(closeHandler));
    }

    @Override
    public void close() {
        getStream().close();
    }









}
