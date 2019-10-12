package com.forte.utils.basis;

import java.util.ArrayList;
import java.util.Collections;
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
     * 在js语法中合法的部分运算字符等
     */
    private static char[] legalChars = {
            '!', '@', '#', '$', '%',
            '^', '&', '*', '(', ')',
            '-', '_', '+', '=', '/',
            '{', '}', '[', ']', '|',
            '\\', ':', ';', '"', '\'',
            ',', '.', '/', '<', '>',
            '1', '2', '3', '4', '5',
            '6', '7', '8', '9', '0', ' ',
            '\r', '\n', '\t'
    };

    /**
     * 尝试将非格式化计算语句进行转化，一般来讲，给非计算符号、数字等加上字符串
     */
    public static String toLegal(String s) {
        //上一个是不是字符类型
        boolean last = false;
        StringBuilder sb = new StringBuilder();
        big:
        for (char c : s.toCharArray()) {
            for (char le : legalChars) {
                if (c == le) {
                    if (last) {
                        //且如果上一个是非法，先拼接引号
                        sb.append('\"').append(c);
                        last = false;
                    } else {
                        sb.append(c);
                    }
                    continue big;
                }
            }
            //如果没有发现合法字符
            //如果是，判断上一个是不是
            if (last) {
                //如果上一个也是，直接拼接
                sb.append(c);
            } else {
                //不是，先拼接引号
                sb.append('\"').append(c);
                last = true;
            }
        }
        //最后看看是不是特殊结尾，是则拼接
        if (last) {
            sb.append('"');
        }
        return sb.toString();
    }


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
     * 将一个字符串等量的复制n倍长度<br>
     * 以下为多种方案：
     * <code>
     *  String.format("%0" + 5 + "d", 0).replace("0", "aaabc");
     *  <p>
     *  String.join("", Collections.nCopies(5, "aaabc"));
     *  <p>
     *  new String(new char[5]).replace("\0", "aaabc");
     *  <p>
     *  repeatString("aaabc", 5, "");//见后面的方法
     *  <p>
     *  执行次数1000_000
     *  <p>
     *  耗时毫秒
     *  1797
     *  167
     *  593
     *  142
     *  根据前面的几位大佬进行总结和测试，相对而言，2和4的耗时比较少，多次测试的结果4都比2用时更少一点。
     *  注重性能就选择2或4
     *  public static String repeatString(String str, int n, String seg) {
     *  StringBuffer sb = new StringBuffer();
     *  for (int i = 0; i < n; i++) {
     *  sb.append(str).append(seg);
     *  }
     *  return sb.substring(0, sb.length() - seg.length());
     *  }
     * </code>
     *
     * @param base 要复制的字符串
     */
    public static String repeat(String base, int times) {
        // 方法一 通过java8的Collections.nCopies方法创建一个重复List并合并。
        return String.join("", Collections.nCopies(times, base));
    }

    /**
     * @see #repeat(String, int)
     */
    public static String repeat(char base, int times) {
        return repeat(base+"", times);
    }

    /**
     * @see #repeat(String, int)
     */
    public static String repeat(int base, int times) {
        return repeat(String.valueOf(base), times);
    }

    /**
     * @see #repeat(String, int)
     */
    public static String repeat(Object base, int times) {
        return repeat(String.valueOf(base), times);
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
     *
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
     *
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
