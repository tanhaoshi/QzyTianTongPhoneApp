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

    /**
     * Return the application's version code.
     *
     * @return the application's version code
     */
    public static int getAppVersionCode(Context context) {
        return getAppVersionCode(context.getPackageName(),context);
    }

    /**
     * Return the application's version code.
     *
     * @param packageName The name of the package.
     * @return the application's version code
     */
    public static int getAppVersionCode(final String packageName,Context context) {
        if (isSpace(packageName)) return -1;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }


}
