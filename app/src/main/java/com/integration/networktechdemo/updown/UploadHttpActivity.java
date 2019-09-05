package com.integration.networktechdemo.updown;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aqi00.lib.dialog.FileSelectFragment;
import com.integration.networktechdemo.R;

import java.util.Map;


public class UploadHttpActivity extends AppCompatActivity implements View.OnClickListener, FileSelectFragment.FileSelectCallbacks, UploadTask.OnUploadTaskListener {
    private static final String TAG = "UploadHttpActivity";
    private EditText et_http_url;
    private TextView tv_file_path;
    private String mFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_http);
        et_http_url = findViewById(R.id.et_http_url);
        tv_file_path = findViewById(R.id.tv_file_path);
        findViewById(R.id.btn_file_select).setOnClickListener(this);
        et_http_url.setText(ClientThread.UPLOAD_REQUEST_URL + "/uploadServlet");
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_file_select){
            //声明一个可输出文件的扩展名数组
            // 声明一个可上传文件的扩展名数组，包括图片文件、文本文件、视频文件、音频文件
            String[] fileExt = new String[]{"jpg", "png", "txt", "3gp", "mp4", "amr", "aac", "mp3"};

            FileSelectFragment.show(this,fileExt, null );

        }
    }

    @Override
    public void onConfirmSelect(String absolutePath, String fileName, Map<String, Object> map_param) {
        //build a path of upload
        mFileName = fileName;
        String path = String.format("%s/%s", absolutePath, mFileName);
        //show the path in the tv widget
        tv_file_path.setText("上传文件的路径为:"+path);
        //创建上传的异步任务

        UploadTask uploadTask = new UploadTask();
        uploadTask.setUploadTaskListener(this);
        //将上传的URL地址， 和文件的路径和文件名，作为参数，传递给异步任务
        uploadTask.execute(et_http_url.getText().toString(), path);

    }

    @Override
    public boolean isFileValid(String absolutePath, String fileName, Map<String, Object> map_param) {
        return true;
    }

    @Override
    public void onUploadTaskListener(String result) {
        Log.i(TAG, "onUploadTaskListener: "+ result);
        String desc = String.format("%s\n上传结果为：%s", et_http_url.getText(),result);

        tv_file_path.setText(desc);


    }
}
