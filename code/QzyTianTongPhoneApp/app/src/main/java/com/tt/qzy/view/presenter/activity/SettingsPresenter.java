package com.tt.qzy.view.presenter.activity;

import android.content.Context;

import com.qzy.data.PhoneCmd;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.tt.data.TtBeiDouStatuss;
import com.tt.qzy.view.bean.TtBeidouOpenBean;
import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.view.SettingsView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SettingsPresenter extends BasePresenter<SettingsView>{

    private Context mContext;

    public SettingsPresenter(Context context){
        this.mContext = context;
        EventBus.getDefault().register(this);
    }

    /**
     * 打开usb开关
     */
    public void openTianTongBeidou(boolean isSwitch){
        EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_BEIDOU_SWITCH,new
                TtBeidouOpenBean(isSwitch)));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEventBus event) {
        switch (event.getType()) {
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_BEIDOU:
                getTianTongConnectBeiDou(event.getObject());
                break;
        }
    }

    /**
     * 天通猫是否连接上北斗卫星
     */
    public boolean getTianTongConnectBeiDou(Object obj){
        PhoneCmd cmd = (PhoneCmd)obj;
        TtBeiDouStatuss.TtBeiDouStatus ttBeiDouStatus = (TtBeiDouStatuss.TtBeiDouStatus)cmd.getMessage();
        boolean isConnect = ttBeiDouStatus.getIsBeiDouStatus();
        return isConnect;
    }

    public void release(){
        EventBus.getDefault().unregister(this);
    }
}
