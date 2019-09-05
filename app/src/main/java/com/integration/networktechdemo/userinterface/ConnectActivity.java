package com.integration.networktechdemo.userinterface;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import com.integration.networktechdemo.R;
import com.integration.networktechdemo.utils.Utils;

public class ConnectActivity extends AppCompatActivity {

    private static final String TAG = "ConnectActivity";
    private TextView tv_connect;
    private Handler mHandler = new Handler();
    private String[] mNetStateArray = {"正在连接", "已连接", "暂停", "正在断开", "已断开", "未知"};
    private Runnable mNetWorkRunnable = new Runnable() {
        @Override
        public void run() {
            //获取可连接的网络
            getAvailableNet();
            //在100毫秒后，再次启动刷新任务
            mHandler.postDelayed(mNetWorkRunnable, 100);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        tv_connect = findViewById(R.id.tv_connect);
        // 延迟50毫秒后启动刷新任务
        mHandler.postDelayed(mNetWorkRunnable, 50);

    }
    private void getAvailableNet() {

        String desc = "";
        //从服务中获取电话管理器
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        //从服务中获取网络连接管理器
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        //从管理器中得到网络连接信息
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null){
                //判断网络连接的状态
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED){
                    //显示相应的连接状态
                    desc = String.format("%S \n 当前的网络已经连接", mNetStateArray[networkInfo.getState().ordinal()]);
                    //判断网络连接的类型
                    if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
                        desc = String.format("%s \n当前网络的逻辑类型为%s",desc, "WIFI");

                    }else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
                        //获取子网络类型
                        desc = String.format("%s \n当前网络的逻辑类型为%s%s", desc,
                                Utils.getNetTypeName(telephonyManager, networkInfo.getSubtype()),
                                Utils.getClassName(telephonyManager, networkInfo.getSubtype()));

                    }else {
                        desc = String.format("%s \n当前网络的逻辑类型为%s",desc, networkInfo.getType());
                    }
                }else {
                    desc = String.format("\n当前网络没有连接");
                }
            }else {
                desc = String.format("\n当前网络没有连接");
            }

        }else {
            desc = String.format("\n当前网络没有连接");
        }
        tv_connect.setText(desc);

    }
}
