package com.integration.networktechdemo.updown;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Wongerfeng on 2019/8/29.
 */
public class HttpUploadUtil {
    private static final String TAG = "HttpUploadUtil";

    public static String upload(String uploadUrl, String uploadFilePath) {
        //声明文件名称
        String fileName = "";
        int pos = uploadFilePath.lastIndexOf("/");
        if (pos > 0){
            fileName = uploadFilePath.substring(pos + 1);

        }

        String end = "\r\n";
        String Hyphens = "--";
        String boundary = "WUm4580jbtwfJhNp7zi1djFEO3wNNm";

        try {
            URL url = new URL(uploadUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(6 * 1000);
            connection.setReadTimeout(15 *1000);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Connection", "Keep-alive");
            connection.setRequestProperty("Charset", "utf-8");
            connection.setRequestProperty("Content-type", "multipart/form-data;boundary="+ boundary);
            //创建输出流数据, 从网络连接中 获取文件输出流
            DataOutputStream ds = new DataOutputStream(connection.getOutputStream());
            ds.writeBytes(boundary + Hyphens + end);
            ds.writeBytes("Content-Disposition: form-data; \"\n" +
                    "+ \"name=\\\"file1\\\";filename=\\\"\" "+ boundary + end);
            ds.writeBytes(end);
            //创建输入流，并读取,将读取内容写入缓存
            FileInputStream fis = new FileInputStream(uploadUrl);
            //创建数组
            byte[] bytes = new byte[1024];
            int len = 0;
            //读取输入流
            while ((len = fis.read(bytes)) != -1){
                ds.write(bytes, 0, len);
            }
            ds.writeBytes(end);
            ds.writeBytes(Hyphens + boundary + Hyphens + boundary);

            fis.close();
            ds.flush();
            ds.close();
            //获取返回的内容
            InputStream inputStream = connection.getInputStream();
            int ch = 0;
            StringBuffer buffer = new StringBuffer();
            while ((ch = inputStream.read(bytes)) != -1){
                buffer.append(ch);
            }
            inputStream.close();
            return "SUCCESS";
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "FAILED";
        } catch (IOException e) {
            e.printStackTrace();
            return "FAILED";
        }catch (Exception e){
            e.printStackTrace();
            return "FAILED : " + e.getMessage();
        }
    }
}
