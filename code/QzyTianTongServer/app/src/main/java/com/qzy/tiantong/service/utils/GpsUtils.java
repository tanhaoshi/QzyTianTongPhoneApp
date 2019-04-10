package com.qzy.tiantong.service.utils;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.provider.Settings;

public class GpsUtils {

    private static final String MODE_CHANGING_ACTION =
            "com.android.settings.location.MODE_CHANGING";
    private static final String CURRENT_MODE_KEY = "CURRENT_MODE";
    private static final String NEW_MODE_KEY = "NEW_MODE";

    /**
     * 打开gps
     */
    public static void openGPS(Context context,int cureentMode,int newModeKey){
        try {
            Intent intent = new Intent(MODE_CHANGING_ACTION);
            intent.putExtra(CURRENT_MODE_KEY, cureentMode);
            intent.putExtra(NEW_MODE_KEY, newModeKey);
            //context.sendBroadcast(intent, android.Manifest.permission.WRITE_SECURE_SETTINGS);
            context.sendBroadcastAsUser(intent,  QzyUserHandler.getUserHandleALL(),android.Manifest.permission.WRITE_SECURE_SETTINGS);
            Settings.Secure.putInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_HIGH_ACCURACY);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 关闭gps
     */
    public static void closeGPS(Context context,int cureentMode,int newModeKey){
        try {
            Intent intent = new Intent(MODE_CHANGING_ACTION);
            intent.putExtra(CURRENT_MODE_KEY, cureentMode);
            intent.putExtra(NEW_MODE_KEY, newModeKey);
            //context.sendBroadcast(intent, android.Manifest.permission.WRITE_SECURE_SETTINGS);
            context.sendBroadcastAsUser(intent, QzyUserHandler.getUserHandleALL(),android.Manifest.permission.WRITE_SECURE_SETTINGS);
            Settings.Secure.putInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
