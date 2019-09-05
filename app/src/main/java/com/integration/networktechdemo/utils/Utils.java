package com.integration.networktechdemo.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Wongerfeng on 2019/8/16.
 */
public class Utils {


    public static String[] mClassNameArray = {"UNKNOWN", "2G", "3G", "4G"};

    /**
     *
     * @param context
     * @param defaultValue
     * @return
     */
    public static int dip2pixel(Context context, float defaultValue) {
        //获取当前的手机 像素密度
        float scale = context.getResources().getDisplayMetrics().density;
        //返回像素值，四舍五入取整
        return (int) (defaultValue * scale + 0.5f);
    }

    public static Object getNetTypeName(TelephonyManager telephonyManager, int subtype) {
        String type_name = "";
        try {
            Method method = telephonyManager.getClass().getMethod("getNetworkTypeName", Integer.TYPE);
            type_name = (String) method.invoke(telephonyManager, subtype);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return type_name;
    }

    public static Object getClassName(TelephonyManager telephonyManager, int subtype) {
        int type = 0;
        try {
            Method method = telephonyManager.getClass().getMethod("getNetworkClass", Integer.TYPE);
            type = (Integer) method.invoke(telephonyManager, subtype);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return mClassNameArray[type];
    }
}
