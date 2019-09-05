package com.integration.networktechdemo.appstore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.aqi00.lib.dialog.FileSelectFragment;
import com.integration.networktechdemo.R;
import com.integration.networktechdemo.utils.ApkUtils;

import java.util.List;
import java.util.Map;


@SuppressLint("HandlerLeak")
public class ApkInfoActivity extends AppCompatActivity implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener , FileSelectFragment.FileSelectCallbacks{
    private final static String TAG = "ApkInfoActivity";
    private Context mContext;
    private ListView lv_apk;
    private List<ApkInfo> mInfoList;
    private SwipeRefreshLayout mRefreshLayout;
    private ApkAdapter mApkAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apk_info);
        mContext = this;
        mRefreshLayout = findViewById(R.id.swipeRefresh);
        findViewById(R.id.btn_open).setOnClickListener(this);
        lv_apk = findViewById(R.id.lv_apk);
        // 弹出默认的圆圈进度对话框。因为扫描设备上的APK文件和解析安装包较费时间，所以通过进度对话框提示用户耐心等待。
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN);
        mHandler.postDelayed(mRunnable, 500);

    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            refreshApkList();
            //停止刷新
            mRefreshLayout.setRefreshing(false);

        }
    };

    private void refreshApkList() {
        mInfoList = ApkUtils.getAllApkFile(mContext);
        mApkAdapter = new ApkAdapter(mContext, mInfoList);
        lv_apk.setAdapter(mApkAdapter);
        lv_apk.setOnItemClickListener(this);
        Log.i(TAG, "refreshApkList: "+mInfoList);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_open){
            //创建apk信息的数组
            String[] apkExs = {"apk"};
            FileSelectFragment.show(mContext, apkExs, null);
        }
    }

    @Override
    public void onRefresh() {
        refreshApkList();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        showApkDetails(mInfoList.get(position));

    }

    private void showApkDetails(ApkInfo info) {
        ApkInfo apkInfo = info;
        if (TextUtils.isEmpty(apkInfo.package_name)){
            apkInfo = ApkUtils.getApkInfo(mContext, info.file_path);
        }

        if (apkInfo != null) {
            String desc = "";
            desc = String.format("文件名称:%s\n文件地址：%s\n文件大小：%s\n包名：%s\n版本号码：%s\n版本名称 ：%s",
                    apkInfo.file_name, apkInfo.file_path, apkInfo.file_size,
                    apkInfo.package_name, apkInfo.version_code, apkInfo.version_name);
            new AlertDialog.Builder(mContext)
                    .setTitle("app信息")
                    .setMessage(desc)
                    .setCancelable(true)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();

        }
    }

    @Override
    public void onConfirmSelect(String absolutePath, String fileName, Map<String, Object> map_param) {
        String path = "";
        path = String.format("%s/%S", absolutePath, fileName);
        ApkInfo apkInfo = new ApkInfo();
        apkInfo.file_path = path;
        apkInfo.file_name = fileName;
        showApkDetails(apkInfo);

    }

    @Override
    public boolean isFileValid(String absolutePath, String fileName, Map<String, Object> map_param) {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
