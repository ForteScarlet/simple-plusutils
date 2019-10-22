package com.forte.utils.net;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 本地请求的路径
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class LocalUrl implements ExUrl {

    private static final String BASE_URL = DEFAULT_HRL_HEAD + "localhost";
    private static final String IP = "127.0.0.1";
    private static final int[]  IP_NUMS = {127, 0, 0, 1};

    private int port;
    private String url;
    private Map<String, String> params;


    LocalUrl(int port, String urlHead){
        this.port = port;
        this.url = urlHead + "localhost";
        this.params = new HashMap<>();
    }
    LocalUrl(int port, URLHead urlHead){
        this.port = port;
        this.url = urlHead.toString() + "localhost";
        this.params = new HashMap<>();
    }
    LocalUrl(String urlHead){
        this.port = DEFAULT_PORT;
        this.url = urlHead + "localhost";
        this.params = new HashMap<>();
    }
    LocalUrl(URLHead urlHead){
        this.port = DEFAULT_PORT;
        this.url = urlHead.toString() + "localhost";
        this.params = new HashMap<>();
    }
    LocalUrl(int port){
        this.port = DEFAULT_PORT;
        this.url = BASE_URL;
        this.params = new HashMap<>();
    }
    LocalUrl(){
        this.port = DEFAULT_PORT;
        this.url = BASE_URL;
        this.params = new HashMap<>();
    }

    LocalUrl(int port, String urlHead, Map<String, String> params){
        this.port = port;
        this.url = urlHead + "localhost";
        this.params = params;
    }
    LocalUrl(int port, URLHead urlHead, Map<String, String> params){
        this.port = port;
        this.url = urlHead.toString() + "localhost";
        this.params = params;
    }
    LocalUrl(String urlHead, Map<String, String> params){
        this.port = DEFAULT_PORT;
        this.url = urlHead + "localhost";
        this.params = params;
    }
    LocalUrl(URLHead urlHead, Map<String, String> params){
        this.port = DEFAULT_PORT;
        this.url = urlHead.toString() + "localhost";
        this.params = params;
    }
    LocalUrl(int port, Map<String, String> params){
        this.port = DEFAULT_PORT;
        this.url = BASE_URL;
        this.params = params;
    }
    LocalUrl(Map<String, String> params){
        this.port = DEFAULT_PORT;
        this.url = BASE_URL;
        this.params = params;
    }


    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getIp() {
        return IP;
    }

    @Override
    public Map<String, String> getUrlParam() {
        return Collections.emptyMap();
    }

    @Override
    public int[] getIpNums() {
        return IP_NUMS;
    }

    @Override
    public int getPort() {
        return port;
    }
}
