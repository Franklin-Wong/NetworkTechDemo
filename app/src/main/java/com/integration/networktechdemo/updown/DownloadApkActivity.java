package com.integration.networktechdemo.updown;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.integration.networktechdemo.R;
import com.integration.networktechdemo.utils.DateUtil;

public class DownloadApkActivity extends AppCompatActivity {
    private static final String TAG = "DownloadApkActivity";
    private static Spinner sp_apk_url;
    private static TextView tv_apk_result;
    private static int mDownloadId = 0;
    private boolean isFirstSelect = true;
    private DownloadManager mDownloadManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_apk);
        tv_apk_result = findViewById(R.id.tv_apk_result);
        sp_apk_url = findViewById(R.id.sp_apk_url);
        mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        initApkSpinner();

    }



    private void initApkSpinner() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.item_select, PackageInfo.mNameArray);
        sp_apk_url.setPrompt("请选择要下载的安装包");
        sp_apk_url.setAdapter(arrayAdapter);
        sp_apk_url.setSelection(0);
        sp_apk_url.setOnItemSelectedListener(new MyItemSelectedListener());

    }

    private class MyItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ///每次进入，刷新描述信息
            if (tv_apk_result != null){
                tv_apk_result.setText("开始下载......"+PackageInfo.mNameArray[position]);
            }
            // 首次进入，不需刷新
            if (isFirstSelect){
                isFirstSelect = false;
                return;
            }
            sp_apk_url.setEnabled(true);

            //创建下载请求，将 下载地址进入到请求参数列表
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(PackageInfo.mUrlArray[position]));
            //设置下载任务的标题
            request.setTitle(PackageInfo.mNameArray[position]);
            //设置描述
            request.setDescription("正在下载"+ PackageInfo.mNameArray[position]);
            //设置允许类型
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            //设置文件的报错路径
            request.setDestinationInExternalFilesDir(DownloadApkActivity.this,  Environment.DIRECTORY_DOWNLOADS , position + ".apk");
            //设置通知栏显示
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            //设置在系统设置也显示
            request.setVisibleInDownloadsUi(true);
            //添加下载请求到队列，并获取队列ID
            mDownloadId = (int) mDownloadManager.enqueue(request);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private DownloadReceiver mDownloadReceiver;
    private NotificationClickReceiver mClickReceiver;
    //注册监听下载的广播
    private class DownloadReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            && tv_apk_result != null){
                //解析意图中 的下载编码
                long downId =  intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                tv_apk_result.setVisibility(View.VISIBLE);
                tv_apk_result.setText(DateUtil.getNowTime() + "下载任务已完成,编码 = " + downId +".....mDownloadId = " + mDownloadId);
                sp_apk_url.setEnabled(true);
            }

        }
    }

    public class NotificationClickReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)
            && tv_apk_result != null){
                //解析意图中 的数据
                long[] downIds = intent.getLongArrayExtra(DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS);
                for (Long id:
                     downIds){
                    if (id == mDownloadId){
                        tv_apk_result.setVisibility(View.VISIBLE);
                        tv_apk_result.setText("下载编码为"+ id +"的任务被点击了一下");

                    }
                }
            }
        }
    }


    
    @Override
    protected void onStart() {
        super.onStart();

        mDownloadReceiver = new DownloadReceiver();
        mClickReceiver = new NotificationClickReceiver();
        registerReceiver(mDownloadReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        registerReceiver(mClickReceiver, new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED));
    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mClickReceiver);
        unregisterReceiver(mDownloadReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

}
