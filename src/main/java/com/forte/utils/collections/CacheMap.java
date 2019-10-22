package com.forte.utils.collections;

import com.forte.utils.stream.ExStream;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * 缓存Map，懒判断方式来对一些值进行缓存
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class CacheMap<T, R> extends AbstractMap<T, R> {

    private final ConcurrentHashMap<T, CacheReturn<R>> concurrentHashMap;

    /**
     * 获取
     */
    @Override
    public R get(Object t){
        CacheReturn<R> cacheReturn = concurrentHashMap.get(t);
        if(cacheReturn == null){
            return null;
        }else{
            //有东西，判断是否过期
            if (cacheReturn.isExpired()) {
                //如果当前时间在过期时间之后，则为过期，移除
                remove(t);
                return null;
            }else{
                return cacheReturn.getCache();
            }
        }
    }

    /**
     * 添加一个，如果时间已经过期则不添加
     */
    public R put(T t, R r, LocalDateTime expireDate){
        return put(t, new CacheReturn<>(expireDate, r));
    }

    /** 添加一个，指定过期规则 */
    public R put(T t, R r, TemporalAmount amount){
        return put(t, r, LocalDateTime.now().plus(amount));
    }

    /** 存值并指定过期规则 */
    public R put(T t , R r, long amountToAdd, TemporalUnit unit){
        return put(t, r, LocalDateTime.now().plus(amountToAdd, unit));
    }

    /** 过xx纳秒之后过期 */
    public R putPlusNanos(T t , R r, long nanos){
        return put(t, r, LocalDateTime.now().plusNanos(nanos));
    }

    /** 过xx秒之后过期 */
    public R putPlusSeconds(T t , R r, long seconds){
        return put(t, r, LocalDateTime.now().plusSeconds(seconds));
    }

    /** 过xx分钟后过期 */
    public R putPlusMinutes(T t , R r, long minutes){
        return put(t, r, LocalDateTime.now().plusMinutes(minutes));
    }

    /** 过xx小时之后过期 */
    public R putPlusHours(T t , R r, long hours){
        return put(t, r, LocalDateTime.now().plusHours(hours));
    }

    /** 过xx天之后过期 */
    public R putPlusDays(T t , R r, long days){
        return put(t, r, LocalDateTime.now().plusDays(days));
    }

    /** 过xx月之后过期 */
    public R putPlusMonth(T t , R r, long month){
        return put(t, r, LocalDateTime.now().plusMonths(month));
    }

    /** 过xx年之后过期，如果你能等到那时候的话 */
    public R putPlusYear(T t , R r, long year){
        return put(t, r, LocalDateTime.now().plusYears(year));
    }


    /**
     * 添加一个，如果时间已经过期则不添加并尝试移除存在的值
     */
    protected final R put(T t, CacheReturn<R> cacheReturn){
        if(LocalDateTime.now().isBefore(cacheReturn.getExpireTime())){
            CacheReturn<R> put = concurrentHashMap.put(t, cacheReturn);
            return put == null ? null : put.getCache();
        }else{
            remove(t);
            return null;
        }
    }

    /**
     * 默认过期时间为1天后
     *
     * @deprecated
     * 推荐使用指定时间的方法而非此方法
     */
    @Override
    public R put(T t, R r){
        return putPlusDays(t, r, 1);
    }


    @Override
    public R remove(Object k){
        CacheReturn<R> remove = concurrentHashMap.remove(k);
        return remove == null ? null : remove.getCache();
    }

    @Override
    public void clear(){
        concurrentHashMap.clear();
    }


    @Override
    public Set<Entry<T, R>> entrySet() {
        return ExStream.of(concurrentHashMap.entrySet())
                .map(e -> {
                    CacheReturn<R> value = e.getValue();
                    return (Entry<T, R>) new SimpleEntry<>(e.getKey(), value == null ? null : value.getCache());
                })
                .toSet();
    }

    /**
     * 手动触发一次检测，检测全部的缓存中是否存在过期值
     */
    public void detect(){
        concurrentHashMap.forEach((k, v) -> {
            if(v.isExpired()){
                remove(k);
            }
        });
    }

    /**
     * 就是foreach
     * @param consumer
     */
    public void cacheForEach(BiConsumer<T, CacheReturn<R>> consumer){
        concurrentHashMap.forEach(consumer);
    }


    /**
     * Creates a new, empty map with the default initial table size (16).
     */
    public CacheMap() {
        this.concurrentHashMap = new ConcurrentHashMap<>();
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
    public CacheMap(int initialCapacity) {
        this.concurrentHashMap = new ConcurrentHashMap<>(initialCapacity);
    }

    /**
     * Creates a new map with the same mappings as the given map.
     *
     * @param m the map
     */
    public CacheMap(Map<T, CacheReturn<R>> m) {
        this.concurrentHashMap = new ConcurrentHashMap<>(m);
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
    public CacheMap(int initialCapacity, float loadFactor) {
        this.concurrentHashMap = new ConcurrentHashMap<>(initialCapacity, loadFactor);
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
    public CacheMap(int initialCapacity, float loadFactor, int concurrencyLevel) {
        this.concurrentHashMap = new ConcurrentHashMap<>(initialCapacity, loadFactor, concurrencyLevel);
    }

    /**
     * 缓存对象，记录过期时间和记录的缓存返回值
     */
    public static class CacheReturn<T> {

        /** 过期时间 */
        private final LocalDateTime expireTime;

        private final T cache;

        public LocalDateTime getExpireTime() {
            return expireTime;
        }

        public T getCache() {
            return cache;
        }

        /**
         * 判断是否过期了
         */
        public boolean isExpired(){
            return LocalDateTime.now().isAfter(expireTime);
        }

        @Override
        public String toString() {
            return "CacheReturn{" +
                    "expireTime=" + expireTime +
                    ", cache=" + (cache.getClass().isArray() ? Arrays.toString((Object[]) cache) : String.valueOf(cache)) +
                    '}';
        }

        public CacheReturn(LocalDateTime expireTime, T obj){
            Objects.requireNonNull(expireTime);
            this.expireTime = expireTime;
            this.cache = obj;
        }

    }
}
