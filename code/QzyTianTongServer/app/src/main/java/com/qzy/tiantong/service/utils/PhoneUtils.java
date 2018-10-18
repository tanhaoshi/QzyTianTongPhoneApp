package com.qzy.tiantong.service.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;

import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.phone.IPhoneDataSyncListener;
import com.qzy.tiantong.service.phone.data.CallLogInfo;
import com.qzy.tiantong.service.phone.data.SmsInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yj.zhang on 2018/8/9.
 */

public class PhoneUtils {

    public static final int NETWORKTYPE_WIFI = 0;
    public static final int NETWORKTYPE_4G = 1;
    public static final int NETWORKTYPE_2G = 2;
    public static final int NETWORKTYPE_NONE = 3;

    public static int getNetWorkType(Context context) {
        int mNetWorkType = -1;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase("WIFI")) {
                mNetWorkType = NETWORKTYPE_WIFI;
            } else if (type.equalsIgnoreCase("MOBILE")) {
                return isFastMobileNetwork(context) ? NETWORKTYPE_4G : NETWORKTYPE_2G;
            }
        } else {
            mNetWorkType = NETWORKTYPE_NONE;//没有网络
        }
        return mNetWorkType;
    }

    /**
     * 判断网络类型
     */
    private static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_LTE) {
            //这里只简单区分两种类型网络，认为4G网络为快速，但最终还需要参考信号值
            return true;
        }
        return false;
    }

    /**
     * 检查是否有sim卡
     *
     * @param context
     * @return
     */
    public static boolean ishasSimCard(Context context) {
        TelephonyManager telMgr = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int simState = telMgr.getSimState();
        boolean result = false;
        switch (simState) {
            case TelephonyManager.SIM_STATE_READY:
                result = true;
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
            case TelephonyManager.SIM_STATE_ABSENT:
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
            default:
                result = false;
                break;
        }
        return result;
    }


    /**
     * 获取所有短信
     *
     * @param context
     * @return
     */
    public static List<SmsInfo> getSms(Context context,int page,int pageCount) {
        List<SmsInfo> smsInfoList = null;
        String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
        String sortOrder = "date desc limit  " + page * pageCount + "," + pageCount;
        try {
            Uri smsUri = Uri.parse("content://sms/");
            Cursor cursor = context.getContentResolver().query(smsUri, projection, null, null, sortOrder);

            String smsType;
            String smsName;
            String smsNumber;
            String smsBody;
            String smsDate;
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    smsType = cursor.getString(cursor.getColumnIndex("type"));
                    int type = Integer.parseInt(smsType);
                    if (type == 1) {
                        smsType = "接收";
                    } else if (type == 2) {
                        smsType = "发送";
                    } else {
                        smsType = "其他";
                    }

                    smsName = cursor.getString(cursor.getColumnIndex("person"));
                    if (smsName == null) {
                        smsName = "未知号码";
                    }


                    smsNumber = cursor.getString(cursor.getColumnIndex("address"));
                    smsName = getContactNameByAddr(context, smsNumber);
                    smsBody = cursor.getString(cursor.getColumnIndex("body"));
                    smsDate = cursor.getString(cursor.getColumnIndex("date"));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date d = new Date(Long.parseLong(smsDate));
                    smsDate = dateFormat.format(d);
                    SmsInfo smsInfo = new SmsInfo(type, smsName, smsNumber, smsBody, smsDate);
                    if (smsInfoList == null) {
                        smsInfoList = new ArrayList<>();
                    }
                    smsInfoList.add(smsInfo);
                }
                cursor.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return smsInfoList;
    }

    /**
     * get phone name
     *
     * @param context
     * @param phoneNumber
     * @return
     */
    public static String getContactNameByAddr(Context context, String phoneNumber) {

        Uri personUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cur = context.getContentResolver().query(personUri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cur.moveToFirst()) {
            int nameIdx = cur.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            String name = cur.getString(nameIdx);
            cur.close();
            return name;
        }
        return "未知号码";
    }


    /**
     * 查询通讯记录
     * @param context
     * @param page     页数
     * @param pageCount  每页的个数
     * @return
     */
    public static List<CallLogInfo> getCallLog(Context context,int page,int pageCount) {
        List<CallLogInfo> callLogList = null;
        String[] projection = {CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER, CallLog.Calls.TYPE, CallLog.Calls.DURATION, CallLog.Calls.DATE};
        String sortOrder = CallLog.Calls.DEFAULT_SORT_ORDER + " limit  " + page * pageCount + "," + pageCount;  // 从那里取  取多少个
        try {
            Uri callLogUri = CallLog.Calls.CONTENT_URI;
            Cursor cursor = context.getContentResolver().query(callLogUri, projection, null, null, sortOrder);
            String callLogName;
            String callLogNumber;
            String callLogDate;
            int callLogType;
            int callLogTime;
            String type;
            String time;

            while (cursor.moveToNext()) {
                callLogName = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                if (callLogName == null) {
                    callLogName = "未知号码";
                }
                callLogNumber = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));

                callLogDate = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
                SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date d = new Date(Long.parseLong(callLogDate));
                callLogDate = DateFormat.format(d);

                callLogType = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
                if (callLogType == 1) {
                    type = "来电";
                } else if (callLogType == 2) {
                    type = "拨出";
                } else
                    type = "未接";

                callLogTime = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));
                if (type == "未接") {
                    time = "未接";
                } else {
                    //time = timeChange(callLogTime);
                }

                if (callLogList == null) {
                    callLogList = new ArrayList<>();
                }

                CallLogInfo callLogInfo = new CallLogInfo(callLogName, callLogNumber, callLogDate, callLogType, callLogTime);
                callLogList.add(callLogInfo);
            }
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return callLogList;
    }


    /**
     * 获取短信内容
     * @param intent
     * @return
     */
    public static SmsMessage[] getMessageFromIntent(Intent intent) {
        SmsMessage retmeMessage[] = null;
        Bundle bundle = intent.getExtras();
        Object pdus[] = (Object[]) bundle.get("pdus");
        retmeMessage = new SmsMessage[pdus.length];
        for (int i = 0; i < pdus.length; i++) {
            byte[] bytedata = (byte[]) pdus[i];
            retmeMessage[i] = SmsMessage.createFromPdu(bytedata);
        }
        return retmeMessage;
    }


}
