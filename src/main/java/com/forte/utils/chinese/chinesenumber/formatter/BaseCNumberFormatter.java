package com.forte.utils.chinese.chinesenumber.formatter;


import com.forte.utils.chinese.chinesenumber.CNumberList;

import java.math.BigDecimal;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/16 13:28
 * @since JDK1.8
 **/
public abstract class BaseCNumberFormatter<N extends Number> implements CNumberFormatter<N>, CNumberList {

    private final String[] CHINESESTR;
    private final boolean NEGATIVE;

    public BaseCNumberFormatter(String[] chineseStr, boolean negative) {
        this.CHINESESTR = chineseStr;
        this.NEGATIVE = negative;
    }

    public String[] getChineseStrArr() {
        return CHINESESTR;
    }

    public String getChineseStr(){
        return String.join("", CHINESESTR);
    }

    public boolean isNegative(){
        return NEGATIVE;
    }

    public abstract boolean isFloat();

    protected abstract BigDecimal baseParse(String[] arr);

    /**
     * 转化
     * @return
     */
    protected BigDecimal parseAsBigDecimal(){
        //如果有小数点，切割
        BigDecimal result;
        if(isFloat()){
            String[] split = getChineseStr().split(POINT);
            //转化
            String[] left = new String[split[0].length()];
            char[] chars1 = split[0].toCharArray();
            for (int i = 0; i < left.length; i++) {
                left[i] = chars1[i]+"";
            }

            String[] right = new String[split[1].length()];
            char[] chars2 = split[1].toCharArray();
            for (int i = 0; i < right.length; i++) {
                right[i] = chars2[i]+"";
            }

            result = baseParse(left).add(new BigDecimal("0." + baseParse(right)));
        }else{
            result = baseParse(getChineseStrArr());
        }

        //如果是负数
        if (isNegative()) {
            result = result.multiply(num(-1));
        }
        return result;
    }


    protected BigDecimal num(String num){
        return new BigDecimal(num);
    }
    protected BigDecimal num(long num){
        return BigDecimal.valueOf(num);
    }
    protected BigDecimal num(int num){
        return BigDecimal.valueOf(num);
    }
    protected BigDecimal num(double num){
        return BigDecimal.valueOf(num);
    }
}
