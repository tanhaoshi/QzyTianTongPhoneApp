package com.tt.qzy.view.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.qzy.eventbus.EventBusUtils;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.tt.phone.data.SmsBean;
import com.socks.library.KLog;
import com.tt.qzy.view.fragment.MainFragment;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.SPUtils;
import com.tt.qzy.view.utils.TimeTask;

import java.util.TimerTask;

public class TimerService extends Service implements MainFragment.GpsCallback{

    private TimeTask mTimeTask;

    private String receive = "";
    private String content = "";

    private String latitude = "";  // 纬度
    private String longitude = ""; // 经度

    public TimerService(MainFragment mainFragment) {
        mainFragment.setGpsCallback(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if(mTimeTask == null){
            mTimeTask = new TimeTask(60000, new TimerTask() {
                @Override
                public void run() {
                    timerSendMessage();
                }
            });
            mTimeTask.start();
        }else{
            mTimeTask.start();
        }
    }

    private void timerSendMessage(){
        receive = SPUtils.getShare(getApplicationContext(), Constans.CRY_HELP_PHONE,"").toString();
        content = SPUtils.getShare(getApplicationContext(),Constans.CRY_HELP_SHORTMESSAGE,"").toString() + "经度:"+longitude
                +"," +"纬度:"+latitude;
        EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_SEND_SMS,
                new SmsBean(receive, content)));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimeTask.stop();
    }


    @Override
    public void setGpsValue(String lat, String longitude) {
        if(lat.length() == 0 || longitude == null){
            return;
        }
        if(longitude.length() == 0 || longitude == null){
            return;
        }
        this.latitude = lat;
        this.longitude = longitude;
        SPUtils.putShare(getApplicationContext(),Constans.CRY_HELP_SHORTMESSAGE,
                SPUtils.getShare(getApplicationContext(),Constans.CRY_HELP_SHORTMESSAGE,"").toString()+ "经度:"+longitude
                        +"," +"纬度:"+latitude);

    }
}
