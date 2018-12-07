package com.tt.qzy.view.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.qzy.eventbus.EventBusUtils;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.tt.phone.data.SmsBean;
import com.socks.library.KLog;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.SPUtils;
import com.tt.qzy.view.utils.TimeTask;

import java.util.TimerTask;

public class TimerService extends Service{

    private TimeTask mTimeTask;

    private String receive = "";
    private String content = "";

    private String latitude = "";  // 纬度
    private String longitude = ""; // 经度

    public TimerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String timerStr =  (String) SPUtils.getShare(this,Constans.CRY_HELP_TIMETIMER,"60");
        Long timerDuration = Long.valueOf(timerStr) * 1000 ;
        KLog.i(" look over duration value = " + timerDuration);
        if(mTimeTask == null){
            mTimeTask = new TimeTask(timerDuration, new TimerTask() {
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
        content = SPUtils.getShare(getApplicationContext(),Constans.CRY_HELP_SHORTMESSAGE,"").toString();
        EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_SEND_SMS,
                new SmsBean(receive, content)));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimeTask.stop();
    }
}
