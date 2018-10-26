package com.qzy.tiantong.service.gps;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;

import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.netty.NettyServerManager;
import com.qzy.tt.data.TtPhonePositionProtos;
import com.qzy.tt.probuf.lib.data.PhoneCmd;
import com.qzy.tt.probuf.lib.data.PrototocalTools;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class GpsManager {

    private static final String action_gps_open = "com.qzy.tt.ACTION_GPS_OPEN";
    private static final String action_gps_close = "com.qzy.tt.ACTION_GPS_CLOSE";

    private static final String action_gps_result = "com.qzy.tt.ACTION_GPS_RESULT";
    private static final String extra_option = "extra_option";
    private static final String extra_value = "extra_value";

    private Context mContext;

    //服务端netty管理工具
    private NettyServerManager mNettyServerManager;


    private LocationManager locationManager;

    private Location mCurrenLocation;

    private boolean isGpsOpen = false;

    public GpsManager(Context context, NettyServerManager manager) {
        mContext = context;
        mNettyServerManager = manager;

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(action_gps_result);
        mContext.registerReceiver(mReceiver, intentFilter);

        openGPS(true);

        initLocationManager();

        //openGps();
    }

    private void initLocationManager() {
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) mContext.getSystemService(serviceName);
        // 查找到服务信息
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
        criteria.setAltitudeRequired(true);
        criteria.setBearingRequired(true);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_HIGH); // 低功耗

        String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
        mCurrenLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); // 通过GPS获取位置
        //updateToNewLocation(location);
        // 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1 * 1000, 1, listener);
        //location.getLatitude();
        if (mCurrenLocation != null) {
            LogUtils.d("initLocationManager lat =" + mCurrenLocation.getLatitude() + " lng =" + mCurrenLocation.getLongitude());
        }
    }


    private LocationListener listener = new LocationListener() {


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub
            LogUtils.d("onStatusChanged lat " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
            LogUtils.d("onProviderEnabled lat " + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
            LogUtils.d("onProviderDisabled lat " + provider);
        }


        public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub
            mCurrenLocation = location;
            LogUtils.d("onLocationChanged lat =" + location.getLatitude() + " lng =" + location.getLongitude());
            sendLoactionToPhoneClient(mCurrenLocation);
        }

    };

    /**
     * 解析控制命令
     *
     * @param ttPhonePosition
     */
    public void parseProtocalControl(TtPhonePositionProtos.TtPhonePosition ttPhonePosition) {
        if (ttPhonePosition == null) {
            LogUtils.e("ttPhonePosition is null");
            return;
        }

        if (ttPhonePosition.getIsOpen()) {
            openGps();

            sendLoactionToPhoneClient(mCurrenLocation);

        } else {
            closeGps();
        }

    }


    /**
     * 打开GPS
     */
    public void openGps() {
        Intent intent = new Intent(action_gps_open);
        mContext.sendBroadcastAsUser(intent, getUserHandleALL());
        LogUtils.d("open gps .....");
    }

    private UserHandle getUserHandleALL() {
        UserHandle userHandle = null;
        try {
            Field[] fields = UserHandle.class.getDeclaredFields();
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    continue;
                }

                if (field.getName().equals("ALL")) {
                    //LogUtils.e("get userHandle ..........");
                    userHandle = (UserHandle) field.get(UserHandle.class);
                    // LogUtils.e("get userHandle success ..........");
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userHandle;
    }


    /**
     * 关闭GPS
     */
    public void closeGps() {
        Intent intent = new Intent(action_gps_close);
        mContext.sendBroadcastAsUser(intent, getUserHandleALL());
        LogUtils.d("close gps .....");
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LogUtils.e("action = " + action);
            if (action_gps_result.equals(action)) {
                String optionType = intent.getStringExtra("extra_option");
                LogUtils.e("optionType = " + optionType);
                if (optionType.equals("open")) {
                    String value = intent.getStringExtra(extra_value);
                    LogUtils.e("value = " + value);

                    if (value.equals("ok")) {
                        mCurrenLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); // 通过GPS获取位置
                        isGpsOpen = true;
                    } else {
                        //isGpsOpen = false;
                    }

                } else if (optionType.equals("close")) {
                    String value = intent.getStringExtra(extra_value);
                    LogUtils.e("value = " + value);

                    if (value.equals("ok")) {
                        mCurrenLocation = null;
                        isGpsOpen = false;
                    } else {
                       // isGpsOpen = true;
                    }

                }
            }

        }
    };

    /**
     * 定时发送经纬度状态给手机
     */
    public void sendGpsState(){
        sendLoactionToPhoneClient(mCurrenLocation);
    }


    /**
     * 发送经纬度给手机
     *
     * @param location
     */
    private void sendLoactionToPhoneClient(Location location) {
        LogUtils.d("isGpsOpen = " + isGpsOpen);
        TtPhonePositionProtos.TtPhonePosition ttPhonePosition = null;
        if (location == null) {
            ttPhonePosition = TtPhonePositionProtos.TtPhonePosition.newBuilder()
                    .setIsOpen(isGpsOpen)
                    .setLatItude("")
                    .setLongItude("")
                    .setResponseStatus(true)
                    .build();


        } else {
            ttPhonePosition = TtPhonePositionProtos.TtPhonePosition.newBuilder()
                    .setIsOpen(isGpsOpen)
                    .setLatItude(location.getLatitude() + "")
                    .setLongItude(location.getLongitude() + "")
                    .setResponseStatus(true)
                    .build();
        }


        if (mNettyServerManager == null) {
            LogUtils.e("mNettyServerManager is null ...");
            return;
        }

        mNettyServerManager.sendData(null, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.tt_gps_position, ttPhonePosition));

    }

    //打开或者关闭gps
    public void openGPS(boolean open) {
        if (Build.VERSION.SDK_INT <19) {
            Settings.Secure.setLocationProviderEnabled(mContext.getContentResolver(), LocationManager.GPS_PROVIDER, open);
        }else{
            if(!open){
                Settings.Secure.putInt(mContext.getContentResolver(), Settings.Secure.LOCATION_MODE, android.provider.Settings.Secure.LOCATION_MODE_OFF);
            }else{
                Settings.Secure.putInt(mContext.getContentResolver(), Settings.Secure.LOCATION_MODE, android.provider.Settings.Secure.LOCATION_MODE_BATTERY_SAVING);
            }
        }
    }




    public void free() {
        mContext.unregisterReceiver(mReceiver);
    }


}
