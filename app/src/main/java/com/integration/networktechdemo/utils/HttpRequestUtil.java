package com.integration.networktechdemo.utils;

import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Wongerfeng on 2019/8/22.
 */
public class HttpRequestUtil {
    private static final String TAG = "HttpRequestUtil";
    public static void setConnHeader(HttpURLConnection connection, String method, HttpRequestData data){

        ///网络连接设置
        try {
            connection.setRequestMethod(method);

            connection.setConnectTimeout(5 * 1000);
            connection.setReadTimeout(10 * 1000);

            //设置数据格式
            connection.setRequestProperty("Accept", "*/*");
            //设置语言格式
            connection.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
            //设置用户代理
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0");
            //设置编码格式
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            if (!data.cookie.equals("")){
                connection.setRequestProperty("Cookie", data.cookie);
            }
            if (!data.x_requested_with.equals("")){
                connection.setRequestProperty("X_requested_with", data.x_requested_with);
            }
            if (!data.referer.equals("")){
                connection.setRequestProperty("Referer", data.referer);
            }
            if (!data.content_type.equals("")){
                connection.setRequestProperty("Content_type", data.content_type);
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param data
     * @return
     */
    public static HttpRespData getImageCode(HttpRequestData data){
        HttpRespData respData = new HttpRespData();

        try {
            //创建网络连接
            URL url = new URL(data.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设置请求头内容
            setConnHeader(connection, "GET", data);
            //连接
            connection.connect();
            //获取输入流
            InputStream inputStream = connection.getInputStream();
            //解码输入流
            respData.bitmap = BitmapFactory.decodeStream(inputStream);
            //获取cookie
            respData.cookie = connection.getHeaderField("Set-Cookie");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            respData.err_msg = e.getMessage();
        }

        Log.i(TAG, "getImageCode: "+ respData);
        return respData;
    }

}
