package com.integration.networktechdemo.updown;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aqi00.lib.dialog.FileSelectFragment;
import com.integration.networktechdemo.R;

import java.util.HashMap;
import java.util.Map;

public class FileSelectActivity extends AppCompatActivity implements View.OnClickListener, FileSelectFragment.FileSelectCallbacks {
    private static final String TAG = "FileSelectActivity";
    private ImageView iv_image_select; // 声明一个图像视图对象
    private TextView tv_image_select; // 声明一个文本视图对象


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_select);
        iv_image_select = findViewById(R.id.iv_image_select);
        tv_image_select = findViewById(R.id.tv_image_select);
        findViewById(R.id.btn_image_select).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_image_select){

            //声明一个图片类型的扩展名数组
            String[] imgs = new String[]{"jpg", "png", "jpeg"};
            ///打开对话框
            FileSelectFragment.show(this, imgs, new HashMap<String, Object>());
        }
    }


    @Override
    public void onConfirmSelect(String absolutePath, String fileName, Map<String, Object> map_param) {
        //拼接图片的完整路径
        String path = String.format("%s/%s", absolutePath, fileName);
        if (TextUtils.isEmpty(path)){
            Toast.makeText(this, "没有图片可以显示", Toast.LENGTH_SHORT).show();
            return;
        }
        //解析路径URI，图片显示在控件上
        iv_image_select.setImageURI(Uri.parse(path));
        //将文件的路径完整显示在控件文本上
        tv_image_select.setText("打开图片的路径为 : "+path);
        Log.i(TAG, "onConfirmSelect: path = "+path + "。");
    }

    @Override
    public boolean isFileValid(String absolutePath, String fileName, Map<String, Object> map_param) {
        Log.i(TAG, "isFileValid: ");
        return true;
    }
}
