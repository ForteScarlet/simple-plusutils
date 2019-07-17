package com.forte.utils.chinese.chinesenumber;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 静态资源类
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/16 11:49
 * @since JDK1.8
 **/
public interface CNumberList {

    /**
     * 0-9汉字对应数字
     */
    Map<String, Integer> C2N_0_9 = new HashMap<String, Integer>() {{
        put("〇", 0);
        put("零", 0);
        put("一", 1);
        put("壹", 1);
        put("弌", 1);
        put("二", 2);
        put("贰", 2);
        put("两", 2);
        put("弍", 2);
        put("三", 3);
        put("叁", 3);
        put("弎", 3);
        put("四", 4);
        put("肆", 4);
        put("五", 5);
        put("伍", 5);
        put("六", 6);
        put("陆", 6);
        put("七", 7);
        put("柒", 7);
        put("八", 8);
        put("捌", 8);
        put("九", 9);
        put("玖", 9);
    }};

    /**
     * 0-9汉字对应数字
     */
    Map<String, Integer> C2N_ONLY_0_9 = new HashMap<String, Integer>() {{
        put("〇", 0);
        put("零", 0);
        //可能会使用此说法
        put("幺", 1);
        put("一", 1);
        put("壹", 1);
        put("弌", 1);
        put("二", 2);
        put("两", 2);
        put("贰", 2);
        put("弍", 2);
        put("三", 3);
        put("叁", 3);
        put("弎", 3);
        put("四", 4);
        put("肆", 4);
        put("五", 5);
        put("伍", 5);
        put("六", 6);
        put("陆", 6);
        put("七", 7);
        put("柒", 7);
        put("八", 8);
        put("捌", 8);
        put("九", 9);
        put("玖", 9);
    }};

    /**
     * 10以上的倍数
     */
    Map<String, Long> C2N_10_UP = new HashMap<String, Long>() {{
        put("十", 10L);
        put("拾", 10L);
        put("廿", 20L);
        put("卅", 30L);
        put("卌", 40L);
        put("百", 100L);
        put("佰", 100L);
        put("皕", 200L);
        put("皕廿", 220L);
        put("皕卅", 230L);
        put("皕卌", 240L);
        put("千", 1000L);
        put("仟", 1000L);
        put("万", 10000L);
        put("十万", 100000L);
        put("百万", 1000000L);
        put("千万", 10000000L);
        put("亿", 100000000L);
        put("十亿", 1000000000L);
        put("百亿", 10000000000L);
        put("千亿", 100000000000L);
        put("兆",    1000000000L);
        put("十兆", 100000000000L);
        put("百兆", 10000000000000L);
        put("千兆", 1000000000000000L);
    }};


    /** 负数汉字 */
    String NEGATIVE = "负";

    /** 小数标识 */
    String POINT = "点";

    /**
     * 中文中的数字所使用的汉字
     */
    //{"〇", "一", "二", "三", "四", "五", "六", "七", "八", "九", "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖", "弌", "弍", "弎", "十", "二十", "三十", "四十", "百", "皕", "千", "万", "亿", "拾", "廿", "卅", "卌", "佰", "仟", "点", "负"};
    String[] CHINESE_NUMS = Stream.concat(Stream.concat(C2N_ONLY_0_9.keySet().stream(), C2N_10_UP.keySet().stream()), Stream.of(NEGATIVE, POINT)).toArray(String[]::new);

    default boolean is0_9(String s){
       return C2N_0_9.keySet().contains(s);
    }

    default boolean is10(String s){
        return C2N_10_UP.keySet().contains(s);
    }

    default boolean isOnly0_9(String s){
        return C2N_ONLY_0_9.keySet().contains(s);
    }

    default Integer get0_9Num(String c){
        return C2N_0_9.getOrDefault(c, C2N_ONLY_0_9.get(c));
    }

    default Long get10Num(String c){
        return C2N_10_UP.get(c);
    }

}
