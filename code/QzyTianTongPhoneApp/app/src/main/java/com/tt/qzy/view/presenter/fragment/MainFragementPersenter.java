package com.tt.qzy.view.presenter.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import com.qzy.data.PhoneCmd;
import com.qzy.eventbus.EventBusUtils;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.tt.data.TtCallRecordProtos;
import com.qzy.tt.data.TtOpenBeiDouProtos;
import com.qzy.tt.data.TtPhoneBatteryProtos;
import com.qzy.tt.data.TtPhonePositionProtos;
import com.qzy.tt.data.TtPhoneSignalProtos;
import com.qzy.tt.data.TtPhoneSmsProtos;
import com.qzy.tt.phone.common.CommonData;
import com.socks.library.KLog;
import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.TellPhoneActivity;
import com.tt.qzy.view.activity.UserEditorsActivity;
import com.tt.qzy.view.bean.TtBeidouOpenBean;
import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.utils.NetworkUtil;
import com.tt.qzy.view.view.MainFragmentView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by yj.zhang on 2018/9/17.
 */

public class MainFragementPersenter extends BasePresenter<MainFragmentView>{

    private Context mContext;


    public MainFragementPersenter(Context context) {
        mContext = context;
        EventBus.getDefault().register(this);
    }

    /**
     * 查询连接状态
     */
    public void checkConnectedSate() {
        KLog.i("checkConnectedSate");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                    EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_SELECTED));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 连接天通
     */
    public void startConnect() {

        if (CommonData.getInstance().isConnected()) {
            Intent intent = new Intent(mContext, UserEditorsActivity.class);
            ((Activity) mContext).startActivityForResult(intent, 99);
            return;
        }
        //先判断wifi开关是否打开 跳转至打开wifi界面
        if (!NetworkUtil.isWifiEnabled(mContext)) {
            WifiManager wfManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            wfManager.setWifiEnabled(true);
            Intent intent = new Intent("android.settings.WIFI_SETTINGS");
            mContext.startActivity(intent);
            return;
        }

        //判断是否连接着天通指定的wifi
        String ssid = NetworkUtil.getConnectWifiSsid(mContext);
        if(TextUtils.isEmpty(ssid) || ssid.length() < 6 || !ssid.contains(Constans.STANDARD_WIFI_NAME)){
            NToast.shortToast(mContext, mContext.getString(R.string.TMT_connect_tiantong_please));
            Intent intent = new Intent("android.settings.WIFI_SETTINGS");
            mContext.startActivity(intent);
            return;
        }

        EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG));
    }

    /**
     * 请求gps准确位置
     */
    public void requestGpsPosition(boolean isSwitch){
        EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_ACCURACY_POSITION,
                new TtBeidouOpenBean(isSwitch)));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEventBus event) {
        switch (event.getType()) {
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_ACCURACY_POSITION:
                parseGpsPostion(event.getObject());
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_BEIDOU_SWITCH:
                parseBeiDouSwitch(event.getObject());
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_SUCCESS:
                mView.get().updateConnectedState(true);
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_FAILED:
                mView.get().updateConnectedState(false);
                break;
        }
    }

    /**
     * 返回gps准确位置进行解析
     * @param object
     */
    private void parseGpsPostion(Object object) {
        PhoneCmd cmd = (PhoneCmd) object;
        TtPhonePositionProtos.TtPhonePosition ttPhonePosition = (TtPhonePositionProtos.TtPhonePosition)cmd.getMessage();
        mView.get().getTtPhonePosition(ttPhonePosition);
    }

    /**
     * 解析天通猫北斗开关是否打开
     * @param object
     */
    private void parseBeiDouSwitch(Object object){
        PhoneCmd cmd = (PhoneCmd)object;
        TtOpenBeiDouProtos.TtOpenBeiDou ttOpenBeiDou = (TtOpenBeiDouProtos.TtOpenBeiDou)cmd.getMessage();
        mView.get().getTtBeiDouSwitch(ttOpenBeiDou);
    }

    /**
     * 拨打电话
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

    public void release(){
        EventBus.getDefault().unregister(this);
    }
}
