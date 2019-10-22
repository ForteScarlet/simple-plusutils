package com.forte.utils.basis;

import com.forte.utils.function.CharConsumer;
import com.forte.utils.stream.CharSequenceStream;

import java.util.PrimitiveIterator;
import java.util.StringJoiner;

/**
 *
 * {@link CharSequence} 操作工具类
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class CharSequenceUtils {

    /**
     * 去除前后空字符
     */
    public static CharSequence trim(CharSequence s){
        if(s instanceof String){
            return ((String) s).trim();
        }
        int len = s.length();
        int st = 0;

        while ((st < len) && (s.charAt(st) <= ' ')) {
            st++;
        }
        while ((st < len) && (s.charAt(len - 1) <= ' ')) {
            len--;
        }
        return ((st > 0) || (len < s.length())) ? s.subSequence(st, len) : s;
    }


    public static boolean isEmpty(CharSequence s){
        if(s == null){
            return true;
        }
        return trim(s).length() == 0;
    }


    public static void foreach(CharConsumer consumer, CharSequence s){
        s.chars().forEach(consumer);
    }

    /**
     * 转化为 <code> char[] </code>
     */
    public static char[] charArray(CharSequence s){
        if(s instanceof String){
            return ((String) s).toCharArray();
        }
        char[] crr = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            crr[i] = s.charAt(i);
        }
        return crr;
    }

    /**
     * 转化为{@link StringBuilder}
     */
    public static StringBuilder toBuilder(CharSequence s){
        return new StringBuilder(s);
    }
    /**
     * 转化为{@link StringBuilder}
     */
    public static StringBuilder toBuilder(int init, CharSequence s){
        return new StringBuilder(init).append(s);
    }
    /**
     * 转化为{@link StringBuilder}
     */
    public static StringBuilder toBuilder(String init, CharSequence s){
        return new StringBuilder(init).append(s);
    }

    /**
     * transform into{@link StringBuilder}
     */
    public static StringBuilder toBuilder(CharSequence init, CharSequence s){
        return new StringBuilder(init).append(s);
    }



}
