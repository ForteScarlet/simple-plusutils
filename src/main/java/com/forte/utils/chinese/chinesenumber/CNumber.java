package com.forte.utils.chinese.chinesenumber;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * 中文数字转化类
 */
public class CNumber<N extends Number> extends Number implements Comparable<CNumber> {

    /** 此值是否代表了一个布尔值 */
    private final boolean isBoolean;

    /** 假如这个值代表了布尔值，那么此值代表了其布尔值的真正值 */
    private final boolean booleanVal;

    /** 数字对应的字符串 */
    private final String CHINESE_STR;

    /** 真正的NUM对象 */
    private final N NUM;

    /** 真正的数字对象 */
    private final BigDecimal REAL_NUM;

    /** 是否为浮点数 */
    private final boolean FLOAT_NUM;

    /** 是否为负数 */
    private final boolean NEGATIVE;

    /**
     * 构造
     */
    public CNumber(String chineseStr, N num, boolean floatNum, boolean negative, BigDecimal realNum) {
        this.CHINESE_STR = chineseStr;
        this.NUM = num;
        this.FLOAT_NUM = floatNum;
        this.NEGATIVE = negative;
        // 使BigDecimal 增加0, 可以在某些情况下将抽象值转化为真实值
        //例如 ： 1.0E+24 -> 1000000000000000000000000
        this.REAL_NUM = realNum.add(BigDecimal.ZERO);

        this.isBoolean = false;
        this.booleanVal = false;
    }

    /**
     * 构造 - 布尔值构造
     */
    private CNumber(boolean booleanVal, N num, BigDecimal realNum) {
        this.CHINESE_STR = String.valueOf(booleanVal);
        this.isBoolean = true;
        this.booleanVal = booleanVal;
        this.NUM = num;
        this.FLOAT_NUM = false;
        this.NEGATIVE = false;
        this.REAL_NUM = realNum;
    }

    /* —————————— factory methods —————————— */

    /** 一个值 */
    public static <N extends Number> CNumber<N> ofNumber(String chineseStr, N num, BigDecimal realNum){
        return new CNumber<>(chineseStr, num, false, false, realNum);
    }

    /** 一个浮点值 */
    public static <N extends Number> CNumber<N> ofFloatNumber(String chineseStr, N num, BigDecimal realNum){
        return new CNumber<>(chineseStr, num, true, false, realNum);
    }

    /** 一个负数值 */
    public static <N extends Number> CNumber<N> ofNegativeNumber(String chineseStr, N num, BigDecimal realNum){
        return new CNumber<>(chineseStr, num, false, true, realNum);
    }

    /** 一个负数浮点值 */
    public static <N extends Number> CNumber<N> ofNegativeFloatNumber(String chineseStr, N num, BigDecimal realNum){
        return new CNumber<>(chineseStr, num, true, true, realNum);
    }
    /** 一个布尔值 */
    public static CNumber<Integer> ofBooleanNumber(boolean booleanVal){
        return new CNumber<>(booleanVal, booleanVal ? 1 : 0, booleanVal ? BigDecimal.ONE : BigDecimal.ZERO);
    }

    /* —————————— getter & setter —————————— */

    /**
     * 是否为浮点数
     */
    public boolean isfloatNum() {
        return this.FLOAT_NUM;
    }

    /**
     * 是否为负数
     */
    public boolean isNegative() {
        return this.NEGATIVE;
    }

    /**
     * 获取Num对象
     */
    public N toNum(){
        return this.NUM;
    }

    public BigDecimal getRealNum(){
        return this.REAL_NUM;
    }

    /* —————————— override methods —————————— */

    @Override
    public int intValue() {
        return REAL_NUM.intValue();
    }

    @Override
    public long longValue() {
        return REAL_NUM.longValue();
    }

    @Override
    public float floatValue() {
        return REAL_NUM.floatValue();
    }

    @Override
    public double doubleValue() {
        return REAL_NUM.doubleValue();
    }

    @Override
    public int compareTo(CNumber o) {
        return REAL_NUM.compareTo(o.REAL_NUM);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CNumber){
            return this.REAL_NUM.equals((((CNumber) obj).REAL_NUM));
        }else{
            return this.REAL_NUM.equals(obj);
        }
    }

    @Override
    public int hashCode() {
        return this.REAL_NUM.hashCode();
    }

    /**
     * toString like BigDecimal.toString()
     * @return
     */
    @Override
    public String toString(){
        return REAL_NUM.toString();
    }

    /**
     * toString like Chinese
     */
    public String toChineseString(){
        return CHINESE_STR;
    }

    /**
     * toString like num
     */
    public String toNumString(){
        return NUM.toString();
    }



}
