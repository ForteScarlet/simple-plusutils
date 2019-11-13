package com.forte.utils.ables;

import com.forte.utils.security.MD5Utils;
import com.forte.utils.function.ExFunction;
import com.forte.utils.function.FunctionThrows;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;

/**
 *
 * 提供获取MD5加密字符串的可能
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface MD5Able extends StringAble {

    /**
     * 转化为MD5
     */
    default String toMD5() throws NoSuchAlgorithmException {
        return MD5Utils.toMD5(toStr());
    }
    /**
     * 转化为MD5
     */
    default String toMD5(String charsetName) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return MD5Utils.toMD5(toStr(charsetName));
    }
    /**
     * 转化为MD5
     */
    default String toMD5(Charset charset) throws NoSuchAlgorithmException {
        return MD5Utils.toMD5(toStr(charset));
    }

    /**
     * 转化为MD5，加盐并自定义盐函数
     * @param salt          盐
     * @param saltFunction
     * 盐函数<br>
     * 盐函数中，第一个参数为MD5字符串获取函数，参数为一个字符串，返回一个MD5字符串<br>
     * 第二个参数为原本的字符串<br>
     * 第三个参数为盐<br>
     * 返回值为加密好的MD5<br>
     *     <code>
     *              String md = "hello!hahahahahahh";
     *              String s = ByteStream.of(md).toMD5("salt", (f, str, salt) -> {
     *             try {
     *                 return f.apply(str + '.' + salt);
     *             } catch (Throwable throwable) {
     *                 return null;
     *             }
     *         });
     *     </code>
     * @return
     */
    default String toMD5(String salt, ExFunction<FunctionThrows<String, String>, String, String, String> saltFunction) {
        return saltFunction.apply(MD5Utils::toMD5, toStr(), salt);
    }
    default String toMD5(String salt, Charset charset, ExFunction<FunctionThrows<String, String>, String, String, String> saltFunction) {
        return saltFunction.apply(MD5Utils::toMD5, toStr(charset), salt);
    }
    default String toMD5(String salt, String charsetName, ExFunction<FunctionThrows<String, String>, String, String, String> saltFunction) throws UnsupportedEncodingException {
        return saltFunction.apply(MD5Utils::toMD5, toStr(charsetName), salt);
    }


}
