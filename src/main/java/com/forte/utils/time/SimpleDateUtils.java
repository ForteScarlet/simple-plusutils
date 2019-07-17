package com.forte.utils.time;


import java.time.*;
import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 简单的时间工具类
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class SimpleDateUtils {

    private static final Map<Integer, String> DAY_OF_WEEK_CHINESE_MAP = new LinkedHashMap<Integer, String>(7){{
       put(1, "星期一");
       put(2, "星期二");
       put(3, "星期三");
       put(4, "星期四");
       put(5, "星期五");
       put(6, "星期六");
       put(7, "星期日");
    }};

    /**
     * 获取向后推n个月的每个月的月初日期
     * 返回值为yyyy-MM-dd
     * 例如：假如今天是2019-4-30，则lastMonthByDay(3,true)返回值就是：
     * [
     * 2019-04-01,
     * 2019-03-01,
     * 2019-02-01
     * ]
     * @param nums 向前推移多少个月
     * @param fromNow 是从今天开始还是上个月开始
     */
   public static List<LocalDate> beforeMonthByDay(int nums, boolean fromNow){
       //获取本月第一天
       LocalDate now = LocalDate.now().withDayOfMonth(1);
       if(!fromNow){
           now = now.plusMonths(-1);
       }

       return Stream.iterate(now, d -> d.plusMonths(-1)).limit(nums).collect(Collectors.toList());
   }

    /**
     * 获取向后推n个月的每个月的月初日期
     * 返回值为yyyy-MM-dd
     * 例如：假如今天是2019-4-30，则lastMonthByDay(3,true)返回值就是：
     * [
     * 2019-04-01,
     * 2019-03-01,
     * 2019-02-01
     * ]
     * @param nums 向前推移多少个月
     * @param fromNow 是从今天开始还是上个月开始
     */
   public static List<String> beforeMonthByDayToString(int nums, boolean fromNow){
    return beforeMonthByDay(nums, fromNow).stream().map(LocalDate::toString).collect(Collectors.toList());
   }

    /**
     * 获取向后推n个月的每个月的月初日期
     * 返回值为yyyy-MM
     * 例如：假如今天是2019-4-30，则lastMonthByMonth(3,true)返回值就是：
     * [
     * 2019-04,
     * 2019-03,
     * 2019-02
     * ]
     * @param nums 向前推移多少个月
     * @param fromNow 是从今天开始还是上个月开始
     */
    public static List<YearMonth> beforeMonthByMonth(int nums, boolean fromNow){
        //获取本月第一天
        YearMonth now = YearMonth.now();
        if(!fromNow){
            now = now.plusMonths(-1);
        }

       return Stream.iterate(now, m -> m.plusMonths(-1)).limit(nums).collect(Collectors.toList());
    }

    /**
     * 获取向后推n个月的每个月的月初日期
     * 返回值为yyyy-MM
     * 例如：假如今天是2019-4-30，则lastMonthByMonth(3,true)返回值就是：
     * [
     * 2019-04,
     * 2019-03,
     * 2019-02
     * ]
     * @param nums 向前推移多少个月
     * @param fromNow 是从今天开始还是上个月开始
     */
    public static List<String> beforeMonthByMonthToString(int nums, boolean fromNow){
        return beforeMonthByMonth(nums, fromNow).stream().map(YearMonth::toString).collect(Collectors.toList());
    }


    /**
     * 向前推n天的数组
     * 例如，今天是2019-01-10,beforeDays(3, false)
     * 就是
     * [2019-01-09, 2019-01-08, 2019-01-07]
     * @param days      推几天
     * @param fromNow   是否包括今天
     * @return
     */
    public static List<LocalDate> beforeDays(int days, boolean fromNow){
        //今天
        LocalDate now = LocalDate.now();
        if(!fromNow){
            now = now.plusDays(-1);
        }
        return Stream.iterate(now, d -> d.plusDays(-1)).limit(days).collect(Collectors.toList());
    }

    /**
     * 向前推n天的数组
     * 例如，今天是2019-01-10,beforeDays(3, false)
     * 就是
     * [2019-01-09, 2019-01-08, 2019-01-07]
     * @param days      推几天
     * @param fromNow   是否包括今天
     * @return
     */
    public static List<String> beforeDaysToString(int days, boolean fromNow){
        return beforeDays(days, fromNow).stream().map(LocalDate::toString).collect(Collectors.toList());
    }

    /**
     * 向前推n天的数组
     * 例如，今天是2019-01-10,beforeDays(3, false)
     * 就是
     * [2019-01-09, 2019-01-08, 2019-01-07]
     *
     * beforeDaysToString(1, false, String[]::new)
     * @param days      推几天
     * @param fromNow   是否包括今天
     */
    public static String[] beforeDaysToString(int days, boolean fromNow, IntFunction<String[]> generator){
        return beforeDays(days, fromNow).stream().map(LocalDate::toString).toArray(generator);
    }

    /**
     * 向前推n年，类似于近5年、近6年这样子
     * @param years
     * @param fromNow
     * @return
     */
    public static List<Year> beforeYears(int years, boolean fromNow){
        Year now = Year.now();
        if(!fromNow){
            //如果不从今年开始，向前推1年
            now = now.plusYears(-1);
        }
        return Stream.iterate(now, d -> d.plusYears(-1)).limit(years).collect(Collectors.toList());
    }

    /**
     * 向前推n年，类似于近5年、近6年这样子
     */
    public static List<String> beforeYearsToString(int years, boolean fromNow){
        return beforeYears(years, fromNow).stream().map(Year::toString).collect(Collectors.toList());
    }

    /**
     * 将DayOfWeek转化为中文的星期字符串
     */
    public static String toChineseWeekDay(DayOfWeek dayOfWeek){
        int dayOfWeekValue = dayOfWeek.getValue();
        switch (dayOfWeekValue){
            case 1 :
            case 2 :
            case 3 :
            case 4 :
            case 5 :
            case 6 :
            case 7 : return DAY_OF_WEEK_CHINESE_MAP.get(dayOfWeekValue);
            default: return "未知";
        }
    }

    /**
     * 获取星期对应中文字符串数组
     */
    public static String[] getDayOfWeekChineseStr(){
        return DAY_OF_WEEK_CHINESE_MAP.values().toArray(new String[0]);
    }

    /** 获取某年所有月份 -yyyy-MM */
    public static List<YearMonth> getAllYearMonth(Year year){
        List<YearMonth> list = new ArrayList<>(12);
        for(int i = 1; i <= 12; i++){
            list.add(year.atMonth(i));
        }
        return list;
    }

    /** 获取某年所有月份 - yyyy-MM */
    public static List<YearMonth> getAllYearMonth(String yearStr){
        Year year = Year.parse(yearStr);
       return getAllYearMonth(year);
    }

    /** 获取某年所有月份 - yyyy-MM */
    public static List<String> getAllYearMonthToString(Year year){
        return getAllYearMonth(year).stream().map(YearMonth::toString).collect(Collectors.toList());
    }

    /** 获取某年所有月份 - yyyy-MM */
    public static List<String> getAllYearMonthToString(String yearStr){
        return getAllYearMonth(yearStr).stream().map(YearMonth::toString).collect(Collectors.toList());
    }

    /** 获取所有月份数组，01-12 */
    public static String[] getAllMonth(){
        return Stream.iterate(1, i -> i + 1).limit(12).map(i -> i >= 10 ? "" + i : "0" + i).toArray(String[]::new);
    }

    /** 获取所有月份数组，1-12，首部不补零 */
    public static String[] getAllMonthNo0(){
        return Stream.iterate(1, i -> i + 1).limit(12).map(Object::toString).toArray(String[]::new);
    }

    public static Year instantToYear(Instant instant){
        return Year.of(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).getYear());
    }


}
