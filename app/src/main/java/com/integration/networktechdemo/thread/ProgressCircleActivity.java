package com.integration.networktechdemo.thread;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.integration.networktechdemo.R;
import com.integration.networktechdemo.widget.TextProgressCircle;

public class ProgressCircleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "ProgressCircleActivity";
    ///声明一个文本进度圈
    private TextProgressCircle tpc_progress;

    private String[] progressArray = {
            "0", "10", "20", "30", "40", "50",
            "60", "70", "80", "90", "100"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_circle);
        tpc_progress = findViewById(R.id.tpc_progress);

        initProgressSpinner();

    }

    private void initProgressSpinner() {

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.item_select, progressArray);

        Spinner spinner = findViewById(R.id.sp_progress);
        spinner.setPrompt("请选择进度条的值");
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //解析数组对应position的值
        int value = Integer.parseInt(progressArray[position]);
        //将该VALUE 数组为进度条的 进度值
        tpc_progress.setProgress(value, -1);
        tpc_progress.setProgressStyle(Color.YELLOW, 20);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.i(TAG, "onNothingSelected: ");
        Toast.makeText(this, "不选择也是一种选择", Toast.LENGTH_SHORT).show();

    }
}
