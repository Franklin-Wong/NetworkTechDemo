package com.integration.networktechdemo.userinterface;

import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.integration.networktechdemo.R;
import com.integration.networktechdemo.utils.SwitchUtil;

public class HttpRequestActivity extends AppCompatActivity {
    private static final String TAG = "HttpRequestActivity";

    private TextView tv_location;
    /**
     * 位置管理器
     */
    private LocationManager mLocationManager;
    private Handler mHandler = new Handler();
    //位置信息
    private String mLocation = "";
    /**定位信息
     */
    private Criteria mCriteria = new Criteria();
    private boolean isLocationEnabled = false;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

            //初始化定位管理器设置
            initLocationManager();
        }

    };

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_request);
        tv_location = findViewById(R.id.tv_location);
        SwitchUtil.checkGpsOpen(this, "需要打开定位功能才能查看定位结果信息");


    }

    private void initLocationManager() {
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //海拔
        mCriteria.setAltitudeRequired(true);
        //方位
        mCriteria.setBearingRequired(true);
        //精确
        mCriteria.setAccuracy(100);
        //电源
        mCriteria.setPowerRequirement(100);
        //付费
        mCriteria.setCostAllowed(true);
        //获取最佳定位提供者
        String bestProvider = mLocationManager.getBestProvider(mCriteria, true);
        //判断提供者是否可以
        if (mLocationManager.isProviderEnabled(bestProvider)){
            //可以使用
            tv_location.setText("正在获取"+ bestProvider +" 定位对象");
            //定位信息
            mLocation = String.format("定位类型=%s", bestProvider);
            beginLocation(bestProvider);
            isLocationEnabled = true;
        }else {
            tv_location.setText("不能定位"+ bestProvider +" 定位对象");
            isLocationEnabled = false;
        }
    }

    private void beginLocation(String provider) {
        //


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}
