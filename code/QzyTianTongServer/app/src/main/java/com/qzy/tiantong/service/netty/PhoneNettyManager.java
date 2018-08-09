package com.qzy.tiantong.service.netty;

import com.qzy.data.PhoneCmd;
import com.qzy.data.PrototocalTools;
import com.qzy.netty.NettyServerManager;
import com.qzy.tiantong.service.phone.TtPhoneState;
import com.qzy.tt.data.CallPhoneStateProtos;
import com.qzy.tt.data.TtPhoneSignalProtos;
import com.qzy.utils.LogUtils;

/**
 * Created by yj.zhang on 2018/8/9.
 */

public class PhoneNettyManager {

    //服务端netty管理工具
    private NettyServerManager mNettyServerManager;


    //客户端手机管理
    private PhoneClientManager mPhoneClientManager;

    public PhoneNettyManager(NettyServerManager manager){
        mNettyServerManager = manager;
    }

    /**
     * 更新电话状态
     * @param state
     */
    public void updateTtCallPhoneState(TtPhoneState state){
        LogUtils.e("updateTtCallPhoneState " + state.ordinal());
        CallPhoneStateProtos.CallPhoneState.PhoneState phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.NOCALL;
        if (state == TtPhoneState.NOCALL) {
            phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.NOCALL;
        } else if (state == TtPhoneState.RING) {
            phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.RING;
        } else if (state == TtPhoneState.CALL) {
            phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.CALL;
        } else if (state == TtPhoneState.HUANGUP) {
            phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.HUANGUP;
        }
        sendTtCallPhoneStateToClient(phoneState);
    }


    /**
     * 发送电话状态到客户端
     * @param phoneState
     */
    private void sendTtCallPhoneStateToClient(CallPhoneStateProtos.CallPhoneState.PhoneState phoneState){
        if (mNettyServerManager != null) {
            CallPhoneStateProtos.CallPhoneState callPhoneState = CallPhoneStateProtos.CallPhoneState.newBuilder()
                    .setPhoneState(phoneState)
                    .build();
            mNettyServerManager.sendData(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.call_phone_state,callPhoneState));
        }
    }

    /**
     * 发送信号强度
     * @param value
     */
    public void sendTtCallPhoneSignalToClient(int value){
        if (mNettyServerManager != null) {
            TtPhoneSignalProtos.PhoneSignalStrength signalStrength = TtPhoneSignalProtos.PhoneSignalStrength.newBuilder()
                    .setSignalStrength(value)
                    .build();

            mNettyServerManager.sendData(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.tt_phone_signal,signalStrength));
        }
    }


}
