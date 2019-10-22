package com.forte.utils.time;

import java.util.function.Function;

/**
 * 秒表-计时用
 * <br>
 * 你可以先计时，在读取，或者直接两次读取
 * <code>
 *     Stopwatch.difference();
 *     long sp = Stopwatch.difference();
 *     System.out.println("时间差: " + sp);
 * </code>
 * <br>
 * 或者
 * <br>
 * <code>
 *     Stopwatch.record();
 *     long sp = Stopwatch.difference();
 *     System.out.println("时间差: " + sp);
 * </code>
 *
 * 秒表
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class Stopwatch {

    /**
     * 上次计时时间戳
     */
    private static final ThreadLocal<Long> LAST_TIME = new ThreadLocal<>();

    /**
     * 记录一个时间
     */
    public static long record() {
        //计时
        long now = System.currentTimeMillis();
        LAST_TIME.set(now);
        return now;
    }

    /**
     * 获取上次计时时间
     */
    public static long getLastTime(){
        return LAST_TIME.get();
    }

    /**
     * 获取上次计时时间并清除
     */
    public static long getLastTimeAndRemove(){
        Long last = LAST_TIME.get();
        LAST_TIME.remove();
        return last;
    }

    /**
     * 返回当前与上次的时间差<br>
     * 如果上次没有时间差，则根据当前计时后的计时时间做自定义处理
     */
    public static long difference(Function<Long, Long> orElse) {
        Long last = LAST_TIME.get();
        // 清除其中的值
        LAST_TIME.remove();
        if (last == null) {
            return orElse.apply(record());
        } else {
            return System.currentTimeMillis() - last;
        }
    }

    /**
     * 返回当前与上次的时间差<br>
     * 如果没有计时，则默认返回-1
     */
    public static long difference() {
        return difference(l -> -1L);
    }

    /**
     * 返回当前与上次的时间差<br>
     * 如果没有计时，则抛出异常
     */
    public static long differenceOrThrow() {
        return difference(l -> {
            throw new NoRecordTimeException("nowTime: " + l);
        });
    }


    /**
     * 没有记录时间异常
     */
    public static class NoRecordTimeException extends RuntimeException {
        public NoRecordTimeException() {
        }

        public NoRecordTimeException(String message) {
            super(message);
        }

        public NoRecordTimeException(String message, Throwable cause) {
            super(message, cause);
        }

        public NoRecordTimeException(Throwable cause) {
            super(cause);
        }

        public NoRecordTimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }

}
