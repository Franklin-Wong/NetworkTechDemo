package com.integration.networktechdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Wongerfeng on 2019/8/21.
 */
public class PermissionUtil {
    public static boolean checkPermission(Activity activity, String permission, int requestCode) {

        ///在SDK6.0以上，才申请权限
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            //判断是否请求权限
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED){
                //请求权限
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
                return false;
            }
        }
        return true;
    }


    public static void goActivity(Context context, Class<?> clazz) {

        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);

    }
}
