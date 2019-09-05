package com.integration.networktechdemo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Wongerfeng on 2019/8/14.
 */
public class DateUtil {


    public static String getNowTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        return sdf.format(new Date());
    }

}
