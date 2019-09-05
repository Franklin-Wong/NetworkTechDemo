package com.integration.networktechdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.integration.networktechdemo.appstore.ApkInfoActivity;
import com.integration.networktechdemo.mails.FoldListActivity;
import com.integration.networktechdemo.socket.NetAddressActivity;
import com.integration.networktechdemo.socket.SocketActivity;
import com.integration.networktechdemo.thread.AsyncTaskActivity;
import com.integration.networktechdemo.thread.IntentServiceActivity;
import com.integration.networktechdemo.thread.MessageActivity;
import com.integration.networktechdemo.thread.ProgressCircleActivity;
import com.integration.networktechdemo.thread.ProgressDialogActivity;
import com.integration.networktechdemo.thread.ProgressTextActivity;
import com.integration.networktechdemo.updown.DownloadApkActivity;
import com.integration.networktechdemo.updown.DownloadImageActivity;
import com.integration.networktechdemo.updown.FileSaveActivity;
import com.integration.networktechdemo.updown.FileSelectActivity;
import com.integration.networktechdemo.updown.UploadHttpActivity;
import com.integration.networktechdemo.userinterface.ConnectActivity;
import com.integration.networktechdemo.userinterface.HttpImageActivity;
import com.integration.networktechdemo.userinterface.HttpRequestActivity;
import com.integration.networktechdemo.userinterface.JsonConvertActivity;
import com.integration.networktechdemo.userinterface.JsonParseActivity;
import com.integration.networktechdemo.utils.PermissionUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewEvent();
    }

    private void initViewEvent() {


        findViewById(R.id.btn_message).setOnClickListener(this);
        findViewById(R.id.btn_progress_dialog).setOnClickListener(this);
        findViewById(R.id.btn_progress_text).setOnClickListener(this);
        findViewById(R.id.btn_progress_circle).setOnClickListener(this);
        findViewById(R.id.btn_async_task).setOnClickListener(this);
        findViewById(R.id.btn_intent_service).setOnClickListener(this);
        findViewById(R.id.btn_connect).setOnClickListener(this);
        findViewById(R.id.btn_json_parse).setOnClickListener(this);
        findViewById(R.id.btn_json_convert).setOnClickListener(this);
        findViewById(R.id.btn_http_request).setOnClickListener(this);
        findViewById(R.id.btn_http_image).setOnClickListener(this);
        findViewById(R.id.btn_download_apk).setOnClickListener(this);
        findViewById(R.id.btn_download_image).setOnClickListener(this);
        findViewById(R.id.btn_file_save).setOnClickListener(this);
        findViewById(R.id.btn_file_select).setOnClickListener(this);
        findViewById(R.id.btn_upload_http).setOnClickListener(this);
        findViewById(R.id.btn_net_address).setOnClickListener(this);
        findViewById(R.id.btn_socket).setOnClickListener(this);
        findViewById(R.id.btn_apk_info).setOnClickListener(this);
        findViewById(R.id.btn_app_store).setOnClickListener(this);
        findViewById(R.id.btn_fold_list).setOnClickListener(this);
        findViewById(R.id.btn_qqchat).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_message) {
            Intent intent = new Intent(this, MessageActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_progress_dialog) {
            Intent intent = new Intent(this, ProgressDialogActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_progress_text) {
            Intent intent = new Intent(this, ProgressTextActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_progress_circle) {
            Intent intent = new Intent(this, ProgressCircleActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_async_task) {
            Intent intent = new Intent(this, AsyncTaskActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_intent_service) {
            Intent intent = new Intent(this, IntentServiceActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_connect) {
            Intent intent = new Intent(this, ConnectActivity.class);
            startActivity(intent);
        }else if (v.getId() == R.id.btn_json_parse) {
            Intent intent = new Intent(this, JsonParseActivity.class);
            startActivity(intent);
        }else if (v.getId() == R.id.btn_json_convert) {
            Intent intent = new Intent(this, JsonConvertActivity.class);
            startActivity(intent);
        }else if (v.getId() == R.id.btn_http_request) {
            if (PermissionUtil.checkPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, R.id.btn_http_request % 4096)) {
                PermissionUtil.goActivity(this, HttpRequestActivity.class);
            }
        } else if (v.getId() == R.id.btn_http_image) {
            Intent intent = new Intent(this, HttpImageActivity.class);
            startActivity(intent);
        }else if (v.getId() == R.id.btn_download_apk) {
            Intent intent = new Intent(this, DownloadApkActivity.class);
            startActivity(intent);
        }else if (v.getId() == R.id.btn_download_image) {
            Intent intent = new Intent(this, DownloadImageActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_file_save) {
            if (PermissionUtil.checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, R.id.btn_file_save % 4096)) {
                PermissionUtil.goActivity(this, FileSaveActivity.class);
            }
        }else if (v.getId() == R.id.btn_file_select) {
            if (PermissionUtil.checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, R.id.btn_file_select % 4096)) {
                PermissionUtil.goActivity(this, FileSelectActivity.class);
            }
        }else if (v.getId() == R.id.btn_upload_http) {
            if (PermissionUtil.checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, R.id.btn_upload_http % 4096)) {
                PermissionUtil.goActivity(this, UploadHttpActivity.class);
            }
        } else if (v.getId() == R.id.btn_net_address) {
            Intent intent = new Intent(this, NetAddressActivity.class);
            startActivity(intent);
        }else if (v.getId() == R.id.btn_socket) {
            Intent intent = new Intent(this, SocketActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_apk_info) {
            Intent intent = new Intent(this, ApkInfoActivity.class);
            startActivity(intent);
        }else if (v.getId() == R.id.btn_fold_list) {
            Intent intent = new Intent(this, FoldListActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == R.id.btn_http_request % 4096 ){
            if ( grantResults[0] == PackageManager.PERMISSION_GRANTED){
                PermissionUtil.goActivity(this, HttpRequestActivity.class);
            }else{
                Toast.makeText(this, "需要允许定位权限才能开始定位噢", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == R.id.btn_file_save % 4096){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                PermissionUtil.goActivity(this, FileSaveActivity.class);
            }else {
                Toast.makeText(this, "需要允许SD卡权限才能保存文件噢", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == R.id.btn_file_select % 4096){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                PermissionUtil.goActivity(this, FileSaveActivity.class);
            }else {
                Toast.makeText(this, "需要允许SD卡权限才能保存文件噢", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == R.id.btn_upload_http % 4096){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                PermissionUtil.goActivity(this, FileSaveActivity.class);
            }else {
                Toast.makeText(this, "需要允许SD卡权限才能保存文件噢", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
