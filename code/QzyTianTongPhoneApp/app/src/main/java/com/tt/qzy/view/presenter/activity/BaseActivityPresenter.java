package com.tt.qzy.view.presenter.activity;

import android.content.Context;

import com.qzy.data.PhoneCmd;
import com.qzy.tt.data.TtBeiDouStatuss;
import com.qzy.tt.data.TtPhoneBatteryProtos;
import com.qzy.tt.data.TtPhoneSignalProtos;
import com.qzy.tt.data.TtPhoneSimCards;
import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.view.BaseMainView;

public class BaseActivityPresenter extends BasePresenter<BaseMainView>{

    private Context mContext;

    public BaseActivityPresenter(Context context){
        this.mContext = context;
    }

    /**
     * 获取天通信号强度
     * @param obj
     * @return
     */
    public int getTianTongSignalValue(Object obj) {
        PhoneCmd cmd = (PhoneCmd) obj;
        TtPhoneSignalProtos.PhoneSignalStrength signalStrength = (TtPhoneSignalProtos.PhoneSignalStrength) cmd.getMessage();
        int value = signalStrength.getSignalStrength();
        return value;
    }

    /**
     * 获取电量level
     * @param obj
     * @return
     */
    public int getBatteryLevel(Object obj){
        PhoneCmd cmd = (PhoneCmd) obj;
        TtPhoneBatteryProtos.TtPhoneBattery ttPhoneBattery = (TtPhoneBatteryProtos.TtPhoneBattery) cmd.getMessage();
        int level = ttPhoneBattery.getLevel();
        return level;
    }

    /**
     * 获取 sdcal
     * @param obj
     * @return
     */
    public int getBatteryScal(Object obj){
        PhoneCmd cmd = (PhoneCmd) obj;
        TtPhoneBatteryProtos.TtPhoneBattery ttPhoneBattery = (TtPhoneBatteryProtos.TtPhoneBattery) cmd.getMessage();
        int scal = ttPhoneBattery.getScale();
        return scal;
    }

    /**
     * 获取设备sim卡状态
     */
    public boolean getTianTongSimStatus(Object obj){
        PhoneCmd cmd = (PhoneCmd)obj;
        TtPhoneSimCards.TtPhoneSimCard ttPhoneSimCard = (TtPhoneSimCards.TtPhoneSimCard)cmd.getMessage();
        boolean status = ttPhoneSimCard.getIsSimCard();
        return status;
    }

    /**
     * 设备是否连接上北斗卫星
     */
    public boolean getTianTongConnectBeiDou(Object obj){
        PhoneCmd cmd = (PhoneCmd)obj;
        TtBeiDouStatuss.TtBeiDouStatus ttBeiDouStatus = (TtBeiDouStatuss.TtBeiDouStatus)cmd.getMessage();
        boolean isConnect = ttBeiDouStatus.getIsBeiDouStatus();
        return isConnect;
    }
}
