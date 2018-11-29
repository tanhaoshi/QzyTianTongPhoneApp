package com.qzy.tiantong.service.mobiledata;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.PhoneStateListener;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import com.qzy.tiantong.lib.utils.LogUtils;

import java.lang.reflect.Method;

public final class MobileDataManager implements IMobileDataLister{

    private Context mContext;
    private TelephonyManager telephonyService;
    private CustomPhoneStateListener mCustomPhoneStateListener;
    private IMobileDataManager mIMobileDataManager;

    public MobileDataManager(Context context,IMobileDataManager iMobileDataManager){
        this.mContext = context;
        this.mIMobileDataManager = iMobileDataManager;
        mCustomPhoneStateListener = new CustomPhoneStateListener(mContext,this);
        telephonyService = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyService.listen(mCustomPhoneStateListener, PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);
    }

    public void setMobileDataState(Context context, boolean enabled) {
        try {
            Method setDataEnabled = telephonyService.getClass().getDeclaredMethod("setDataEnabled",boolean.class);
            if (null != setDataEnabled) {
                setDataEnabled.invoke(telephonyService, enabled);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getMobileDataState(Context context) {
        try {
            Method getDataEnabled = telephonyService.getClass().getDeclaredMethod("getDataEnabled");
            if (null != getDataEnabled) {
                return (Boolean) getDataEnabled.invoke(telephonyService);
            }
            LogUtils.i("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void getMobileDataState(boolean isSwitch) {
        mIMobileDataManager.getMobileDataSwitch(isSwitch);
    }
}
