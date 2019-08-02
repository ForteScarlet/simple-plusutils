package com.forte.utils.collections;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * 固定时长的缓存Map，他在默认情况下会记录你每次规定的时间并在下一次与之保持一致
 * 如果是使用增量定时，则下次也以同量增量
 * 如果是使用指定日期定时，则下次也使用同一个日期
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class StableTimeCacheMap<T, R> extends CacheMap<T, R> {

    /**
     * 当前默认时间获取器 通过AtomicReference对象实现线程安全
     * 默认情况下使用1小时后过期的固定时间
     */
    private volatile AtomicReference<Supplier<LocalDateTime>> stableTimeCreator;

    /**
     * 更新默认时间获取器
     */
    private void updateTimeCreator(Supplier<LocalDateTime> creator){
        stableTimeCreator.updateAndGet(old -> creator);
    }

    /**
     * 获取当前的过时时间
     */
    private LocalDateTime getStableTime(){
        return stableTimeCreator.get().get();
    }

    /**
     * 添加一个，如果时间已经过期则不添加
     */
    @Override
    public R put(T t, R r, LocalDateTime expireDate){
        setTime(expireDate);
        return put(t, new CacheReturn<>(expireDate, r));
    }

    @Override
    public R put(T t, R r, TemporalAmount amount){
        setTime(LocalDateTime.now().plus(amount));
        return put(t, r, LocalDateTime.now().plus(amount));
    }

    @Override
    public R put(T t , R r, long amountToAdd, TemporalUnit unit){
        setTime(LocalDateTime.now().plus(amountToAdd, unit));
        return put(t, r, LocalDateTime.now().plus(amountToAdd, unit));
    }

    @Override
    public R putPlusNanos(T t , R r, long nanos){
        setTimePlusNanos(nanos);
        return put(t, r, LocalDateTime.now().plusNanos(nanos));
    }

    @Override
    public R putPlusSeconds(T t , R r, long seconds){
        setTimePlusSeconds(seconds);
        return put(t, r, LocalDateTime.now().plusSeconds(seconds));
    }

    @Override
    public R putPlusMinutes(T t , R r, long minutes){
        setTimePlusMinutes(minutes);
        return put(t, r, LocalDateTime.now().plusMinutes(minutes));
    }

    @Override
    public R putPlusHours(T t , R r, long hours){
        setTimePlusHours(hours);
        return put(t, r, LocalDateTime.now().plusHours(hours));
    }

    @Override
    public R putPlusDays(T t , R r, long days){
        setTimePlusDays(days);
        return put(t, r, LocalDateTime.now().plusDays(days));
    }

    @Override
    public R putPlusMonth(T t , R r, long month){
        setTimePlusMonth(month);
        return put(t, r, LocalDateTime.now().plusMonths(month));
    }

    @Override
    public R putPlusYear(T t , R r, long year){
        setTimePlusYear(year);
        return put(t, r, LocalDateTime.now().plusYears(year));
    }

    /**
     * 此方法将不再过时, 并使用固定时长来作为时间
     */
    @Override
    public R put(T t, R r){
        return put(t, r, getStableTime());
    }


    //**************** 定义时间长度 ****************//
    /**
     * 添加一个，如果时间已经过期则不添加
     */
    public void setTime(LocalDateTime expireDate){
        updateTimeCreator(() -> expireDate);
    }

    public void setTime(TemporalAmount amount){
        updateTimeCreator(() -> LocalDateTime.now().plus(amount));
    }

    public void setTime(long amountToAdd, TemporalUnit unit){
        updateTimeCreator(() -> LocalDateTime.now().plus(amountToAdd, unit));
    }

    public void setTimePlusNanos(long nanos){
        updateTimeCreator(() -> LocalDateTime.now().plusNanos(nanos));
    }

    public void setTimePlusSeconds(long seconds){
        updateTimeCreator(() -> LocalDateTime.now().plusSeconds(seconds));
    }

    public void setTimePlusMinutes(long minutes){
        updateTimeCreator(() -> LocalDateTime.now().plusMinutes(minutes));
    }

    public void setTimePlusHours(long hours){
        updateTimeCreator(() -> LocalDateTime.now().plusHours(hours));
    }

    public void setTimePlusDays(long days){
        updateTimeCreator(() -> LocalDateTime.now().plusDays(days));
    }

    public void setTimePlusMonth(long month){
        updateTimeCreator(() -> LocalDateTime.now().plusMonths(month));
    }

    public void setTimePlusYear(long year){
        updateTimeCreator(() -> LocalDateTime.now().plusYears(year));
    }









    //**************** 构造 ****************//


    /**
     * Creates a new, empty map with the default initial table size (16).
     */
    public StableTimeCacheMap() {
        this.stableTimeCreator = new AtomicReference<>(() -> LocalDateTime.now().plusHours(1));
    }

    /**
     * Creates a new, empty map with an initial table size
     * accommodating the specified number of elements without the need
     * to dynamically resize.
     *
     * @param initialCapacity The implementation performs internal
     *                        sizing to accommodate this many elements.
     * @throws IllegalArgumentException if the initial capacity of
     *                                  elements is negative
     */
    public StableTimeCacheMap(int initialCapacity) {
        super(initialCapacity);
        this.stableTimeCreator = new AtomicReference<>(() -> LocalDateTime.now().plusHours(1));
    }

    /**
     * Creates a new map with the same mappings as the given map.
     *
     * @param m the map
     */
    public StableTimeCacheMap(Map<T, CacheReturn<R>> m) {
        super(m);
        this.stableTimeCreator = new AtomicReference<>(() -> LocalDateTime.now().plusHours(1));
    }

    /**
     * Creates a new, empty map with an initial table size based on
     * the given number of elements ({@code initialCapacity}) and
     * initial table density ({@code loadFactor}).
     *
     * @param initialCapacity the initial capacity. The implementation
     *                        performs internal sizing to accommodate this many elements,
     *                        given the specified load factor.
     * @param loadFactor      the load factor (table density) for
     *                        establishing the initial table size
     * @throws IllegalArgumentException if the initial capacity of
     *                                  elements is negative or the load factor is nonpositive
     * @since 1.6
     */
    public StableTimeCacheMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        this.stableTimeCreator = new AtomicReference<>(() -> LocalDateTime.now().plusHours(1));

    }

    /**
     * Creates a new, empty map with an initial table size based on
     * the given number of elements ({@code initialCapacity}), table
     * density ({@code loadFactor}), and number of concurrently
     * updating threads ({@code concurrencyLevel}).
     *
     * @param initialCapacity  the initial capacity. The implementation
     *                         performs internal sizing to accommodate this many elements,
     *                         given the specified load factor.
     * @param loadFactor       the load factor (table density) for
     *                         establishing the initial table size
     * @param concurrencyLevel the estimated number of concurrently
     *                         updating threads. The implementation may use this value as
     *                         a sizing hint.
     * @throws IllegalArgumentException if the initial capacity is
     *                                  negative or the load factor or concurrencyLevel are
     *                                  nonpositive
     */
    public StableTimeCacheMap(int initialCapacity, float loadFactor, int concurrencyLevel) {
        super(initialCapacity, loadFactor, concurrencyLevel);
        this.stableTimeCreator = new AtomicReference<>(() -> LocalDateTime.now().plusHours(1));
    }

    //**************** 指定初始固定时间的构造 ****************//


    /**
     * Creates a new, empty map with the default initial table size (16).
     */
    public StableTimeCacheMap(Supplier<LocalDateTime> timeCreator) {
        this.stableTimeCreator = new AtomicReference<>(timeCreator);
    }

    /**
     * Creates a new, empty map with an initial table size
     * accommodating the specified number of elements without the need
     * to dynamically resize.
     *
     * @param initialCapacity The implementation performs internal
     *                        sizing to accommodate this many elements.
     * @throws IllegalArgumentException if the initial capacity of
     *                                  elements is negative
     */
    public StableTimeCacheMap(int initialCapacity, Supplier<LocalDateTime> timeCreator) {
        super(initialCapacity);
        this.stableTimeCreator = new AtomicReference<>(timeCreator);
    }

    /**
     * Creates a new map with the same mappings as the given map.
     *
     * @param m the map
     */
    public StableTimeCacheMap(Map<T, CacheReturn<R>> m, Supplier<LocalDateTime> timeCreator) {
        super(m);
        this.stableTimeCreator = new AtomicReference<>(timeCreator);
    }

    /**
     * Creates a new, empty map with an initial table size based on
     * the given number of elements ({@code initialCapacity}) and
     * initial table density ({@code loadFactor}).
     *
     * @param initialCapacity the initial capacity. The implementation
     *                        performs internal sizing to accommodate this many elements,
     *                        given the specified load factor.
     * @param loadFactor      the load factor (table density) for
     *                        establishing the initial table size
     * @throws IllegalArgumentException if the initial capacity of
     *                                  elements is negative or the load factor is nonpositive
     * @since 1.6
     */
    public StableTimeCacheMap(int initialCapacity, float loadFactor, Supplier<LocalDateTime> timeCreator) {
        super(initialCapacity, loadFactor);
        this.stableTimeCreator = new AtomicReference<>(timeCreator);

    }

    /**
     * Creates a new, empty map with an initial table size based on
     * the given number of elements ({@code initialCapacity}), table
     * density ({@code loadFactor}), and number of concurrently
     * updating threads ({@code concurrencyLevel}).
     *
     * @param initialCapacity  the initial capacity. The implementation
     *                         performs internal sizing to accommodate this many elements,
     *                         given the specified load factor.
     * @param loadFactor       the load factor (table density) for
     *                         establishing the initial table size
     * @param concurrencyLevel the estimated number of concurrently
     *                         updating threads. The implementation may use this value as
     *                         a sizing hint.
     * @throws IllegalArgumentException if the initial capacity is
     *                                  negative or the load factor or concurrencyLevel are
     *                                  nonpositive
     */
    public StableTimeCacheMap(int initialCapacity, float loadFactor, int concurrencyLevel, Supplier<LocalDateTime> timeCreator) {
        super(initialCapacity, loadFactor, concurrencyLevel);
        this.stableTimeCreator = new AtomicReference<>(timeCreator);
    }
}
