package com.integration.networktechdemo.thread;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.integration.networktechdemo.R;
import com.integration.networktechdemo.utils.DateUtil;

public class IntentServiceActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "IntentServiceActivity";

    private TextView tv_intent;

    private Handler mHandler = new Handler();
    private Runnable mServiceRunnable = new Runnable() {
        @Override
        public void run() {
            startService(new Intent(IntentServiceActivity.this, AsyncService.class));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
        tv_intent = findViewById(R.id.tv_intent);
        findViewById(R.id.btn_intent).setOnClickListener(this);
        //启动延时服务
        mHandler.postDelayed(mServiceRunnable, 200);
    }

    @Override
    public void onClick(View v) {
        tv_intent.setText(DateUtil.getNowTime() + "您轻轻点了一下下(异步服务正在运行，不影响您在界面操作)");
    }
}
