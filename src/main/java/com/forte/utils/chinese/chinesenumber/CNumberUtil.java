package com.forte.utils.chinese.chinesenumber;


import com.forte.utils.chinese.chinesenumber.formatter.*;
import com.forte.utils.reflect.FieldUtils;
import com.forte.utils.reflect.MethodUtil;

import javax.script.ScriptException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.forte.utils.chinese.chinesenumber.CNumberList.*;

/**
 * 对外接口类
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/16 10:16
 * @since JDK1.8
 **/
public class CNumberUtil implements CNumberList {

    /**
     * 将中文数字转化为真正的数字
     * @param chinese 中文数字
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
            char ci = chars[i];
            String c = ci + "";
            //如果有任意字符不存在于中文数字数组中, 尝试转化为汉字字符，如果无法转化，抛出异常
            if (!list.contains(c)) {
                String toC = tryToC(ci);
                if (toC == null) {
                    // 获取索引位置的值
                    String indexOf = Stream.iterate(0, index -> index += 1)
                            .limit(i)
                            .map(s -> " ")
                            .reduce((old, val) -> old += val).orElse("") + '^';

                    throw new RuntimeException(new NumberFormatException("存在非汉字数字:" + c + "; index: " + i + "\n" +
                            chinese + "\n" + indexOf));
                } else {
                    c = toC;
                }
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
     * 解析可能存在运算符的中文字符并计算结果 <br>
     * 假如结果是布尔值，则true:1，false:0 <br>
     *
     */
    public static CNumber toCalculation(String chinese) throws ScriptException {
        //先进行转化
        chinese = replaceOperators(chinese);
        //当前字符串拼接
        StringBuilder str = new StringBuilder();
        //结果集拼接
        StringBuilder numberAppend = new StringBuilder();
        chinese.chars().forEach(ci -> {
            char c = (char) ci;
            if (isOperators(c)) {
                //如果是运算符，结束拼接并尝试进行转化
                if (str.length() > 0) {
                    numberAppend.append(toNumber(str.toString()));
                }
                numberAppend.append(c);
                //清空
                str.delete(0, str.length());
            } else {
                //是非运算符
                str.append(c);
            }
        });
        if (str.length() > 0) {
            numberAppend.append(toNumber(str.toString()));
        }

        Object eval = MethodUtil.eval(numberAppend.toString());
        //判断其类型
        if (FieldUtils.isChild(eval.getClass(), Boolean.class)) {
            // 如果是布尔值，返回布尔类型值
            return parseToCNumber((Boolean) eval);
        }
        //是否为浮点
        boolean isFl = false;
        //是否为负数
        boolean isNa = false;

        if (FieldUtils.isChild(eval.getClass(), java.lang.Number.class)) {
            Number evalNumber = (Number) eval;
            if (evalNumber.longValue() < 0) {
                isNa = true;
            }

            if (
                    eval.getClass().equals(java.lang.Float.class) ||
                            eval.getClass().equals(java.lang.Double.class)
            ) {
                //是个浮点数
                isFl = true;
            }
            return parseToCNumber(evalNumber, isFl, isNa);
        }


        if (FieldUtils.isBasic(eval.getClass())) {
            // 是基础数据类型
            if (
                    eval.getClass().equals(short.class) ||
                            eval.getClass().equals(int.class) ||
                            eval.getClass().equals(byte.class)
            ) {
                int evalVal = (int) eval;
                return parseToCNumber(evalVal, false, evalVal < 0);
            } else if (eval.getClass().equals(long.class)) {
                long evalVal = (long) eval;
                return parseToCNumber(evalVal, false, evalVal < 0);
            } else if (eval.getClass().equals(double.class)) {
                double evalVal = (double) eval;
                return parseToCNumber(evalVal, true, evalVal < 0);
            } else if (eval.getClass().equals(float.class)) {
                float evalVal = (float) eval;
                return parseToCNumber(evalVal, true, evalVal < 0);
            } else {
                //最后一个基础数据类型： char
                char evalVal = (char) eval;
                return new CNumber<>(Character.toString(evalVal), (int) evalVal, false, false, new BigDecimal(String.valueOf((int) evalVal)));
            }
        }

        // 不是基础数据类型、不是Number类型、不是boolean类型，抛出异常
        throw new NumberFormatException("无法将结果转化为" + CNumber.class + ": " + eval);
    }


    /**
     * 将一个数字类型转化为CNumber类型
     *
     * @param number number对象
     */
    private static <T extends Number> CNumber<T> parseToCNumber(T number, boolean isFloat, boolean negative) {
        String numVal = String.valueOf(number);
        return new CNumber<>(numVal, number, isFloat, negative, new BigDecimal(numVal));
    }

    /**
     * 将一个布尔值类型转化为CNumber类型
     * true:1 , false:0
     *
     * @param booleanVal 布尔值
     */
    private static CNumber<Integer> parseToCNumber(boolean booleanVal) {
        return CNumber.ofBooleanNumber(booleanVal);
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
            if (indexOf >= 0) {
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
