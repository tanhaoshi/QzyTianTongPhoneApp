package com.qzy.tiantong.service.gps;

import android.content.Context;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;

import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.atcommand.AtCommandToolManager;
import com.qzy.tiantong.service.atcommand.AtCommandTools;
import com.qzy.tiantong.service.netty.NettyServerManager;
import com.qzy.tiantong.service.service.ITianTongServer;
import com.qzy.tiantong.service.utils.GpsUtils;
import com.qzy.tt.data.TtPhonePositionProtos;
import com.qzy.tt.probuf.lib.data.PhoneCmd;
import com.qzy.tt.probuf.lib.data.PrototocalTools;

import java.util.Iterator;

public class GpsManager {

    private Context mContext;

    //服务端netty管理工具
    private NettyServerManager mNettyServerManager;

    private ITianTongServer mServer;

    private LocationManager locationManager;

    private Location mCurrenLocation;

    public boolean isGpsOpen = false;

    //at 指令管理
    private AtCommandToolManager mAtCommandToolManager;

    public GpsManager(Context context, NettyServerManager manager,ITianTongServer server) {
        mContext = context;
        mNettyServerManager = manager;
        mServer = server;

        openGPS(true);

        initLocationManager();

        //openGps();

        initAtCommandManager();
    }


    private void initAtCommandManager() {
        mAtCommandToolManager = new AtCommandToolManager(mContext, new AtCommandToolManager.IAtResultListener() {
            @Override
            public void onResult(String cmd, String result) {
                if (AtCommandTools.at_command_open_gps.equals(cmd)) {
                    if (result.equals("ok")) {
                        mCurrenLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); // 通过GPS获取位置
                        isGpsOpen = true;
                    } else {
                        //isGpsOpen = false;
                    }
                } else if (AtCommandTools.at_command_close_gps.equals(cmd)) {

                    if (result.equals("ok")) {
                        mCurrenLocation = null;
                        isGpsOpen = false;
                    } else {
                        // isGpsOpen = true;
                    }
                }
            }
        });
    }

    private void initLocationManager() {
        try {
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

            //状态监听
            locationManager.addGpsStatusListener(gpsStatus);

        } catch (Exception e) {
            e.printStackTrace();
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

        LogUtils.e("parse pro tocalcontrol status ,,,");

        if (ttPhonePosition.getIsOpen()) {

            openGps();

            GpsUtils.openGPS(mContext, Settings.Secure.LOCATION_MODE_OFF, Settings.Secure.LOCATION_MODE_HIGH_ACCURACY);

            sendLoactionToPhoneClient(mCurrenLocation);

        } else {

            closeGps();

            GpsUtils.closeGPS(mContext, Settings.Secure.LOCATION_MODE_HIGH_ACCURACY, Settings.Secure.LOCATION_MODE_OFF);

        }

    }


    /**
     * 打开GPS
     */
    public void openGps() {

        if(mServer != null){
            mServer.getSystemSleepManager().wakeupTianTong();
        }

        LogUtils.d("open gps .....");

        if (mAtCommandToolManager != null) {
            mAtCommandToolManager.sendAtCommand(AtCommandTools.at_command_open_gps);
        }

        isGpsOpen = true;

    }



    /**
     * 关闭GPS
     */
    public void closeGps() {
        if (mAtCommandToolManager != null) {
            mAtCommandToolManager.sendAtCommand(AtCommandTools.at_command_close_gps);
            mCurrenLocation = null;
            sendGpsState();
        }
        isGpsOpen = false;
        LogUtils.d("close gps .....");
        if(mServer != null){
            mServer.getSystemSleepManager().sleepTianTong();
        }

    }

    /**
     * 定时发送经纬度状态给手机
     */
    public void sendGpsState() {
        sendLoactionToPhoneClient(mCurrenLocation);
    }

    /**
     * 发送经纬度给手机
     *
     * @param location
     */
    private void sendLoactionToPhoneClient(Location location) {
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
        if(location != null){
            mNettyServerManager.sendData(null, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.tt_gps_position, ttPhonePosition));
        }

    }

    /**
     * 获取gps
     * @param location
     * @return
     */
    public TtPhonePositionProtos.TtPhonePosition getLoactionToPhoneClientNew(Location location) {
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


        return ttPhonePosition;

    }

    //打开或者关闭gps
    public void openGPS(boolean open) {
        if (Build.VERSION.SDK_INT < 19) {
            Settings.Secure.setLocationProviderEnabled(mContext.getContentResolver(), LocationManager.GPS_PROVIDER, open);
        } else {
            if (!open) {
                Settings.Secure.putInt(mContext.getContentResolver(), Settings.Secure.LOCATION_MODE, android.provider.Settings.Secure.LOCATION_MODE_OFF);
            } else {
                Settings.Secure.putInt(mContext.getContentResolver(), Settings.Secure.LOCATION_MODE, android.provider.Settings.Secure.LOCATION_MODE_BATTERY_SAVING);
            }
        }
    }

    /**
     * 获取当前经纬度
     *
     * @return
     */
    public Location getmCurrenLocation() {
        return mCurrenLocation;
    }

    public void free() {
        if (mAtCommandToolManager != null) {
            mAtCommandToolManager.free();
        }
    }



    private GpsStatus.Listener gpsStatus = new GpsStatus.Listener() {

        private  int gpscount = 0;

        @Override
        public void onGpsStatusChanged(int event) {

            // TODO Auto-generated method stub
            if (event == GpsStatus.GPS_EVENT_FIRST_FIX) {
            //第一次定位  
            } else if (event == GpsStatus.GPS_EVENT_SATELLITE_STATUS) {
                //卫星状态改变  
                GpsStatus gpsStauts = locationManager.getGpsStatus(null); // 取当前状态  
                int maxSatellites = gpsStauts.getMaxSatellites(); //获取卫星颗数的默认最大值  

                Iterator<GpsSatellite> it = gpsStauts.getSatellites().iterator();//创建一个迭代器保存所有卫星  
                while (it.hasNext() && gpscount <= maxSatellites) {
                    GpsSatellite s = it.next();
                    //可见卫星数量
                    if (s.usedInFix()) {
                    //已定位卫星数量
                        gpscount++;
                    }
                }
               LogUtils.e("gps count " + gpscount);



                mHandler.removeCallbacks(runnable);
                mHandler.postDelayed(runnable,10 * 1000);

            } else if (event == GpsStatus.GPS_EVENT_STARTED) {
            //定位启动  
                LogUtils.e("gps start " );
            } else if (event == GpsStatus.GPS_EVENT_STOPPED) {
            //定位结束  
                LogUtils.e("gps stop " );
            }
        }
    };


    private Handler mHandler = new Handler(){};

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                LogUtils.e("lost gps signal ..... ");
                mCurrenLocation = null;
                sendGpsState();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };


}
