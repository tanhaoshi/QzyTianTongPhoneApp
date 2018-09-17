package com.tt.qzy.view.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.qzy.eventbus.EventBusUtils;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.tt.phone.common.CommonData;
import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.TellPhoneActivity;
import com.tt.qzy.view.utils.NToast;

/**
 * Created by yj.zhang on 2018/9/17.
 */

public class AidlPhoneFragmentPersenter {
    private Context mContext;


    public AidlPhoneFragmentPersenter(Context context) {
        mContext = context;
    }


    /**
     * 拨打电话
     *
     * @param phoneNumber
     */
    public void dialPhone(String phoneNumber) {
        if (!CommonData.getInstance().isConnected()) {
            NToast.shortToast(mContext, R.string.TMT_connect_tiantong_please);
            return;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            NToast.shortToast(mContext, R.string.TMT_dial_number_notmull);
            return;
        }

        EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_DIAL,phoneNumber));

        Intent intent = new Intent(mContext, TellPhoneActivity.class);
        intent.putExtra("diapadNumber", phoneNumber);
        mContext.startActivity(intent);
    }

}
