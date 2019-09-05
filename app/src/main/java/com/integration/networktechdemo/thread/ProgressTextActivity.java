package com.integration.networktechdemo.thread;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.integration.networktechdemo.R;
import com.integration.networktechdemo.widget.TextProgressBar;

public class ProgressTextActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "ProgressTextActivity";
    private TextProgressBar mTextProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_text);
        // 从布局文件中获取名叫tpb_progress的文本进度条
        mTextProgressBar = findViewById(R.id.tpb_progress);

        initProgressSpinner();

    }

    private String[] progressArray = {
            "0", "10", "20", "30", "40", "50",
            "60", "70", "80", "90", "100"
    };

    private void initProgressSpinner() {

        ArrayAdapter arrayAdapter =
                new ArrayAdapter(this, R.layout.item_select, progressArray);
        Spinner spinner = findViewById(R.id.sp_progress);
        spinner.setAdapter(arrayAdapter);
        spinner.setPrompt("选择加载的进度");
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //解析数值，获取下载的进度值
        int progress = Integer.parseInt(progressArray[position]);
        //设置进度条进度
        mTextProgressBar.setProgress(progress);
        //设置进度条上的文字
        mTextProgressBar.setProgressText("当前的进度值为" + progress + "%");

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
