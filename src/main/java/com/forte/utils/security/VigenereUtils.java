package com.forte.utils.security;

/**
 * 维吉尼亚加密解密工具
 *
 * 维吉尼亚密码（又译维热纳尔密码）是使用一系列凯撒密码组成密码字母表的加密算法，属于多表密码的一种简单形式。
 * —— <a href='https://baike.baidu.com/item/%E7%BB%B4%E5%90%89%E5%B0%BC%E4%BA%9A%E5%AF%86%E7%A0%81/4905472?fr=aladdin'>baidu baike</a>
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class VigenereUtils {

    /*
            arg1行 -> 的arg2对应密码
                  A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
                A A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
                B B C D E F G H I J K L M N O P Q R S T U V W X Y Z A
                C C D E F G H I J K L M N O P Q R S T U V W X Y Z A B
                D D E F G H I J K L M N O P Q R S T U V W X Y Z A B C
                E E F G H I J K L M N O P Q R S T U V W X Y Z A B C D
                F F G H I J K L M N O P Q R S T U V W X Y Z A B C D E
                G G H I J K L M N O P Q R S T U V W X Y Z A B C D E F
                H H I J K L M N O P Q R S T U V W X Y Z A B C D E F G
                I I J K L M N O P Q R S T U V W X Y Z A B C D E F G H
                J J K L M N O P Q R S T U V W X Y Z A B C D E F G H I
                K K L M N O P Q R S T U V W X Y Z A B C D E F G H I J
                L L M N O P Q R S T U V W X Y Z A B C D E F G H I J K
                M M N O P Q R S T U V W X Y Z A B C D E F G H I J K L
                N N O P Q R S T U V W X Y Z A B C D E F G H I J K L M
                O O P Q R S T U V W X Y Z A B C D E F G H I J K L M N
                P P Q R S T U V W X Y Z A B C D E F G H I J K L M N O
                Q Q R S T U V W X Y Z A B C D E F G H I J K L M N O P
                R R S T U V W X Y Z A B C D E F G H I J K L M N O P Q
                S S T U V W X Y Z A B C D E F G H I J K L M N O P Q R
                T T U V W X Y Z A B C D E F G H I J K L M N O P Q R S
                U U V W X Y Z A B C D E F G H I J K L M N O P Q R S T
                V V W X Y Z A B C D E F G H I J K L M N O P Q R S T U
                W W X Y Z A B C D E F G H I J K L M N O P Q R S T U V
                X X Y Z A B C D E F G H I J K L M N O P Q R S T U V W
                Y Y Z A B C D E F G H I J K L M N O P Q R S T U V W X
                Z Z A B C D E F G H I J K L M N O P Q R S T U V W X Y
         */

    private static final char A_CHAR = 'A';
    private static final char Z_CHAR = 'Z';
    private static final int  WORD_NUM = 26;

    /**
     * 加密密码表
     * 查询的是 {arg1}行的{arg2}对应字母
     * 一般，加密是密钥->arg1, 明文->arg2 <br>
     * 通过密钥行对应明文列得到交叉
     * @param arg1 arg1
     * @param arg2 arg2
     */
    private static char encryptTable(char arg1, char arg2) {
        // 转为大写
        arg1 = Character.toUpperCase(arg1);
        if (arg1 < A_CHAR || arg1 > Z_CHAR) {
            throw new IllegalArgumentException("Arg1 is not in the range of A-Z after capitalization: " + arg1 + "(" + (int) arg1 + ")");
        }
        arg2 = Character.toUpperCase(arg2);
        if (arg2 < A_CHAR || arg2 > Z_CHAR) {
            throw new IllegalArgumentException("Arg2 is not in the range of A-Z after capitalization: " + arg2 + "(" + (int) arg2 + ")");
        }

        // 计算结果并返回
        // 计算arg1 与 A 的差值
        int diffArg1 = arg1 - A_CHAR;
        // 计算arg2 与 A 的差值
        int diffArg2 = arg2 - A_CHAR;

        // 结果是差值相加再加A
        int diffSum = diffArg1 + diffArg2 + A_CHAR;

        // 如果大于了Z, 再回算，正常只会回算一次
        if (diffSum > Z_CHAR) {
            diffSum -= WORD_NUM;
        }

        return (char) diffSum;
    }

    /**
     * 解密密码表
     * 查询的是{arg1}行的结果{arg2}所对应的列
     * @param arg1  密钥字符
     * @param arg2  明文列
     * @return decrypt char
     */
    private static char decryptTable(char arg1, char arg2){
        // 转为大写
        arg1 = Character.toUpperCase(arg1);
        if (arg1 < A_CHAR || arg1 > Z_CHAR) {
            throw new IllegalArgumentException("Arg1 is not in the range of A-Z after capitalization: " + arg1 + "(" + (int) arg1 + ")");
        }
        arg2 = Character.toUpperCase(arg2);
        if (arg2 < A_CHAR || arg2 > Z_CHAR) {
            throw new IllegalArgumentException("Arg2 is not in the range of A-Z after capitalization: " + arg2 + "(" + (int) arg2 + ")");
        }
        /*
             a b
             0 1 2 3 4 ... 11 22 23 24 25
         a 0 0 1 2 3 4
         b 1 1 2 3 4 5
         c 2 2 3 4 5 6
         d 3 3 4 5 6 7
         e 4 4 5 6 7 8 ... 26 0  1  2  d
             e f

            3 + 26 = 29
            29 - 4 = 25

         */
        // arg1是某一行，计算arg2与arg1的差值
        // 计算char位数
        int diffArg1 = arg1 - A_CHAR;
        int diffArg2 = arg2 - A_CHAR;
        // 如果交叉字符比行字符小，补26
        if(diffArg2 < diffArg1){
            diffArg2 += WORD_NUM;
        }

        // 计算差值
        int diff = diffArg2 - diffArg1 + A_CHAR;

        return (char) diff;
    }



    /**
     * 加密，需要明文与密钥。
     *
     * @param text text
     * @param key  key, <code>key <= text</code>
     * @return password
     */
    public static String encrypt(String text, String key) {
        // 如果key的长度与text的长度不同，补全至相同长度
        int length = text.length();
        if (key.length() != length) {
            key = completionKey(key, length);
        }

        // 加密 -> 遍历, 通过密钥对应明文表获取对应字符
        char[] password = new char[length];
        for (int i = 0; i < length; i++) {
            password[i] = encryptTable(key.charAt(i), text.charAt(i));
        }

        return new String(password);
    }

    /**
     * 解密，需要密码和密钥
     *
     * @param password password
     * @param key      key
     * @return text
     */
    public static String decrypt(String password, String key) {
        //密文与明文的长度一致，则密钥长度也应该与密文一致
        int length = password.length();
        if (key.length() != length) {
            key = completionKey(key, length);
        }

        // 解密 -> 遍历，通过密钥对应交叉字符寻找对应明文列
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = decryptTable(key.charAt(i), password.charAt(i));
        }

        return new String(text);
    }


    /**
     * 补全key至指定长度
     *
     * @param key    key
     * @param length length
     */
    private static String completionKey(String key, int length) {
        // 长度相同，直接返回
        if (key.length() == length) {
            return key;
        }

        // 如果需要的长度还短，直接切割
        if (length < key.length()) {
//            throw new IllegalArgumentException("key longer than " + length + ": " + key);
            return key.substring(0, length);
        }
        // 判断length 与 key 的差值
        // 取模
        char[] end = new char[length];
        int endLength = length % key.length();

        // 次数
        int time = 0;
        // 计算除法
        for (int i = 0; i < length / key.length(); i++, time++) {
            key.getChars(0, key.length(), end, time * key.length());
        }

        if (endLength > 0) {
            key.getChars(0, endLength, end, end.length - endLength);
        }

        return new String(end);
    }


}
