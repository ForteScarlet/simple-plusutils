package com.forte.utils.basis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * {@link String} 操作工具类
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ExStringUtils extends CharSequenceUtils {

    /**
     * 是否为空（包括全部为空字符的情况）
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    /**
     * 是否为空（包括全部为空字符的情况）
     *
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s) {
        return s != null && s.trim().length() > 0;
    }


    /**
     * 获取一个单词的全部排列组合，数量一般为 word.length 的阶乘量
     *
     * @param word 要进行排列的字符串
     * @return 排列结果
     */
    public static String[] getPermutation(String word) {
        // 保存结果的数组
        String[] s = new String[(int) ExMath.factorial(word.length())];

        AtomicInteger index = new AtomicInteger(0);
        getPermutation(new StringBuilder(), word, new boolean[word.length()], w -> {
            s[index.getAndAdd(1)] = w;
        });

        return s;
    }

    /**
     * 递归获取字符串所有排列组合
     *
     * @param out  拼接用的StringBuilder
     * @param in   字符串
     * @param used 记录是否拼接过
     * @param s    每一个组合的输出
     */
    private static void getPermutation(StringBuilder out, String in, boolean[] used, Consumer<String> s) {
        if (out.length() == in.length()) {
            s.accept(out.toString());
            return;
        }
        for (int i = 0; i < in.length(); i++) {
            if (used[i]) continue;
            out.append(in.charAt(i));
            used[i] = true;
            getPermutation(out, in, used, s);
            used[i] = false;
            out.setLength(out.length() - 1);
        }
    }

    /**
     * 获取集合的所有切割可能。
     * <code>
     * abc:
     * - ab
     * - ac
     * - a
     * - b
     * - c
     * - bc
     * </code>
     * @param list 元素列表
     */
    public static <T> List<List<T>> getCombinations(List<T> list) {
        List<List<T>> result = new ArrayList<>();
        long n = (long) Math.pow(2, list.size());
        List<T> combine;
        for (long l = 0L; l < n; l++) {
            combine = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if ((l >>> i & 1) == 1)
                    combine.add(list.get(i));

            }
            if (!combine.isEmpty())
                result.add(combine);
        }
        return result;
    }

    /**
     * 获取集合的所有切割可能。
     * <code>
     * abc:
     * - ab
     * - ac
     * - a
     * - b
     * - c
     * - bc
     * </code>
     * @param list 元素列表
     */
    public static <T> List<List<T>> getCombinations(T... list) {
        List<List<T>> result = new ArrayList<>();
        long n = (long) Math.pow(2, list.length);
        List<T> combine;
        for (long l = 0L; l < n; l++) {
            combine = new ArrayList<>();
            for (int i = 0; i < list.length; i++) {
                if ((l >>> i & 1) == 1)
                    combine.add(list[i]);

            }
            if (!combine.isEmpty())
                result.add(combine);
        }
        return result;
    }



}
