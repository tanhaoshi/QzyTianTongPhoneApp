package com.tt.qzy.view.presenter.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import com.qzy.data.PhoneCmd;
import com.qzy.eventbus.EventBusUtils;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.tt.data.TtOpenBeiDouProtos;
import com.qzy.tt.data.TtPhoneMobileDataProtos;
import com.qzy.tt.data.TtPhonePositionProtos;
import com.qzy.tt.data.TtPhoneUpdateResponseProtos;
import com.qzy.tt.phone.common.CommonData;
import com.qzy.utils.IPUtil;
import com.socks.library.KLog;
import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.TellPhoneActivity;
import com.tt.qzy.view.activity.UserEditorsActivity;
import com.tt.qzy.view.bean.AppInfoModel;
import com.tt.qzy.view.bean.DatetimeModel;
import com.tt.qzy.view.bean.EnableDataModel;
import com.tt.qzy.view.bean.ServerPortIp;
import com.tt.qzy.view.bean.TtBeidouOpenBean;
import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.utils.AppUtils;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.DateUtil;
import com.tt.qzy.view.utils.MD5Utils;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.utils.NetworkUtil;
import com.tt.qzy.view.view.MainFragmentView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.Date;


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

        EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG,new ServerPortIp(Constans.IP,Constans.UPLOAD_PORT)));

    }

    /**
     * 关闭连接
     */
    public void stopConnect(){
        EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_DISCONNECT_TIANTONG));
    }

    /**
     * 请求gps准确位置
     */
    public void requestGpsPosition(boolean isSwitch){
        EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_ACCURACY_POSITION,
                new TtBeidouOpenBean(isSwitch)));
    }

    /**
     * 检查天通猫服务端APP版本是否更新
     */
    public void requestServerVersion(){
        AssetManager assetManager = mContext.getAssets();
        try {
            EventBus.getDefault().post(new MessageEventBus((IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG__REQUEST_SERVER_APP_VERSION),
                    new AppInfoModel(IPUtil.getLocalIPAddress(mContext), String.valueOf(AppUtils.getVersionCode(mContext)),Constans.SERVER_APP_VERSION,
                            MD5Utils.getFileMD5(assetManager.open("tiantong_update.zip")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送当前时间至服务器
     */
    public void requestServerDatetime(){
        EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG__REQUEST_SERVER_TIME_DATE,
                new DatetimeModel(DateUtil.backTimeFomat(new Date()))));
    }

    /**
     * 打开天通猫移动数据
     */
    public void requestEnableData(boolean isSwitch){
        EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_ENABLE_DATA,
                new EnableDataModel(isSwitch)));
    }

    /**
     * 获取服务初始化数据状态
     */
    public void requestServerMobileStatus(){
        EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_MOBILE_STATUS));
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
                saveLocalWIFIIP();
                requestServerVersion();
                mView.get().updateConnectedState(true);
                requestServerDatetime();
//                requestServerMobileStatus();
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_FAILED:
                mView.get().updateConnectedState(false);
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG__RESPONSE_SERVER_APP_VERSION:
                parseServerAppVersion(event.getObject());
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG__RESPONSE_SERVER_UPLOAD_FINSH:
                parseAppUploadFinsh(event.getObject());
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_SERVER_ENABLE_DATA:
                parseServerDataEnable(event.getObject());
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_SERVER_PERCENT:
                parseServerPercent(event.getObject());
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_SERVER_NONCONNECT:
                upgradleBreakoff();
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_SERVER_UPGRADLE:
                parseServerUpgradleFailed(event.getObject());
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_SERVER_MOBILE_STATUS:
                parseServerMobileDataInit(event.getObject());
                break;
        }
    }

    /**
     * 保存链接wifi后的ip地址
     */
    private void saveLocalWIFIIP(){
        CommonData.getInstance().setLocalWifiIp(IPUtil.getLocalIPAddress(mContext));
    }

    /**
     * 解析初始化服务底层数据状态
     */
    private void parseServerMobileDataInit(Object o){
        PhoneCmd cmd = (PhoneCmd)o;
        TtPhoneMobileDataProtos.TtPhoneMobileData ttPhoneMobileData = (TtPhoneMobileDataProtos.TtPhoneMobileData)cmd.getMessage();
        mView.get().getSetverInitMobileStatus(ttPhoneMobileData.getResponseStatus());
    }

    /**
     * 服务器验证MD5文件失败
     */
    private void parseServerUpgradleFailed(Object o){
        PhoneCmd cmd = (PhoneCmd)o;
        TtPhoneUpdateResponseProtos.UpdateResponse updateResponse = (TtPhoneUpdateResponseProtos.UpdateResponse)cmd.getMessage();
        if(updateResponse.getIsSendFileFinish()){
            EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG__REQUEST_SERVER_UPLOAD_APP
                    ,new ServerPortIp(Constans.IP,Constans.UPLOAD_PORT)));
        }
    }

    /**
     * 服务端升级出现链接中断
     */
    private void upgradleBreakoff(){
        mView.get().upgradleNonconnect();
    }

    /**
     * 解析服务端升级的进度条
     */
    private void parseServerPercent(Object o){
        Integer i = (Integer) o;
        mView.get().serverAppUpgradlePercent(i);
    }

    /**
     * 天通猫打开数据流量失败或成功
     */
    private void parseServerDataEnable(Object o){
        PhoneCmd cmd = (PhoneCmd)o;
        TtPhoneMobileDataProtos.TtPhoneMobileData ttPhoneMobileData = (TtPhoneMobileDataProtos.TtPhoneMobileData)cmd.getMessage();
        if(ttPhoneMobileData.getResponseStatus()){
            NToast.shortToast(mContext,"打开天通猫数据成功!");
        }else{
            NToast.shortToast(mContext,"打开天通猫数据失败!");
        }
    }

    /**
     * 关闭天通猫链接
     */
    private void parseAppUploadFinsh(Object o){
        PhoneCmd cmd = (PhoneCmd)o;
        TtPhoneUpdateResponseProtos.UpdateResponse updateResponse = (TtPhoneUpdateResponseProtos.UpdateResponse)cmd.getMessage();
        if(updateResponse.getIsUpdateFinish()){
            mView.get().isServerUpdate(true);
            EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_DISCONNECT_TIANTONG));
            EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG,new ServerPortIp(Constans.IP,Constans.PORT)));
        }
    }

    /**
     * 返回天通猫服务端APP是否需要更新
     */
    private void parseServerAppVersion(Object o){
        PhoneCmd cmd = (PhoneCmd)o;
        TtPhoneUpdateResponseProtos.UpdateResponse updateResponse = (TtPhoneUpdateResponseProtos.UpdateResponse)cmd.getMessage();
        if(updateResponse.getIsUpdate()){
            mView.get().upgradleServerApp();
        }else{
            EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG,new ServerPortIp(Constans.IP,Constans.PORT)));
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
