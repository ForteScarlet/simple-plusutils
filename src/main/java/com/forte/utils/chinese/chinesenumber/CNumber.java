package com.forte.utils.chinese.chinesenumber;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * 中文数字转化类
 */
public class CNumber<N extends Number> extends Number implements Comparable<CNumber> {

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
        this.REAL_NUM = realNum;
    }

    /* —————————— factory methods —————————— */

    public static <N extends Number> CNumber<N> ofNumber(String chineseStr, N num, BigDecimal realNum){
        return new CNumber<>(chineseStr, num, false, false, realNum);
    }

    public static <N extends Number> CNumber<N> ofFloatNumber(String chineseStr, N num, BigDecimal realNum){
        return new CNumber<>(chineseStr, num, true, false, realNum);
    }

    public static <N extends Number> CNumber<N> ofNegativeNumber(String chineseStr, N num, BigDecimal realNum){
        return new CNumber<>(chineseStr, num, false, true, realNum);
    }

    public static <N extends Number> CNumber<N> ofNegativeFloatNumber(String chineseStr, N num, BigDecimal realNum){
        return new CNumber<>(chineseStr, num, true, true, realNum);
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

    /**
     * Returns the value of the specified number as an {@code int},
     * which may involve rounding or truncation.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code int}.
     */
    @Override
    public int intValue() {
        return REAL_NUM.intValue();
    }

    /**
     * Returns the value of the specified number as a {@code long},
     * which may involve rounding or truncation.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code long}.
     */
    @Override
    public long longValue() {
        return REAL_NUM.longValue();
    }

    /**
     * Returns the value of the specified number as a {@code float},
     * which may involve rounding.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code float}.
     */
    @Override
    public float floatValue() {
        return REAL_NUM.floatValue();
    }

    /**
     * Returns the value of the specified number as a {@code double},
     * which may involve rounding.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code double}.
     */
    @Override
    public double doubleValue() {
        return REAL_NUM.doubleValue();
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     *                              <p>
     *                              由于可能使用到了N泛型理论上都已经实现了Comparable接口，所以直接强转就好
     */
    @Override
    public int compareTo(CNumber o) {
        return REAL_NUM.compareTo(o.REAL_NUM);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * The {@code equals} method implements an equivalence relation
     * on non-null object references:
     * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value
     * {@code x}, {@code x.equals(x)} should return
     * {@code true}.
     * <li>It is <i>symmetric</i>: for any non-null reference values
     * {@code x} and {@code y}, {@code x.equals(y)}
     * should return {@code true} if and only if
     * {@code y.equals(x)} returns {@code true}.
     * <li>It is <i>transitive</i>: for any non-null reference values
     * {@code x}, {@code y}, and {@code z}, if
     * {@code x.equals(y)} returns {@code true} and
     * {@code y.equals(z)} returns {@code true}, then
     * {@code x.equals(z)} should return {@code true}.
     * <li>It is <i>consistent</i>: for any non-null reference values
     * {@code x} and {@code y}, multiple invocations of
     * {@code x.equals(y)} consistently return {@code true}
     * or consistently return {@code false}, provided no
     * information used in {@code equals} comparisons on the
     * objects is modified.
     * <li>For any non-null reference value {@code x},
     * {@code x.equals(null)} should return {@code false}.
     * </ul>
     * <p>
     * The {@code equals} method for class {@code Object} implements
     * the most discriminating possible equivalence relation on objects;
     * that is, for any non-null reference values {@code x} and
     * {@code y}, this method returns {@code true} if and only
     * if {@code x} and {@code y} refer to the same object
     * ({@code x == y} has the value {@code true}).
     * <p>
     * Note that it is generally necessary to override the {@code hashCode}
     * method whenever this method is overridden, so as to maintain the
     * general contract for the {@code hashCode} method, which states
     * that equal objects must have equal hash codes.
     *
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     * @see #hashCode()
     * @see HashMap
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CNumber){
            return this.REAL_NUM.equals((((CNumber) obj).REAL_NUM));
        }else{
            return this.REAL_NUM.equals(obj);
        }
    }

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link HashMap}.
     * <p>
     * The general contract of {@code hashCode} is:
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during
     * an execution of a Java application, the {@code hashCode} method
     * must consistently return the same integer, provided no information
     * used in {@code equals} comparisons on the object is modified.
     * This integer need not remain consistent from one execution of an
     * application to another execution of the same application.
     * <li>If two objects are equal according to the {@code equals(Object)}
     * method, then calling the {@code hashCode} method on each of
     * the two objects must produce the same integer result.
     * <li>It is <em>not</em> required that if two objects are unequal
     * according to the {@link Object#equals(Object)}
     * method, then calling the {@code hashCode} method on each of the
     * two objects must produce distinct integer results.  However, the
     * programmer should be aware that producing distinct integer results
     * for unequal objects may improve the performance of hash tables.
     * </ul>
     * <p>
     * As much as is reasonably practical, the hashCode method defined by
     * class {@code Object} does return distinct integers for distinct
     * objects. (This is typically implemented by converting the internal
     * address of the object into an integer, but this implementation
     * technique is not required by the
     * Java&trade; programming language.)
     *
     * @return a hash code value for this object.
     * @see Object#equals(Object)
     * @see System#identityHashCode
     */
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
