package com.qzy.tiantong.service.mobiledata;

import android.content.Context;
import android.support.annotation.Keep;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.qzy.tiantong.lib.utils.LogUtils;

public class CustomPhoneStateListener extends PhoneStateListener{

    private Context mContext;
    private IMobileDataLister mIMobileDataLister;

    public CustomPhoneStateListener(Context context,IMobileDataLister iMobileDataLister){
        this.mContext = context;
        this.mIMobileDataLister = iMobileDataLister;
        LogUtils.i("init data connecttion listener");
    }

    @Override
    public void onDataConnectionStateChanged(int state, int networkType) {
        super.onDataConnectionStateChanged(state, networkType);
        LogUtils.i("data connecttion change");
        switch (state){
            case TelephonyManager.DATA_DISCONNECTED:
                LogUtils.i("TelephonyManager.DATA_DISCONNECTED state = " + state);
                mIMobileDataLister.getMobileDataState(false);
                break;
            case TelephonyManager.DATA_CONNECTED:
                LogUtils.i("TelephonyManager.DATA_CONNECTED state = " +state);
                mIMobileDataLister.getMobileDataState(true);
                break;
            case TelephonyManager.DATA_CONNECTING:
                LogUtils.i("TelephonyManager.DATA_CONNECTING state = " + state);
                break;
            case TelephonyManager.DATA_SUSPENDED:
                LogUtils.i("TelephonyManager.DATA_SUSPENDED state = " +state);
                break;
                default:
                    mIMobileDataLister.getMobileDataState(false);
                    LogUtils.i("default state = " + state);
                    break;
        }
    }
}
