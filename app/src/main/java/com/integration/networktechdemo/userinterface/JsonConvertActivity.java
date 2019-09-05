package com.integration.networktechdemo.userinterface;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.integration.networktechdemo.R;
import com.integration.networktechdemo.bean.User;

public class JsonConvertActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "JsonConvertActivity";
    private TextView tv_json;
    private User mUser = new User();
    private String mJsonStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_convert);

        initData();
        initEvent();
    }

    private void initEvent() {
        tv_json = findViewById(R.id.tv_json);
        findViewById(R.id.btn_origin_json).setOnClickListener(this);
        findViewById(R.id.btn_convert_json).setOnClickListener(this);

    }

    private void initData() {
        mUser.age = 90;
        mUser.height = 170;
        mUser.name = "阿卡卡";
        mUser.married = true;
        mUser.weight = 67;

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_origin_json){

            mJsonStr = new Gson().toJson(mUser);

            tv_json.setText("json串内容如下：\n" +mJsonStr);
        }else if (v.getId() == R.id.btn_convert_json){

            User user = new Gson().fromJson(mJsonStr, User.class);

            tv_json.setText("从json串解析而来的用户信息如下：" + user.toString());

        }

    }
}
