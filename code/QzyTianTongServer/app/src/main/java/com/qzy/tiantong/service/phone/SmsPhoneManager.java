package com.qzy.tiantong.service.phone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.phone.obersever.SmsDatabaseChaneObserver;
import com.qzy.tiantong.service.utils.PhoneUtils;

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

    //短信数据库监听
    private SmsDatabaseChaneObserver mSmsDBChangeObserver;


    public SmsPhoneManager(Context context, IOnSMSCallback callback) {
        mContext = context;
        mCallback = callback;
        registerSms();
    }


    /**
     * 注册接受短信
     */
    public static final String SMS_ACTION_RECEIVER = "android.provider.Telephony.SMS_RECEIVED";

    private void registerSms() {

        //处理返回的发送状态
        String SENT_SMS_ACTION = "SENT_SMS_ACTION";
        sentIntent = new Intent(SENT_SMS_ACTION);
        mContext.registerReceiver(mSendReceiver, new IntentFilter(SENT_SMS_ACTION));

        //处理返回的接收状态
        String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
        // create the deilverIntent parameter
        deliverIntent = new Intent(DELIVERED_SMS_ACTION);
        mContext.registerReceiver(mReceReceiver, new IntentFilter(DELIVERED_SMS_ACTION));


        //接受短信
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SMS_ACTION_RECEIVER);
        mContext.registerReceiver(mReceiverSms, intentFilter);

    }

    /**
     * 解除注册接受短信
     */
    private void unregisterSms() {
        mContext.unregisterReceiver(mReceiverSms);

        mContext.unregisterReceiver(mSendReceiver);
        mContext.unregisterReceiver(mReceReceiver);
    }


    /**
     * 接受短信
     */
    private BroadcastReceiver mReceiverSms = new BroadcastReceiver() {
        @SuppressLint("NewApi")
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LogUtils.d("sms receiver action = " + action);
            if (intent.getAction().equals(SMS_ACTION_RECEIVER)) {

                Bundle bundle = intent.getExtras();
                Object[] pdus = (Object[]) bundle.get("pdus");
                String format = intent.getStringExtra("format");
                SmsMessage[] messages = new SmsMessage[pdus.length];
                LogUtils.d("onReceive format is " + format + " !!!!!! ");
                for (int i = 0; i < messages.length; i++) {
//                createFromPdu(byte []pdu)方法已被废弃
//                原因是为了同时支持3GPP和3GPP2
//                他们是移动系统通信标准的拟定组织分别拟定的GSM/UMTS/LTE标准和CDMA/LTE标准
//                因此推荐是用的方法是createFromPdu(byte[] pdu, String format)
//                其中fotmat可以是SmsConstants.FORMAT_3GPP或者SmsConstants.FORMAT_3GPP2
                    byte[] sms = (byte[]) pdus[i];
                    messages[i] = SmsMessage.createFromPdu(sms, format);
                }
                //获取发送方手机号码
                String address = messages[0].getOriginatingAddress();
                String fullMessage = "";
                for (SmsMessage message : messages) {
                    //获取短信内容（短信内容太长会被分段）
                    fullMessage += message.getMessageBody();
                }


                LogUtils.d("receiver sms ：\n sender is ：" + address + " body sms =  " + fullMessage);
                if (mCallback != null) {
                    mCallback.onReceiveSms(address, fullMessage);
                }
                /*SmsMessage[] smsMessages = PhoneUtils.getMessageFromIntent(intent);

                if (smsMessages != null && smsMessages.length > 0) {
                    String phoneNumber = null;
                    StringBuilder stringBuilder = new StringBuilder();

                    for (SmsMessage smsMessage : smsMessages) {

                        phoneNumber = smsMessage.getDisplayOriginatingAddress();
                        LogUtils.d("receiver sms ：\n sender is ：" + phoneNumber);
                        stringBuilder.append(smsMessage.getDisplayOriginatingAddress());
                        LogUtils.d("\n------sms content -------\n");
                        stringBuilder.append(smsMessage.getDisplayMessageBody());
                        LogUtils.d("\n------getMessageBody-------\n");
                        stringBuilder.append(smsMessage.getMessageBody());
                    }
                }*/


            }
        }
    };


    /**
     * 注册短信数据库变化监听
     *
     * @param context
     */
    private void registerSmsDatabaseChangeObserver(Context context) {
        //因为，某些机型修改rom导致没有getContentResolver
        final Uri SMS_MESSAGE_URI = Uri.parse("content://sms");
        try {
            mSmsDBChangeObserver = new SmsDatabaseChaneObserver(context.getContentResolver(), new Handler());
            context.getContentResolver().registerContentObserver(SMS_MESSAGE_URI, true, mSmsDBChangeObserver);
        } catch (Throwable b) {
        }
    }

    /**
     * 接触短信数据库监听
     *
     * @param context
     */
    private void unregisterSmsDatabaseChangeObserver(Context context) {
        try {
            context.getContentResolver().unregisterContentObserver(mSmsDBChangeObserver);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                //  mCallback.onReceiveSuccess(ip, phoneNumber);
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

        unregisterSms();
    }

    public interface IOnSMSCallback {
        void onSendSuccess(String ip, String phoneNumber);

        void onSendFailed(String ip, String phoneNumber);

        void onReceiveSuccess(String ip, String phoneNumber);

        void onReceiveFailed(String ip, String phoneNumber);

        void onReceiveSms(String phoneNumber, String smsBody);
    }

}
