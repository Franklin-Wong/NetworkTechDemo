package com.integration.networktechdemo.userinterface;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.integration.networktechdemo.R;

public class HttpImageActivity extends AppCompatActivity implements View.OnClickListener, GetImageCodeTask.OnImageCodeListener {
    private static final String TAG = "HttpImageActivity";
    private ImageView iv_image_code;
    private boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_image);
        iv_image_code = findViewById(R.id.iv_image_code);
        iv_image_code.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_image_code){
            getImageCode();
        }

    }

    private void getImageCode() {
        //当前验证码任务没有进行时 ，创建任务
        if (!isRunning){
            //更新运行状态
            isRunning = true;
            GetImageCodeTask task = new GetImageCodeTask();
            task.setListener(this);
            task.execute();
        }
    }

    @Override
    public void onImageCode(String path) {

        //解析图片地址
        Uri uri = Uri.parse(path);
        //加载图片地址并显示
        iv_image_code.setImageURI(uri);
        //显示图片成功后，修改图片任务状态
        isRunning = false;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
