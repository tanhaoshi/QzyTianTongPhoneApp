package com.tt.qzy.view.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.qzy.tt.phone.data.SmsBean;
import com.qzy.tt.phone.data.TtPhoneDataManager;
import com.socks.library.KLog;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.SPUtils;
import com.tt.qzy.view.utils.TimeTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class TimerService extends Service{

    private TimeTask mTimeTask;

    private String receive = "";
    private String content = "";

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
        if(mTimeTask == null){
            mTimeTask = new TimeTask(timerDuration, new TimerTask() {
                @Override
                public void run() {
//                    if(!SPUtils.containsShare(TimerService.this,Constans.SOS_FLAG)){
                        timerSendMessage();
//                    }
                }
            });
            mTimeTask.start();
        }else{
            mTimeTask.start();
        }
    }

    private void timerSendMessage(){
        String longitude = (String) SPUtils.getShare(getApplicationContext(),Constans.LONGITUDE_VALUE,"");
        String latitude =  (String)SPUtils.getShare(getApplicationContext(),Constans.LATITUDE_VALUE,"");
        receive = SPUtils.getShare(getApplicationContext(), Constans.CRY_HELP_PHONE,"").toString();
        content = SPUtils.getShare(getApplicationContext(),Constans.CRY_HELP_SHORTMESSAGE,"").toString()
        +"经度:"+longitude + " 纬度:"+latitude;
        //EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_SEND_SMS, new SmsBean(receive, content)));
        SmsBean smsBean = new SmsBean(receive, content);
        TtPhoneDataManager.getInstance().sendSmsTtPhone(smsBean);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimeTask.stop();
        mTimeTask = null;
    }
}
