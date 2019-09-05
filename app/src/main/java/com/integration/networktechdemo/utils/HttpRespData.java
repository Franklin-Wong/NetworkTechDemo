package com.integration.networktechdemo.utils;

import android.graphics.Bitmap;

/**
 * Created by Wongerfeng on 2019/8/22.
 */
public class HttpRespData {
    public String content;
    public Bitmap bitmap;
    public String cookie;
    public String err_msg;

    public HttpRespData() {
        content = "";
        bitmap = null;
        cookie = "";
        err_msg = "";
    }
}
