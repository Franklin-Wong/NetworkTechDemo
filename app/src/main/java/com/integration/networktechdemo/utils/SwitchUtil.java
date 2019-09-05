package com.integration.networktechdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.widget.Toast;

/**
 * Created by Wongerfeng on 2019/8/22.
 */
public class SwitchUtil {


    public static void checkGpsOpen(Activity activity, String hint) {

        if (!getGpsStatus(activity)){
            Toast.makeText(activity, hint, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            activity.startActivity(intent);
        }
    }

    private static boolean getGpsStatus(Activity activity) {
        LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        if (lm != null) {
            return lm.isLocationEnabled();
        }
        return false;
    }
}


