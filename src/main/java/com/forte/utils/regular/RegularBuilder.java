package com.forte.utils.regular;

import java.util.function.Function;

/**
 * 正则拼接工具
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Deprecated
public class RegularBuilder {

//    //**************** 正则起始结尾符 ****************//
//
//    private static final String START = "^";
//    private static final String END = "$";
//
//    //**************** 括号 ****************//
//
//    private static final String BRACKETS_START = "(";
//    private static final String BRACKETS_END = ")";
//
//    //**************** 数量相关正则 ****************//
//
//    /** 任意数量 */
//    private static final String NUM_ANY = "*";
//    /** 至少一个 */
//    private static final String NUM_MORE_THAN_ONE = "+";
//    /** 任意数量 */
//    private static final String NUM_MAYBE = "?";
//    /** 数量区间值 */
//    private static final String NUM_START = "{";
//    private static final String NUM_END = "}";
//    private static final String NUM_SPLIT = ",";
//
//    //**************** 或与、任意等匹配 ****************//
//
//    /** 不包含匹配 开头 */
//    private static final String MATCH_NOT_IN = BRACKETS_START + "?!";
//    /** 不包含匹配 结尾 */
//    private static final String MATCH_NOT_IN_END = BRACKETS_END;
//
//    private static final String MATCH_OR = "|";
//    /** 多元素匹配 */
//    private static final String MATCH_OR_ARRAY_START = "[";
//    /** 多元素匹配 */
//    private static final String MATCH_OR_ARRAY_END = "]";
//
//
//    //**************** 符号匹配 ****************//
//
//    /** 任何非\r\n */
//    private static final String MATCH_ANY_NOT_BLANK = ".";

    private StringBuilder builder;

    private void initBuilder() {
        if (this.builder == null) {
            this.builder = new StringBuilder();
        }
    }
    private void initBuilder(Function<String, String> strFor) {
        initBuilder();
        this.builder = new StringBuilder(strFor.apply(this.builder.toString()));
    }

    private StringBuilder builder() {
        initBuilder();
        return builder;
    }

    /**
     * 根据某个格式格式化
     */
    private StringBuilder builder(Function<String, String> strFormat){
        initBuilder(strFormat);
        return this.builder;
    }







    //**************** 各种函数转化 ****************//

//    /**
//     * 或函数
//     * @return
//     */
//    private Function<String, String> orF(){
//
//    }




}
