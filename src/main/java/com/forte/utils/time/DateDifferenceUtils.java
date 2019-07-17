package com.forte.utils.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类，对日期进行各种操作
 * <ul>
 *     <li>计算两时间之间的差值：dateDiff</li>
 * </ul>
 * @author ForteScarlet
 */
public class DateDifferenceUtils {

    private static final long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
    private static final long nh = 1000 * 60 * 60;// 一小时的毫秒数
    private static final long nm = 1000 * 60;// 一分钟的毫秒数
    private static final long ns = 1000;// 一秒钟的毫秒数


    /**
     * 获取两个时间之间相差的各个时间类型
     *
     * @param start
     * @param end
     * @return
     */
    public static DateDiff dateDiff(Date start, Date end) {

        long t = end.getTime() - start.getTime();

        long day = 0;
        long hour = 0;
        long minute = 0;
        long second = 0;
        long millisecond = 0;

        day = t / nd;// 计算差多少天
        hour = t % nd / nh + day * 24;// 计算差多少小时
        minute = t % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
        second = t % nd % nh % nm / ns;// 计算差多少秒
        millisecond = t % nd % nh % nm % ns;

        //返回封装类，
        return new DateDiff(t, day, hour, minute, second, millisecond);
    }

    /**
     * 获取两个时间之间相差的各个时间类型
     *
     * @param start  开始时间
     * @param end    结束时间
     * @param format 格式化方法
     * @return
     */
    public static DateDiff dateDiff(String start, String end, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return dateDiff(simpleDateFormat.parse(start), simpleDateFormat.parse(end));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 时间差的封装类
     */
    public static class DateDiff {
        private long difference;
        private long day = 0;
        private long hour = 0;
        private long minute = 0;
        private long second = 0;
        private long millisecond = 0;

        /* —————— getter —————— */

        public long getDifference() {
            return difference;
        }

        public long getDay() {
            return day;
        }

        public long getHour() {
            return hour;
        }

        public long getMinute() {
            return minute;
        }

        public long getSecond() {
            return second;
        }

        public long getMillisecond() {
            return millisecond;
        }

        /**
         * 构造封装时间差结果
         *
         * @param day
         * @param hour
         * @param minute
         * @param second
         * @param millisecond
         */
        public DateDiff(long difference, long day, long hour, long minute, long second, long millisecond) {
            this.difference = difference;
            this.day = day;
            this.hour = hour;
            this.minute = minute;
            this.second = second;
            this.millisecond = millisecond;
        }
    }
}
