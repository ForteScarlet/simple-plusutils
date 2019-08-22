package com.forte.utils.ables;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * 提供三种获取字符串的方法
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface StringAble {


    /** get a String, like toString() */
    String toStr();
    /** get a String by charset type */
    String toStr(Charset charset);
    /** get a String by charset name */
    String toStr(String charsetName) throws UnsupportedEncodingException ;


}
