package com.forte.utils.net;

import java.util.Map;

/**
 *
 *  拓展Url封装
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface ExUrl {

    int DEFAULT_PORT = 80;
    String DEFAULT_HRL_HEAD = URLHead.HTTP.toString();

    /** h获取路径，开头默认为http:// */
    String getUrl();

    /** 如果存在ip则返回IP */
    String getIp();

    /** 如果存在路径参数则返回路径参数 */
    Map<String, String> getUrlParam();

    /** 如果存在IP则返回ip的4个分段数字 */
    int[] getIpNums();

    /** 如果存在端口则返回端口，否则端口默认为80 */
    int getPort();

}

