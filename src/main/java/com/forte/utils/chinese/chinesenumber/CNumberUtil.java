package com.forte.utils.chinese.chinesenumber;


import com.forte.utils.chinese.chinesenumber.formatter.*;

import java.util.Arrays;
import java.util.List;

/**
 * 对外接口类
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/16 10:16
 * @since JDK1.8
 **/
public class CNumberUtil implements CNumberList {

    /**
     * 转化为数字
     *
     * @param chinese
     * @return
     */
    public static <T extends Number> CNumber<T> toNumber(String chinese) {
        //去空
        chinese = chinese.trim();

        //转化为字符串数组
        String[] ch = new String[chinese.length()];
        List<String> list = Arrays.asList(CHINESE_NUMS);
        char[] chars = chinese.toCharArray();

        //是否为负数
        boolean isNegative = false;

        //是否为小数
        boolean isPoint = false;

        for (int i = 0; i < chars.length; i++) {
            String c = chars[i] + "";
            //如果有任意字符不存在于中文数字数组中，报错
            if (!list.contains(c)) {
                throw new RuntimeException(new NumberFormatException("存在非汉字数字:" + c));
            }

            //如果首位为负数标识
            if (i == 0 && c.equals(NEGATIVE)) {
                //标记负数
                isNegative = true;
            }


            //如果出现了小数点标识，记录
            if (i > 0 && c.equals(POINT)) {
                isPoint = true;
            }

            //如果首位出现了 点，报错
            if ((!isPoint) && i == 0 && c.equals(POINT)) {
                throw new RuntimeException(new NumberFormatException("小数点标识错误"));
            }
            //如果负数标识没有出现在首字母或已经标记过，报错
            if (isNegative && i > 0 && c.equals(NEGATIVE)) {
                throw new RuntimeException(new NumberFormatException("负数标识错误"));
            }
            ch[i] = c;
        }

        //解析并返回结果
        return parseAndFormat(ch, isNegative, isPoint);
    }


    /**
     * 解析汉字字符，判断数字长度并进行分配
     */
    private static <T extends Number> CNumber<T> parseAndFormat(String[] chineseArray, boolean negative, boolean point) {
        return (CNumber<T>) getFormatter(chineseArray, negative, point).parse();
    }


    /**
     * 获取解析器
     */
    private static <T extends Number> CNumberFormatter<T> getFormatter(String[] chineseArray, boolean negative, boolean point) {
        /*
            首先判断是否存在10倍以上的中文汉字，如果只有代表数字的数字(没有白十千之类的)则使用BigInt
            如果存在点，直接使用Float
            其次判断是否有亿，如果有亿且亿左边有十或以上的倍数，则使用Long，否则使用int
         */


        List<String> chineseList = Arrays.asList(chineseArray);

        if (chineseList.stream().noneMatch(c -> C2N_10_UP.keySet().stream().anyMatch(k -> k.equals(c)))) {
            if (point) {
                return (CNumberFormatter<T>) new CBigDecimalOnlyNumFormatter(chineseArray, negative);
            } else {
                return (CNumberFormatter<T>) new CBigIntFormatter(chineseArray, negative);
            }
        }

        if (point) {
            //如果过亿，使用CBigDecimal
            if (chineseList.contains("亿")) {
                return (CNumberFormatter<T>) new CBigDecimalFormatter(chineseArray, negative);
            } else {
                return (CNumberFormatter<T>) new CFloatFormatter(chineseArray, negative);
            }
        } else {
            //不是浮点类型
            boolean contains = chineseList.contains("亿");
            String join = String.join("", chineseArray);
            int indexOf = join.indexOf("亿");
            boolean moreThan10 = false;
            if(indexOf >= 0){
                String substring = join.substring(0, indexOf);
                moreThan10 = C2N_10_UP.entrySet().stream().filter(e -> e.getValue() >= C2N_10_UP.get("十")).anyMatch(e -> substring.contains(e.getKey()));
            }

            //如果十亿或以上
            if (contains && moreThan10) {
                return (CNumberFormatter<T>) new CLongFormatter(chineseArray, negative);
            } else {
                return (CNumberFormatter<T>) new CIntegerFormatter(chineseArray, negative);
            }
        }
    }

}
