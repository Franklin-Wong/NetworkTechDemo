package com.integration.networktechdemo.updown;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aqi00.lib.dialog.FileSaveFragment;
import com.aqi00.lib.util.BitmapUtil;
import com.integration.networktechdemo.R;

public class FileSaveActivity extends AppCompatActivity implements View.OnClickListener , FileSaveFragment.FileSaveCallbacks {
    private static final String TAG = "FileSaveActivity";
    private EditText et_image_save; // 声明一个编辑框对象
    private TextView tv_image_save; // 声明一个文本视图对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_save);
        et_image_save = findViewById(R.id.et_image_save);
        tv_image_save = findViewById(R.id.tv_image_save);
        findViewById(R.id.btn_image_save).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        et_image_save.setDrawingCacheEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_image_save){
            FileSaveFragment.show(this, "jpg");
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        et_image_save.setDrawingCacheEnabled(false);

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onCanSave(String absolutePath, String fileName) {
        return true;
    }

    @Override
    public void onConfirmSave(String absolutePath, String fileName) {

        //拼接完整的文件路径
        String path = String.format("%s/%s", absolutePath, fileName);
        //从控件获取绘图的缓存位图
        Bitmap drawingCache = et_image_save.getDrawingCache();
        //将位图保存为 图片
        BitmapUtil.saveBitmap(path, drawingCache, "jpg", 80);
        //回收位图
        drawingCache.recycle();
        //将图片的路径显示在文本中
        tv_image_save.setText("截图的保存路径为："+path);
    }
}
