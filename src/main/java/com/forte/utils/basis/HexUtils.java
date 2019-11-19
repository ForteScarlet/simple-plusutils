package com.forte.utils.basis;

/**
 * 进制转化器    <br>
 * 可以将数字转化为任意进制的数据，使用默认的大小写字母转化的化，支持2-62位   <br>
 * 可以自定义转化使用的字符，但是额外的字符不能是0-9a-zA-Z的字符           <br>
 * <br>
 * <code> 从10进制转化为xx进制 -> toHex(number, radix[, char[] otherChar]) </code><br>
 * <code> 从xx进制转化为10进制 -> unHex(number, radix[, char[] otherChar]) </code><br>
 * ([]中为可选参数。)
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class HexUtils {

    /**
     * 大概默认最高为62进制
     */
    private static final int DEFAULT_MAX = 10 + 26 + 26;   //62
    /**
     * 最小为2进制
     */
    private static final int DEFAULT_MIN = 2;

    /**
     * 空的字符数组
     */
    private static final char[] EMPTY_CHAR = new char[0];

    private static final int CHAR_0 = '0';
    private static final int CHAR_a = 'a';
    private static final int CHAR_A = 'A';

    /**
     * 各位数代表的字符, 0-9a-zA-Z
     */
    private static final char[] DEFAULT_62CHAR;

    // 加载资源
    static {
        // [0, 62) 位数上的各个代替字符
        DEFAULT_62CHAR = new char[DEFAULT_MAX];
        for (int i = 0; i < DEFAULT_MAX; i++) {
            if (i <= 9) {
                // [2,9]
                DEFAULT_62CHAR[i] = (char) (CHAR_0 + (i));
            } else if (i < 36) {
                // [10,36] a-z
                DEFAULT_62CHAR[i] = (char) (CHAR_a + (i - 10));
            } else {
                // [37,62] A-Z
                DEFAULT_62CHAR[i] = (char) (CHAR_A + (i - 36));
            }
        }
    }


    /**
     * 转化为xx进制, 最大为62进制，最小为2进制。<br>
     * 当进制数量在 2~(10 + 26)以内的时候，进制结果的字符没有大小之分<br>
     * 例如：<code>10a</code>
     * 当进制 > (10+26) 的时候，进制结果有大小之分，且大写字符值顺位大于小写字符值
     * 例如：<code>10Aa</code>
     *
     * @param number 10进制的数
     * @param radix  进制，1-9就是1-9，10以后就是abc,36之后就是ABC 0-9 + abc + ABC = radix
     */
    public static String toHex(long number, int radix) {
        return toHex(number, radix, EMPTY_CHAR);
    }


    public static String toHex(long number, int radix, char[] diy) {

        // 进制最小不能小于2, 最大不能大于diy + default
        int maxLength = DEFAULT_MAX + diy.length;
        if (radix < DEFAULT_MIN || radix > maxLength) {
            throw new IllegalArgumentException("error radix, must in [2, " + maxLength + "] but: " + radix);
        }

        // 判断diy是否不在0-9a-zA-Z
        for (int i = 0; i < diy.length; i++) {
            int c = diy[i];
            if (isDefault(c)) {
                throw new IllegalArgumentException("diy char can not in [0-9a-zA-Z] but '" + (char) c + "' in " + i + "");
            }
        }

        // 构建一个diy数组
        char[] newArr;
        if (diy.length > 0) {
            newArr = new char[DEFAULT_62CHAR.length + diy.length];
            // 合并两个数组
            System.arraycopy(DEFAULT_62CHAR, 0, newArr, 0, DEFAULT_62CHAR.length);
            System.arraycopy(diy, 0, newArr, DEFAULT_62CHAR.length, diy.length);
        } else {
            newArr = DEFAULT_62CHAR;
        }


        // 进制，1-9就是1-9，10以后就是abc,36之后就是ABC 0-9 + abc + ABC = radix
        StringBuilder radixNumberStringBuilder = new StringBuilder();

        long numd = number;
        while (numd != 0) {
            int digNums = (int) (numd % radix);
            radixNumberStringBuilder.append(digNumStr(digNums, newArr));
            numd /= radix;
        }

        // 遍历是正向的，但是结果应该是反向的
        // 例如，235的15进制是10a
        //   余数      除法
        //  →  10    235 / 15 ↓
        //  ↑  0     15 / 15  ↓
        //  ↑  1     1        ↓
        //  ↑        0        ← 结束
        //   1    0   10
        //   ↓    ↓   ↓
        //   1    0   a
        //
        // 取反向数据
        return radixNumberStringBuilder.reverse().toString();
    }

    /**
     * 将一个某进制的字符串转化回10进制
     *
     * @param hexString 某进制的字符串
     * @param radix     进制
     * @return
     */
    public static long unHex(String hexString, int radix) {
        return unHex(hexString, radix, EMPTY_CHAR);
    }

    /**
     * 将一个某进制的字符串转化回10进制
     *
     * @param hexString 某进制的字符串
     * @param radix     进制
     * @param diy       如果进制大于62，则此为额外的进制代替符
     * @return
     */
    public static long unHex(String hexString, int radix, char[] diy) {
        // 进制最小不能小于2, 最大不能大于diy + default
        int maxLength = DEFAULT_MAX + diy.length;
        if (radix < DEFAULT_MIN || radix > maxLength) {
            throw new IllegalArgumentException("error radix, must in [2, " + maxLength + "] but: " + radix);
        }

        // 保存当前数值
        long sub = 0;

        char c;
        long d;
        int p;

        int length = hexString.length();
        for (int i = 0; i < length; i++) {
            c = hexString.charAt(i);
            // 将字符转换为对应的数字
            // 保存取出的最低位
            d = digStrToNum(c, diy);
            // 保存当前位权,从个位开始
            p = length - 1 - i;
            // 0^0 = 1
            if (d != 0) {
                sub += d * (long) (Math.pow(radix, p));
            }
        }
        return sub;
    }


    /**
     * 字符转为对应的值
     *
     * @param dig 某进制字符
     * @param diy 额外的自定义字符
     * @return
     */
    private static int digStrToNum(char dig, char[] diy) {
        // 判断这个值在不在default里
        if (isNum(dig)) {
            // 是个数字，从0开始计算，0-9
            return dig - CHAR_0;
        } else if (ischar(dig)) {
            // 是个小写字符，从10开始计算，a-z
            return dig - CHAR_a + 10;
        } else if (isCHAR(dig)) {
            // 是个大写字符，从36开始计算，A-Z
            return dig - CHAR_A + 36;
        } else {
            // 是自定义的
            for (int i = 0; i < diy.length; i++) {
                if (dig == diy[i]) {
                    return i + DEFAULT_62CHAR.length;
                }
            }
            throw new IllegalArgumentException("unknown char value: '" + dig + "' .");
        }
    }


    /**
     * 获取默认的char的值
     *
     * @param c
     * @return
     */
    public static int getDefaultValue(char c) {
        // 取0-9a-zA-Z对应的值
        if (isNum(c)) {
            // 是个数字，从0开始计算，0-9
            return c - CHAR_0;
        } else if (ischar(c)) {
            // 是个小写字符，从10开始计算，a-z
            return c - CHAR_a + 10;
        } else if (isCHAR(c)) {
            // 是个大写字符，从36开始计算，A-Z
            return c - CHAR_A + 36;
        } else {
            throw new IllegalArgumentException("'" + c + "' is not in 0-9a-zA-Z.");
        }

    }


    /**
     * 判断是否在0-9a-zA-Z
     *
     * @param c charValue
     * @return
     */
    private static boolean isDefault(char c) {
        return isNum(c)
                ||
                ischar(c)
                ||
                isCHAR(c);
    }

    private static boolean isNum(char c) {
        return c >= CHAR_0 && c <= (CHAR_0 + 9);
    }

    private static boolean ischar(char c) {
        return c >= CHAR_a && c <= (CHAR_a + 26);
    }

    private static boolean isCHAR(char c) {
        return c >= CHAR_A && c <= (CHAR_A + 26);
    }

    /**
     * 判断是否在0-9a-zA-Z
     *
     * @param c
     * @return
     */
    private static boolean isDefault(int c) {
        return isNum(c)
                ||
                ischar(c)
                ||
                isCHAR(c);
    }

    private static boolean isNum(int c) {
        return c >= CHAR_0 && c <= (CHAR_0 + 9);
    }

    private static boolean ischar(int c) {
        return c >= CHAR_a && c <= (CHAR_a + 26);
    }

    private static boolean isCHAR(int c) {
        return c >= CHAR_A && c <= (CHAR_A + 26);
    }

    /**
     * 一个数字 % 进制得到的数，并根据进制来返回对应的Char, 使用默认的进制转化格式
     *
     * @param residue 取%得到的结果
     */
    private static char digNumStr(int residue, char[] diy) {
        return toDigNumFunc(residue, diy);
    }


    /**
     * 根据进制获取一个进制对应值函数，2进制-62进制之间，其中超过10的，10=a,11=b以此类推，如果超过了小写字母则顺位大写字母。
     * 如果还是超了则借用diy的值。
     *
     * @param diy diy索引指向的各自代表超出62范围的值
     * @return
     */
    private static char toDigNumFunc(int di, char[] diy) {
        if (di < diy.length) {
            return diy[di];
        } else {
            throw new IllegalArgumentException("can not find radix value: " + ((char) (int) di));
        }
    }
}
