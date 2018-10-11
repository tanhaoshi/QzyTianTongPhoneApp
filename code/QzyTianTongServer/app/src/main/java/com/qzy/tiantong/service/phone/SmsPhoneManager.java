package com.qzy.tiantong.service.phone;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;

import com.qzy.tiantong.lib.utils.LogUtils;

import java.util.List;

/**
 * Created by yj.zhang on 2018/9/18.
 */

public class SmsPhoneManager {

    private Context mContext;
    private PendingIntent deliverPI;
    private PendingIntent sentPI;

    private Intent deliverIntent;

    private Intent sentIntent;

    private IOnSMSCallback mCallback;

    public SmsPhoneManager(Context context, IOnSMSCallback callback) {
        mContext = context;
        mCallback = callback;
        register();
    }

    private void register() {

        //处理返回的发送状态
        String SENT_SMS_ACTION = "SENT_SMS_ACTION";
        sentIntent = new Intent(SENT_SMS_ACTION);
        mContext.registerReceiver(mSendReceiver, new IntentFilter(SENT_SMS_ACTION));

        //处理返回的接收状态
        String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
        // create the deilverIntent parameter
        deliverIntent = new Intent(DELIVERED_SMS_ACTION);
        mContext.registerReceiver(mReceReceiver, new IntentFilter(DELIVERED_SMS_ACTION));
    }


    /**
     * 发送状态
     */
    private BroadcastReceiver mSendReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context _context, Intent _intent) {
            String phoneNumber = _intent.getStringExtra("phone_number");
            String ip = _intent.getStringExtra("ip");
            LogUtils.e("ip = " + ip + " phoneNumber = " + phoneNumber);
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    LogUtils.d("send sms RESULT_OK .....");
                    if (mCallback != null) {
                        mCallback.onSendSuccess(ip, phoneNumber);
                    }
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    LogUtils.d("send sms RESULT_ERROR_GENERIC_FAILURE .....");
                    if (mCallback != null) {
                        mCallback.onSendFailed(ip, phoneNumber);
                    }
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    LogUtils.d("send sms RESULT_ERROR_RADIO_OFF .....");
                    if (mCallback != null) {
                        mCallback.onSendFailed(ip, phoneNumber);
                    }
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    LogUtils.d("send sms RESULT_ERROR_NULL_PDU .....");
                    if (mCallback != null) {
                        mCallback.onSendFailed(ip, phoneNumber);
                    }
                    break;
            }
        }
    };


    /**
     * 对方接受状态
     */
    private BroadcastReceiver mReceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context _context, Intent _intent) {
            String phoneNumber = _intent.getStringExtra("phone_number");
            String ip = _intent.getStringExtra("ip");
            LogUtils.e("ip = " + ip + " phoneNumber = " + phoneNumber);
            LogUtils.d("receiver sms RESULT_OK .....");
            if (mCallback != null) {
                mCallback.onReceiveSuccess(ip, phoneNumber);
            }
            /*switch (getResultCode()) {
                case Activity.RESULT_OK:

                    break;
                default:
                    if (mCallback != null) {
                        mCallback.onReceiveFailed(ip, phoneNumber);
                    }
                    break;
            }*/
        }
    };

    /**
     * 发送短信
     *
     * @param ip
     * @param phoneNumber
     * @param message
     */
    public void sendSms(final String ip, final String phoneNumber, final String message) {
        sentIntent.putExtra("phone_number", phoneNumber);
        sentIntent.putExtra("ip", ip);
        sentPI = PendingIntent.getBroadcast(mContext, 0, sentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        deliverIntent.putExtra("phone_number", phoneNumber);
        deliverIntent.putExtra("ip", ip);
        deliverPI = PendingIntent.getBroadcast(mContext, 0, deliverIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 获取短信管理器
        SmsManager smsManager = SmsManager.getDefault();
        // 拆分短信内容（手机短信长度限制）
        List<String> divideContents = smsManager.divideMessage(message);
        for (String text : divideContents) {
            smsManager.sendTextMessage(phoneNumber, null, text, sentPI, deliverPI);
            LogUtils.d(ip + " send sms  " + " to " + phoneNumber);
        }
    }

    /**
     * 释放
     */
    public void free() {
        mContext.unregisterReceiver(mSendReceiver);
        mContext.unregisterReceiver(mReceReceiver);
    }

    public interface IOnSMSCallback {
        void onSendSuccess(String ip, String phoneNumber);

        void onSendFailed(String ip, String phoneNumber);

        void onReceiveSuccess(String ip, String phoneNumber);

        void onReceiveFailed(String ip, String phoneNumber);
    }

}
