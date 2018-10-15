package com.qzy.tiantong.service.phone;

import android.content.Context;


import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.netty.PhoneNettyManager;
import com.qzy.tiantong.service.phone.data.CallLogInfo;
import com.qzy.tiantong.service.phone.data.SmsInfo;
import com.qzy.tiantong.service.utils.PhoneUtils;
import com.qzy.tt.data.TtCallRecordProtos;
import com.qzy.tt.data.TtShortMessageProtos;

import java.util.List;

/**
 * 通话记录管理
 */
public class CallLogManager {

    /**
     * 同步信息
     *
     * @param context
     */
    public static void syncCallLogInfo(final String ip,final Context context, final PhoneNettyManager phoneNettyManager) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtils.e("getCall log sync .....");
                List<CallLogInfo> callLogInfo = PhoneUtils.getCallLog(context);
                if (callLogInfo != null) {
                    TtCallRecordProtos.TtCallRecordProto.Builder listRecorder = TtCallRecordProtos.TtCallRecordProto.newBuilder();
                    for (CallLogInfo callInfo : callLogInfo) {
                        LogUtils.e("callInfo = " + callInfo.toString());
                        TtCallRecordProtos.TtCallRecordProto.CallRecord callRecord = TtCallRecordProtos.TtCallRecordProto.CallRecord.newBuilder()
                                .setPhoneNumber(callInfo.getNumber())
                                .setDate(callInfo.getDate())
                                .setName(callInfo.getName())
                                .setType(callInfo.getType())
                                .setDuration(callInfo.getTime())
                                .build();
                        listRecorder.addCallRecord(callRecord);
                    }

                    if (phoneNettyManager != null) {
                        phoneNettyManager.sendCallLogToPhoneClient(ip,listRecorder.build());
                    }
                }
            }
        }).start();


    }

    /**
     * 同步短信
     *
     * @param context
     * @param phoneNettyManager
     */
    public static void syncSmsInfo(final String ip,final Context context, final PhoneNettyManager phoneNettyManager) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtils.e("getSms sync .....");
                List<SmsInfo> smsList = PhoneUtils.getSms(context);
                if (smsList != null) {
                    TtShortMessageProtos.TtShortMessage.Builder ttShortMessage = TtShortMessageProtos.TtShortMessage.newBuilder();
                    for (SmsInfo smsInfo : smsList) {
                        LogUtils.d("smsInfo  = " + smsInfo.toString());
                        TtShortMessageProtos.TtShortMessage.ShortMessage shortMessage = TtShortMessageProtos.TtShortMessage.ShortMessage.newBuilder()
                                .setNumberPhone(smsInfo.getNumber())
                                .setName(smsInfo.getName())
                                .setMessage(smsInfo.getBody())
                                .setTime(smsInfo.getDate())
                                .setType(smsInfo.getType())
                                .build();
                        ttShortMessage.addShortMessage(shortMessage);
                    }
                    if (phoneNettyManager != null) {
                        phoneNettyManager.sendCallLogToPhoneClient(ip,ttShortMessage.build());
                    }

                }
            }
        }).start();

    }


}