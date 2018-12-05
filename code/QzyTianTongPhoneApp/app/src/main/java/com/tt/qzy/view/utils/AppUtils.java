package com.tt.qzy.view.utils;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.icu.text.DecimalFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;

import java.io.File;
import java.util.List;
import java.util.Locale;

public final class AppUtils {

    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
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

    public static String getRootDirPath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File file = ContextCompat.getExternalFilesDirs(context.getApplicationContext(),
                    null)[0];
            return file.getAbsolutePath();
        } else {
            return context.getApplicationContext().getFilesDir().getAbsolutePath();
        }
    }

    public static String getProgressDisplayLine(long currentBytes, long totalBytes) {
        return getBytesToMBString(currentBytes) + "/" + getBytesToMBString(totalBytes);
    }

    private static String getBytesToMBString(long bytes){
        return String.format(Locale.ENGLISH, "%.2fMb", bytes / (1024.00 * 1024.00));
    }

    public static void installApk(Context context,String installPath){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(installPath)),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String decimalDouble(double latItude){
        DecimalFormat df = new DecimalFormat("##0.0000");
        String handleValue = df.format(latItude);
        return handleValue;
    }

    /**
     * 判断服务是否处于运行状态.
     * @param servicename
     * @param context
     * @return
     */
    public static boolean isServiceRunning(String servicename,Context context){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> infos = am.getRunningServices(100);
        for(ActivityManager.RunningServiceInfo info: infos){
            if(servicename.equals(info.service.getClassName())){
                return true;
            }
        }
        return false;
    }

    public static void wakeUpAndUnlock(Context context){
        //屏锁管理器
        KeyguardManager km= (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
        //解锁
        kl.disableKeyguard();
        //获取电源管理器对象
        PowerManager pm=(PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.SCREEN_DIM_WAKE_LOCK,"bright");
        //点亮屏幕
        wl.acquire();
        //释放
        wl.release();
        // 屏幕解锁
        KeyguardManager keyguardManager = (KeyguardManager) context
                .getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("StartupReceiver");
        // 屏幕锁定
//        keyguardLock.reenableKeyguard();
        keyguardLock.disableKeyguard(); // 解锁
    }
}
