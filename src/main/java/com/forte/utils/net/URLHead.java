package com.forte.utils.net;

/**
 *
 * 请求-响应协议
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public enum URLHead {

    /** http */
    HTTP("http"),
    /** https */
    HTTPS("https");


    private final String head;
    private final String toString;
    URLHead(String head){
        this.head = head;
        this.toString = head + "://";
    }

    public String getHead(){
        return head;
    }
    @Override
    public String toString(){
        return toString;
    }

}
