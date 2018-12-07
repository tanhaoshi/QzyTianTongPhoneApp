package com.qzy.tiantong.service.phone;

import android.content.Context;
import android.text.TextUtils;


import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.netty.PhoneNettyManager;
import com.qzy.tiantong.service.phone.data.CallLogInfo;
import com.qzy.tiantong.service.phone.data.SmsInfo;
import com.qzy.tiantong.service.utils.PhoneUtils;
import com.qzy.tt.data.TtCallRecordProtos;
import com.qzy.tt.data.TtDeleCallLogProtos;
import com.qzy.tt.data.TtDeleSmsProtos;
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
    public static void syncCallLogInfo(final String ip, final Context context, final PhoneNettyManager phoneNettyManager) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int page = 0;
                int pageCount = 10;
                while (true) {
                    LogUtils.e("getCall log sync .....");
                    List<CallLogInfo> callLogInfo = PhoneUtils.getCallLog(context, page, pageCount);
                    page++;
                    if (callLogInfo == null || callLogInfo.size() <= 0) {
                        LogUtils.e("getCall log fininsh .....");
                        break;
                    }

                    LogUtils.e("callLogInfo size  = " + callLogInfo.size());
                    TtCallRecordProtos.TtCallRecordProto.Builder listRecorder = TtCallRecordProtos.TtCallRecordProto.newBuilder();
                    for (CallLogInfo callInfo : callLogInfo) {
                        LogUtils.e("callInfo = " + callInfo.toString());
                        TtCallRecordProtos.TtCallRecordProto.CallRecord callRecord = TtCallRecordProtos.TtCallRecordProto.CallRecord.newBuilder()
                                .setId(callInfo.getId())
                                .setPhoneNumber(callInfo.getNumber())
                                .setDate(callInfo.getDate())
                                .setName(callInfo.getName())
                                .setType(callInfo.getType())
                                .setDuration(callInfo.getTime())
                                .build();
                        listRecorder.addCallRecord(callRecord);
                    }

                    if (phoneNettyManager != null) {
                        phoneNettyManager.sendCallLogToPhoneClient(ip, listRecorder.build());
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
    public static void syncSmsInfo(final String ip, final Context context, final PhoneNettyManager phoneNettyManager) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                int page = 0;
                int pageCount = 10;
                while (true) {
                    LogUtils.e("getSms sync .....");
                    List<SmsInfo> smsList = PhoneUtils.getSms(context, page, pageCount);
                    page++;
                    if (smsList == null || smsList.size() <= 0) {
                        LogUtils.e("getSms sync finish .....");
                        break;
                    }
                    LogUtils.e("getSms sync size = " + smsList.size());
                    TtShortMessageProtos.TtShortMessage.Builder ttShortMessage = TtShortMessageProtos.TtShortMessage.newBuilder();
                    for (SmsInfo smsInfo : smsList) {
                       // LogUtils.d("smsInfo  = " + smsInfo.toString());
                        TtShortMessageProtos.TtShortMessage.ShortMessage shortMessage = TtShortMessageProtos.TtShortMessage.ShortMessage.newBuilder()
                                .setId(smsInfo.getId())
                                .setNumberPhone(smsInfo.getNumber())
                                .setName(smsInfo.getName())
                                .setMessage(smsInfo.getBody())
                                .setTime(smsInfo.getDate())
                                .setType(smsInfo.getType())
                                .setIsRead(smsInfo.getIsRead() == 1 ? true : false)
                                .build();
                        ttShortMessage.addShortMessage(shortMessage);
                    }


                    if (phoneNettyManager != null) {
                        phoneNettyManager.sendCallLogToPhoneClient(ip, ttShortMessage.build());
                    }

                }
            }
        }).start();
    }


    /**
     * 删除通话记录
     * @param context
     * @param ttDeleCallLog
     * @return
     */
    public static synchronized boolean deleteCallLog(Context context,TtDeleCallLogProtos.TtDeleCallLog ttDeleCallLog){
        try{
            if(ttDeleCallLog.getIsDeleAll()){
                return PhoneUtils.deleteAllCallLog(context);
            }

            String phoneNumber = ttDeleCallLog.getPhonenumber();
            if(!TextUtils.isEmpty(phoneNumber)){
                return PhoneUtils.deleteCallLogByPhoneNumber(context,phoneNumber);
            }

            long id = ttDeleCallLog.getServerDataId();
            if(id >= 0){
                return PhoneUtils.deleteCallLogByID(context,id);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return false;

    }

    /**
     * 删除通话记录
     * @param context
     * @param ttDeleSms
     * @return
     */
    public static synchronized void deleteSms(Context context,TtDeleSmsProtos.TtDeleSms ttDeleSms){
        try{
            if(ttDeleSms.getIsDeleAll()){
                 PhoneUtils.delAllSms(context);
            }

            String phoneNumber = ttDeleSms.getPhonenumber();
            if(TextUtils.isEmpty(phoneNumber)){
                 PhoneUtils.delSmsByPhonenumber(context,phoneNumber);
            }

            long id = ttDeleSms.getServerDataId();
            if(id >= 0){
                 PhoneUtils.delSmsById(context,id +"");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }



}