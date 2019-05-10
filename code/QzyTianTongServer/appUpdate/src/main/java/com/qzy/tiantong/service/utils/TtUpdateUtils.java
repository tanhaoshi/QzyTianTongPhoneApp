package com.qzy.tiantong.service.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.qzy.tiantong.lib.utils.LogUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class TtUpdateUtils {

    public static boolean openTtUpdateMode() {
        try {
            FileWriter writer = new FileWriter("/dev/tt-mode");
            writer.write(1);
            writer.flush();
            writer.close();
            LogUtils.e("write 1 successs ......");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.e("write 1 falied......");
        return false;

    }

    public static boolean closeTtUpdateMode() {
        try {
            FileWriter writer = new FileWriter("/dev/tt-mode");
            writer.write(0);
            writer.flush();
            writer.close();
            LogUtils.e("write 0 successs ......");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.e("write 0 falied......");
        return false;
    }

    public static int readTtUpdateMode() {
        try {
            FileReader fileReader = new FileReader("/dev/tt-mode");
            BufferedReader bf = new BufferedReader(fileReader);
            String s = bf.readLine().trim();
            if(TextUtils.isEmpty(s)){
                return 0;
            }
            return Integer.parseInt(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }


}
