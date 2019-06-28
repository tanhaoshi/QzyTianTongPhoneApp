package com.tt.qzy.view.utils;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.icu.text.DecimalFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Random;

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

    public static String getIpAddress(Context context) {

        ConnectivityManager connect = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //检查网络连接
        NetworkInfo info = connect.getActiveNetworkInfo();

        if(null == info){
            NToast.shortToast(context,"请连接WIFI!");
            return "";
        }

        int netType = info.getType();
        int netSubtype = info.getSubtype();

        if (netType == ConnectivityManager.TYPE_WIFI) {  //WIFI
            return getWifiIpAddress(context);
        } else if (netType == ConnectivityManager.TYPE_MOBILE ) {   //MOBILE
            return getLocalIpAddress();
        } else {
            return "127.0.0.1";
        }
    }

    //使用wifi
    public static String getWifiIpAddress(Context context) {

        //获取wifi服务
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        //             if (!wifiManager.isWifiEnabled()) {
        //      <span style="white-space:pre">    </span>   wifiManager.setWifiEnabled(true);
        //             }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        return ip;
    }

    private static String intToIp(int i) {
        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }

    //使用GPRS
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("Exception", ex.toString());
        }
        return "127.0.0.1";
    }

    /**
     *  require the object are not null
     *
     * @param objects
     */
    public static void requireNonNull(Object... objects){
        if(objects == null) throw new NullPointerException();
        for (Object o : objects){
            if(o == null) throw new NullPointerException();
        }
    }

    /**
     ** 获取随机汉字
     ** @return
     */
   public static String getRandomWord(){
       String str = "";
       int heightPos;
       int lowPos;
       Random rd = new Random();
       heightPos = 176 + Math.abs(rd.nextInt(39));
       lowPos = 161 + Math.abs(rd.nextInt(93));
       byte[] bt = new byte[2];
       bt[0] = Integer.valueOf(heightPos).byteValue();
       bt[1] = Integer.valueOf(lowPos).byteValue();
       try {
           str = new String(bt, "GBK");
       } catch (Exception e) {
           e.printStackTrace();
       }
       return str;
   }
}
