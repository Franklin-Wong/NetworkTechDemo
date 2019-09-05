package com.integration.networktechdemo.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;
import android.widget.Toast;

import com.integration.networktechdemo.appstore.ApkInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wongerfeng on 2019/9/2.
 */
public class ApkUtils {

    private static final String TAG = "ApkUtils";

    /**
     *
     * @param context
     * @return
     */
    public static List<ApkInfo> getAllApkFile(Context context) {

        List<ApkInfo> arrayList = new ArrayList();
        //查询获取所有的apk文件
        Cursor cursor = context.getContentResolver().query(MediaStore.Files.getContentUri("external"), null,
                "mime_type=\"application/vnd.android.package-archive\"",
                null, null);
        try {
            //查询文件
            if (cursor != null){
                while (cursor.moveToNext()){
                    //获取文件名
                    String title = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE));
                    //获取文件路径
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                    //获取文件大小
                    int size = cursor.getInt(cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE));
                    //获取apk包信息
                    //获取包管理器
                    PackageManager packageManager = context.getPackageManager();
                    //提取 包名 版本名 版本编码
                    PackageInfo pi = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
                    if (pi != null){
                        String packageName = pi.packageName;
                        String versionName = pi.versionName;
                        int versionCode = pi.versionCode;
                        arrayList.add(new ApkInfo(title, packageName,versionCode, path,size, versionName));
                    }
                }
                cursor.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public static ApkInfo getApkInfo(Context context, String file_path) {
        try {
            ApkInfo apkInfo = new ApkInfo();
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageArchiveInfo(file_path, PackageManager.GET_ACTIVITIES);
            apkInfo.file_path = file_path;
            apkInfo.package_name = pi.packageName;
            apkInfo.version_name = pi.versionName;
            apkInfo.version_code = pi.versionCode;
            return apkInfo;
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
        return null;
    }
}
