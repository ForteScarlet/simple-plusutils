package com.forte.utils.stream;

import com.forte.utils.basis.ExStringUtils;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 * 字符串类型的Stream
 *
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class StringStream extends CharSequenceStream<String> {

    /**
     * 构造，以指定内部流对象
     */
    StringStream(Stream<String> stream) {
        super(stream);
    }


    public static StringStream toStrEx(Stream<String> stream){
        return ofString(stream);
    }

    public static StringStream ofString(Stream<String> stream){
        return new StringStream(stream);
    }

    public static StringStream ofString(String s){
        return new StringStream(Stream.of(s));
    }

    public static StringStream ofString(String... s){
        return new StringStream(Stream.of(s));
    }

    //**************** 重写部分方法，改变返回值 ****************//


    @Override
    public StringStream trim() {
        return toStrEx(getStream().map(String::trim));
    }

    @Override
    public StringStream concat(Stream<String> concat) {
        return toStrEx(Stream.concat(getStream(), concat));
    }

    @Override
    public StringStream concat(String s) {
        return toStrEx(Stream.concat(getStream(), Stream.of(s)));
    }

    @Override
    public StringStream concat(String... t) {
        return toStrEx(Stream.concat(getStream(), Stream.of(t)));
    }

    @Override
    public StringStream concat(Collection<String> collection) {
        return toStrEx(Stream.concat(getStream(), collection.stream()));
    }

    @Override
    public StringStream filter(Predicate<? super String> predicate) {
        return toStrEx(getStream().filter(predicate));
    }

    @Override
    public StringStream filterNonNull() {
        return toStrEx(getStream().filter(Objects::nonNull));
    }

    @Override
    public StringStream filterNull() {
        return toStrEx(getStream().filter(Objects::isNull));
    }

    @Override
    public StringStream filterEmpty() {
        return toStrEx(getStream().filter(ExStringUtils::isEmpty));
    }

    @Override
    public StringStream filterNotEmpty() {
        return toStrEx(getStream().filter(ExStringUtils::isNotEmpty));
    }

    @Override
    public StringStream flatChangelessMap(Function<? super String, ? extends Stream<? extends String>> mapper) {
        return toStrEx(getStream().flatMap(mapper));
    }

    @Override
    public StringStream distinct() {
        return toStrEx(getStream().distinct());
    }

    @Override
    public StringStream sorted() {
        return toStrEx(getStream().sorted());
    }

    @Override
    public StringStream sorted(Comparator<? super String> comparator) {
        return toStrEx(getStream().sorted(comparator));
    }

    @Override
    public StringStream peek(Consumer<? super String> action) {
        return toStrEx(getStream().peek(action));
    }

    @Override
    public StringStream limit(long maxSize) {
        return toStrEx(getStream().limit(maxSize));
    }

    @Override
    public StringStream skip(long n) {
        return toStrEx(getStream().skip(n));
    }

    @Override
    public StringStream sequential() {
        return toStrEx(getStream().sequential());
    }

    @Override
    public StringStream parallel() {
        return toStrEx(getStream().parallel());
    }

    @Override
    public StringStream unordered() {
        return toStrEx(getStream().unordered());
    }

    @Override
    public StringStream onClose(Runnable closeHandler) {
        return toStrEx(getStream().onClose(closeHandler));
    }

}
