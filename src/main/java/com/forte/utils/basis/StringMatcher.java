package com.forte.utils.basis;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 *
 * 字符串匹配对象
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class StringMatcher {

    private static final StringMatcher EMPTY = new StringMatcher();

    /** charMatchNode节点集合 */
    private final Map<Character, CharMatchNode> matchMap;

    private final Character head;

    // 寻找节点
    public CharMatchNode find(char c){
        return matchMap.get(c);
    }

    public Character getHead(){
        return head;
    }

//    /** 查询索引位，默认初始值为0 */
//    private final AtomicInteger index = new AtomicInteger(0);
//
//    /**
//     * 查询到的符合条件的值的索引位置坐标点
//     *
//     * 坐标点仅使用x,y
//     */
//    private final List<Coordinate<Integer>> foundIndex = new ArrayList<>();



    public static StringMatcher getInstance(Character head, String... words){
        if(words.length == 0){
            return EMPTY;
        }else{
            return new StringMatcher(head, words);
        }
    }

    public static StringMatcher getInstance(String... words){
        if(words.length == 0){
            return EMPTY;
        }else{
            return new StringMatcher(words);
        }
    }



    private static Map<Character, char[][]> toMap(String[] srr){
        Map<Character, char[][]> charMap = new HashMap<>(srr.length / 2);
        for (String s : srr) {
            if(s == null)
                s = "null";
            if(s.length() > 0){
                char head = s.charAt(0);
                char[] crr = new char[s.length() - 1];
                s.getChars(1, s.length(), crr, 0);

                // 放入map
                char[][] chars = charMap.get(head);
                if(chars == null){
                    charMap.put(head, new char[][]{crr});
                }else{
                    // 存在，合并
                    int index = chars.length;
                    chars = Arrays.copyOf(chars, index + 1);
                    chars[index] = crr;
                    // 放入
                    charMap.put(head, chars);
                }

            }
        }


        return charMap;
    }


    StringMatcher(String... words){
        this(null, words);
    }


    StringMatcher(Character head, String... words){
        if(words.length == 0){
            // 空值
            this.head = '\001';
            this.matchMap = new HashMap<>();
        }else{
            // 有值
            this.head = head;
            // 转化为节点列表
            this.matchMap = toMap(words).entrySet().stream().map(e -> {
                CharMatchNode node = new CharMatchNode(e.getKey(), e.getValue());
                return new AbstractMap.SimpleEntry<>(e.getKey(), node);
            }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
    }

}
