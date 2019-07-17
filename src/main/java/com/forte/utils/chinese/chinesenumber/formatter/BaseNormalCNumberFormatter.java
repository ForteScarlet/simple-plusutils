package com.forte.utils.chinese.chinesenumber.formatter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/16 15:47
 * @since JDK1.8
 **/
public abstract class BaseNormalCNumberFormatter<N extends Number> extends BaseCNumberFormatter<N> {
    public BaseNormalCNumberFormatter(String[] chineseStr, boolean negative) {
        super(chineseStr, negative);
    }

    //parseAsBigDecimal

//    /**
//     * 转化
//     * @return
//     */
//    protected BigDecimal parseAsBigDecimal(){
//        //如果有小数点，切割
//        if(isFloat()){
//            String[] split = getChineseStr().split(POINT);
//            //转化
//            String[] left = new String[split[0].length()];
//            char[] chars1 = split[0].toCharArray();
//            for (int i = 0; i < left.length; i++) {
//                left[i] = chars1[i]+"";
//            }
//
//            String[] right = new String[split[1].length()];
//            char[] chars2 = split[1].toCharArray();
//            for (int i = 0; i < right.length; i++) {
//                right[i] = chars2[i]+"";
//            }
//
//            return baseParse(left).add(new BigDecimal("0." + baseParse(right)));
//        }else{
//            return baseParse(getChineseStrArr());
//        }
//    }

    /**
     * 使用BigDecimal对中文数字进行解析并返回BigDecimal对象
     */
    @Override
    protected BigDecimal baseParse(String[] arr) {
        //总值集合
        List<BigDecimal> numbers = new ArrayList<>();
        //总值
        BigDecimal number = BigDecimal.ZERO;
        //间值
        BigDecimal inner = BigDecimal.ZERO;

        //上一个数字的倍数，用于检测非法同级倍数
        int lastLength = -1;

        //查询第一个位数最大的,一般也是整个数里面位数最大的
        int firstMaxLength = -1;

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < arr.length; ) {
            int add = 0;
            //拼接当前
            sb.append(arr[i + add++]);
            //如果是，则继续遍历追加
            while((i+add < arr.length) && is10(sb.toString() + arr[i + add])){
                sb.append(arr[i + add++]);
            }

            i += add;
            String finalStr = sb.toString();
            sb.delete(0, sb.length());

            Long num = get10Num(finalStr);
            if(num != null){
                firstMaxLength = Math.max(firstMaxLength, num.toString().length());
            }

        }


        // 上一次间值的位数
        int maxInnerLength = -1;

        // 上一次是否也是普通数字，当为是的时候，间值使用字符串拼接
        boolean lastIsNum = false;

        //数组
//        String[] arr = getChineseStrArr();
        //字符拼接
        StringBuilder s = new StringBuilder();

        //遍历字符数组，手动操做索引值
        for (int i = 0; i < arr.length; ) {
            //当前字符
            String c = arr[i];
            //判断是0-9还是10
            if (is0_9(c) || isOnly0_9(c)) {
                if(lastIsNum){
                    inner = new BigDecimal(inner.toString() + num(get0_9Num(c)));
                }else{
                    //是0-9, 不做拼接，间值做加法
                    inner = inner.add(num(get0_9Num(c)));
                }
                i++;
                lastIsNum = true;
            } else if (is10(c)) {
                lastIsNum = false;
                //是10
                int add = 0;

                do {
                    s.append(arr[i + add]);
                    add++;

                    //如果已经是最后一个索引位了
                    if (i == arr.length - 1) {
                        break;
                    }

                } while ((i + add) < arr.length && is10(s.toString() + arr[i + add]));
                //循环结束，记录最终结果并清空拼接
                if (s.length() > 0) {
                    String str10 = s.toString();
                    s.delete(0, s.length());
                    //获取倍数
                    BigDecimal num = num(get10Num(str10));
                    int thisInnerLength = num.toString().length();
                    //如果为-1，初始化长度
                    if (maxInnerLength == -1) {
                        maxInnerLength = thisInnerLength;
                    }

                    //如果是'十'且inner没有数，这比较特殊，先在inner里面增加1
                    if("十".equals(str10) && inner.equals(BigDecimal.ZERO)){
                        inner = BigDecimal.ONE;
                    }

                    //如果出现同级数字，抛出异常
                    if(lastLength == thisInnerLength){
                        throw new NumberFormatException("错误的同级数字:" + str10);
                    }

                    lastLength = thisInnerLength;

                    if (thisInnerLength > maxInnerLength || thisInnerLength == firstMaxLength) {
                        //如果位数出现更大的，初始化最大位数
                        maxInnerLength = -1;
                        //先将间值加入总值
                        number = number.add(inner);
                        //总值乘以倍数
                        number = number.multiply(num);
                        //保存当前总值并准备下一个总值对象
                        numbers.add(number);
                        number = BigDecimal.ZERO;

                    } else {
                        number = number.add(inner.multiply(num));
                    }

                    //记录上一次间值的位数
//                    maxInnerLength = thisInnerLength;
                    inner = BigDecimal.ZERO;
                    //增加索引位
                    i += add;
                }
            } else {
                //是未知, 加0处理
                number = number.add(num(0));
                System.err.println("出现未知数:" + c);
                i++;
            }
        }

        BigDecimal result = BigDecimal.ZERO;

        for (BigDecimal b : numbers) {
            result = result.add(b);
        }
        result = result.add(number);
        result = result.add(inner);

        return result;
    }


}
