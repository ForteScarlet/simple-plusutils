package com.forte.utils.regular;

/**
 *
 * 正则特殊符号
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public enum RegularSymbol {

    //**************** 正则起始结尾符 ****************//

    START("^"),
    END("$"),
    /** 转义符 */
    ESCAPE("\\"),

    //**************** 括号 ****************//

    BRACKETS_START("("),
    BRACKETS_END(")"),

    //**************** 数量相关正则 ****************//

    /** 任意数量 */
    NUM_ANY("*"),
    /** 至少一个 */
    NUM_MORE_THAN_ONE("+"),
    /** 任意数量 */
    NUM_MAYBE("?"),
    /** 数量区间值 */
    NUM_START("{"),
    NUM_END("}"),
    NUM_SPLIT(","),

    //**************** 或与、任意等匹配 ****************//

    /** 不包含匹配 开头 */
    MATCH_NOT_IN(BRACKETS_START + "?!"),
    /** 不包含匹配 结尾 */
    MATCH_NOT_IN_END(BRACKETS_END.symbol),

    MATCH_OR("|"),
    /** 多元素匹配 */
    MATCH_OR_ARRAY_START("["),
    MATCH_OR_ARRAY_REVERSE_START("[^"),
    /** 多元素匹配 */
    MATCH_OR_ARRAY_END("]"),


    //**************** 符号匹配 ****************//

    /** 任何非\r\n */
    MATCH_ANY_NOT_BLANK("."),

    /** 匹配一个字符边界 */
    MATCH_WORD_BOUNDARY("\\b"),
    /** 匹配一个非字符边界 */
    MATCH_NOT_WORD_BOUNDARY("\\B"),
    /** 匹配数字 */
    MATCH_NUMBER("\\d"),
    /** 不是数字 */
    MATCH_NOT_NUMBER("\\D"),
    /** 匹配换页符 */
    MATCH_NEW_PAGE("\\f"),
    /** 匹配换行符 */
    MATCH_NEW_LINE("\\n"),
    /** 匹配回车符 */
    MATCH_ENTER("\\r"),
    /** 匹配空白符，包括空格、制表符、换页符等。与 [ \f\n\r\t\v] 等效。 */
    MATCH_BLANK("\\s"),
    /** 匹配非空白字符 */
    MATCH_NOT_BLANK("\\S"),
    /** 匹配制表符 */
    MATCH_TABLE("\\t"),
    /** 匹配垂直制表符 */
    MATCH_VERTICAL_TABLE("\\v"),
    /** 匹配任何字类字符，包括下划线。与"[A-Za-z0-9_]"等效。 */
    MATCH_ANY_WORD("\\w"),
    /** 与任何非单词字符匹配。与"[^A-Za-z0-9_]"等效。 */
    MATCH_ANY_NOT_WORD("\\W"),



    ;


    private final String symbol;
    RegularSymbol(String symbol){
        this.symbol = symbol;
    }

    public String getSymbol(){
        return symbol;
    }

    //**************** 判断是否包含 ****************//

    public static boolean contains(String s){
        for (RegularSymbol v : values()) {
            if(v.symbol.equals(s)){
                return true;
            }
        }
        return false;
    }

    public static boolean contains(char c){
        for (RegularSymbol v : values()) {
                if((v.symbol.length() == 1) && (v.symbol.charAt(0) == c)){
                    return true;
                }
        }
        return false;
    }

    @Override
    public String toString(){
        return symbol;
    }

}
