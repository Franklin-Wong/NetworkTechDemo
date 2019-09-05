package com.integration.networktechdemo.utils;

/**
 * Created by Wongerfeng on 2019/8/22.
 */
public class HttpRequestData {

    public String url;
    public String cookie;
    public String referer;
    public String content_type;
    public String x_requested_with;
    public StringBuffer params;
    public String charset;
    public String boundary;

    public HttpRequestData(String url) {
        this();
        this.url = url;
    }

    private HttpRequestData() {
        url = "";
        cookie = "";
        referer ="";
        content_type = "";
        x_requested_with = "";
        params = new StringBuffer();
        charset = "utf-8";
        boundary = "";
    }
}
