package com.qzy.tiantong.service.utils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import com.qzy.tiantong.lib.utils.LogUtils;

import java.lang.reflect.Method;

public class MobileDataUtils {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public static void setDataEnabled(int slotIdx, boolean enable, Context context) throws Exception
    {
        try {
            int subid = SubscriptionManager.from(context).getActiveSubscriptionInfoForSimSlotIndex(slotIdx).getSubscriptionId();
            TelephonyManager telephonyService = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Method setDataEnabled = telephonyService.getClass().getDeclaredMethod("setDataEnabled", int.class, boolean.class);
            if (null != setDataEnabled) {
                setDataEnabled.invoke(telephonyService, subid, enable);
                LogUtils.e("setDataEnabled sucsss");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            LogUtils.e("setDataEnabled exception");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public static boolean getDataEnabled(int slotIdx, Context context) throws Exception {
        boolean enabled = false;
        try {
            int subid = SubscriptionManager.from(context).getActiveSubscriptionInfoForSimSlotIndex(slotIdx).getSubscriptionId();
            TelephonyManager telephonyService = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Method getDataEnabled = telephonyService.getClass().getDeclaredMethod("getDataEnabled", int.class);
            if (null != getDataEnabled) {
                enabled = (Boolean) getDataEnabled.invoke(telephonyService, subid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return enabled;
    }
}
