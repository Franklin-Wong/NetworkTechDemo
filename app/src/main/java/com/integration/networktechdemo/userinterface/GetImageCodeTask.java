package com.integration.networktechdemo.userinterface;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.integration.networktechdemo.MyApplication;
import com.integration.networktechdemo.utils.DateUtil;
import com.integration.networktechdemo.utils.HttpRequestData;
import com.integration.networktechdemo.utils.HttpRequestUtil;
import com.integration.networktechdemo.utils.HttpRespData;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Wongerfeng on 2019/8/22.
 */
public class GetImageCodeTask extends AsyncTask<Void, Void, String> {
    private static final String TAG = "GetImageCodeTask";
    /**
     * 图片地址
     */
    private String mImageCodeUrl = "http://yx12.fjjcjy.com/Public/Control/GetValidateCode?time=";
    private OnImageCodeListener mListener;

    public void setListener(OnImageCodeListener listener) {
        mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(Void... voids) {

        //创建请求bean对象
        HttpRequestData requestData = new HttpRequestData(mImageCodeUrl);
        //请求网络，获取图片
        HttpRespData respData = HttpRequestUtil.getImageCode(requestData);
        //拼接一个地址保存获取的图片
        String path = MyApplication.getInstance().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + DateUtil.getNowTime() + ".jpg";
        //将图片压缩后，保存到地址
        //创建压缩格式
        Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
        //创建输出流
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path));
            //将位图压缩到输出流
            if (respData.bitmap!= null){
                respData.bitmap.compress(compressFormat, 80, bos);
            }
            //回收输出流
            bos.flush();
            //关闭输出流
            bos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, "doInBackground: image path=" + path);
        return path;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected void onPostExecute(String path) {
        super.onPostExecute(path);
        mListener.onImageCode(path);
    }

    public interface OnImageCodeListener {
        void onImageCode(String path);
    }
}
