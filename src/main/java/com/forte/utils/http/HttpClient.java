package com.forte.utils.http;

import java.util.Map;

/**
 * httpClient规范接口
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface HttpClient {

    /**
     * 发送一个请求
     * @param type   请求类型
     * @param url    请求路径
     * @param params 参数集
     * @return       请求响应值
     */
    String send(String type, String url, Map<String, Object> params);

    /**
     * 发送一个请求
     * @param type  请求类型
     * @param url   请求路径
     * @return      请求响应值
     */
    default String send(String type, String url){
        return send(type, url, null);
    }

    /**
     * GET方式
     * @param url       请求路径
     * @param params    参数集
     * @return          响应值
     */
    String get(String url, Map<String, Object> params);

    /**
     * GET方式
     * @param url       请求路径
     * @return          响应值
     */
    default String get(String url){
        return get(url, null);
    }

    /**
     * POST方式
     * @param url       请求路径
     * @param params    参数集
     * @return          响应值
     */
    String post(String url, Map<String, Object> params);

    /**
     * POST方式
     * @param url       请求路径
     * @return          响应值
     */
    default String post(String url){
        return post(url, null);
    }

    /**
     * PUT方式
     * @param url       请求路径
     * @param params    参数集
     * @return          响应值
     */
    String put(String url, Map<String, Object> params);

    /**
     * PUT方式
     * @param url       请求路径
     * @return          响应值
     */
    default String put(String url){
        return put(url);
    }

    /**
     * DELETE方式
     * @param url       请求路径
     * @param params    参数集
     * @return          响应值
     */
    String delete(String url, Map<String, Object> params);

    /**
     * DELETE方式
     * @param url       请求路径
     * @return          响应值
     */
    default String delete(String url){
        return delete(url, null);
    }

    /**
     * PATCH方式
     * @param url       请求路径
     * @param params    参数集
     * @return          响应值
     */
    String patch(String url, Map<String, Object> params);

    /**
     * PATCH方式
     * @param url       请求路径
     * @return          响应值
     */
    default String patch(String url){
        return patch(url, null);
    }

}
