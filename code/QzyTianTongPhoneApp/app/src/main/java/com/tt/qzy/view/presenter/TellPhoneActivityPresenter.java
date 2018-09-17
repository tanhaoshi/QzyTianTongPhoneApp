package com.tt.qzy.view.presenter;

import android.content.Context;

import com.qzy.data.PhoneCmd;
import com.qzy.tt.data.CallPhoneStateProtos;
import com.qzy.tt.phone.netty.TtPhoneState;
import com.qzy.utils.LogUtils;

/**
 * Created by yj.zhang on 2018/9/17.
 */

public class TellPhoneActivityPresenter {
    private Context mContext;


    public TellPhoneActivityPresenter(Context context) {
        mContext = context;
    }

    /**
     * 获取转换后的状态
     * @param cmd
     * @return
     */
    public TtPhoneState getTtPhoneState(PhoneCmd cmd){
        TtPhoneState ttPhoneState = TtPhoneState.UNRECOGNIZED;
        CallPhoneStateProtos.CallPhoneState callPhoneState = (CallPhoneStateProtos.CallPhoneState) cmd.getMessage();
        LogUtils.e("call Phone State = " + callPhoneState.getPhoneStateValue());
        if (callPhoneState.getPhoneState() == CallPhoneStateProtos.CallPhoneState.PhoneState.NOCALL) {
            ttPhoneState = TtPhoneState.NOCALL;
        } else if (callPhoneState.getPhoneState() == CallPhoneStateProtos.CallPhoneState.PhoneState.CALL) {
            ttPhoneState = TtPhoneState.CALL;
        } else if (callPhoneState.getPhoneState() == CallPhoneStateProtos.CallPhoneState.PhoneState.RING) {
            ttPhoneState = TtPhoneState.RING;
        } else if (callPhoneState.getPhoneState() == CallPhoneStateProtos.CallPhoneState.PhoneState.HUANGUP) {
            ttPhoneState = TtPhoneState.HUANGUP;
        }
        return ttPhoneState;
    }


}
