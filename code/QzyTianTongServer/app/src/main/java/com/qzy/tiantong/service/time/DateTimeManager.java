package com.qzy.tiantong.service.time;

import android.content.Context;

import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.netty.NettyServerManager;
import com.qzy.tiantong.service.utils.DateTimeUtils;
import com.qzy.tt.data.TtTimeProtos;
import com.qzy.tt.probuf.lib.data.PhoneCmd;
import com.qzy.tt.probuf.lib.data.PrototocalTools;

import java.util.Calendar;
import java.util.TimeZone;

public class DateTimeManager {
    private Context mContext;
    //服务端netty管理工具
    private NettyServerManager mNettyServerManager;

    public DateTimeManager(Context context,NettyServerManager serverManager){
        mContext = context;
        mNettyServerManager = serverManager;
        setTimeZone();
    }

    /**
     * 设置时区
     */
    private void setTimeZone(){
        String timeZone = DateTimeUtils.getDefaultTimeZone();
        LogUtils.d("timeZone = " + timeZone);
        if(!timeZone.equals("China Standard Time")){
            DateTimeUtils.setTimeZone("GMT+8");
        }

        if(!DateTimeUtils.is24Hour(mContext)){
            DateTimeUtils.set24Hour(mContext);
        }
    }

    /**
     * 设置系统时间
     * @param ttTime
     */
    public void setDataAndTime(TtTimeProtos.TtTime ttTime){
        if(ttTime == null){
            LogUtils.e("ttTime is null ");
            return;
        }

        LogUtils.d("phone time = " + ttTime.getDateTime() + " server time = " + DateTimeUtils.getCurrentTime());
        boolean isSet = ttTime.getIsSync();
        if(isSet){
            Calendar calendar = DateTimeUtils.getCalendarByDateTimeStr(ttTime.getDateTime());
            DateTimeUtils.setTimeZone(calendar.getTimeZone());
            DateTimeUtils.setSysTime(mContext,calendar);
            LogUtils.d("111 phone time = " + ttTime.getDateTime() + " server time = " + DateTimeUtils.getCurrentTime());
            return;
        }

        if(mNettyServerManager == null){
            LogUtils.e("mNettyServerManager is null ");
            return;
        }
        TtTimeProtos.TtTime timet = TtTimeProtos.TtTime.newBuilder()
                .setIp(ttTime.getIp())
                .setDateTimeServer(DateTimeUtils.getCurrentTime())
                .build();
        mNettyServerManager.sendData(null, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.response_tt_time,timet));
        LogUtils.d("222 phone time = " + ttTime.getDateTime() + " server time = " + DateTimeUtils.getCurrentTime());
    }





}
