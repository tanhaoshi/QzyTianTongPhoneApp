package com.tt.qzy.view.presenter.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import com.qzy.data.PhoneCmd;

import com.qzy.tt.data.TtOpenBeiDouProtos;
import com.qzy.tt.data.TtPhoneMobileDataProtos;
import com.qzy.tt.data.TtPhonePositionProtos;
import com.qzy.tt.data.TtPhoneSosStateProtos;
import com.qzy.tt.data.TtPhoneUpdateResponseProtos;
import com.qzy.tt.phone.common.CommonData;
import com.qzy.tt.phone.data.impl.IMainFragment;
import com.qzy.tt.phone.data.TtPhoneDataManager;
import com.qzy.utils.IPUtil;
import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.TellPhoneActivity;
import com.tt.qzy.view.activity.UserEditorsActivity;
import com.tt.qzy.view.bean.TtBeidouOpenBean;
import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.utils.AppUtils;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.utils.NetworkUtil;
import com.tt.qzy.view.view.MainFragmentView;

/**
 * Created by yj.zhang on 2018/9/17.
 */

public class MainFragmentPresenter extends BasePresenter<MainFragmentView> implements IMainFragment {

    private Context mContext;

    public MainFragmentPresenter(Context context) {
        mContext = context;
    }

    public void setMainFragmentListener() {
        if (TtPhoneDataManager.getInstance() != null) {
            TtPhoneDataManager.getInstance().setMainFragmentListener(this);
        }
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

        if (!NetworkUtil.isWifiEnabled(mContext)) {
            WifiManager wfManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            wfManager.setWifiEnabled(true);
            Intent intent = new Intent("android.settings.WIFI_SETTINGS");
            mContext.startActivity(intent);
            return;
        }

        String ssid = NetworkUtil.getConnectWifiSsid(mContext);

        if (!AppUtils.getIpAddress(mContext).contains(Constans.LOCAL_HEAD_IP)) {
            NToast.shortToast(mContext, mContext.getString(R.string.TMT_PLEASE_CLOSE_DATA_MOBILE_AND_ENABLE_WIFI));
        }

        if (TextUtils.isEmpty(ssid) || ssid.length() < 6 || !ssid.contains(Constans.STANDARD_WIFI_NAME)) {
            NToast.shortToast(mContext, mContext.getString(R.string.TMT_connect_tiantong_please));
            Intent intent = new Intent("android.settings.WIFI_SETTINGS");
            mContext.startActivity(intent);
            return;
        }

        startConnectMain();
    }

    private void startConnectMain() {
        setMainFragmentListener();
        if (TtPhoneDataManager.getInstance() != null) {
            TtPhoneDataManager.getInstance().connectTtPhoneServer(Constans.IP, Constans.PORT);
        }
    }

    /**
     * 关闭连接
     */
    public void stopConnect() {
        if (TtPhoneDataManager.getInstance() != null) {
            TtPhoneDataManager.getInstance().disconnectTtPhoneServer();
        }
    }

    /**
     * 请求gps准确位置
     */
    public void requestGpsPosition(boolean isSwitch) {
        /*EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_ACCURACY_POSITION,
                new TtBeidouOpenBean(isSwitch)));*/

        if (TtPhoneDataManager.getInstance() != null) {
            TtBeidouOpenBean ttBeidouOpenBean = new TtBeidouOpenBean(isSwitch);
            if (isSwitch) {
                TtPhoneDataManager.getInstance().openTtPhoneGps(ttBeidouOpenBean);
            } else {
                TtPhoneDataManager.getInstance().closeTtPhoneGps(ttBeidouOpenBean);
            }
        }

    }

    /**
     * 检查设备服务端APP版本是否更新
     */
    public void requestServerVersion() {
        AssetManager assetManager = mContext.getAssets();
        try {
           /* EventBus.getDefault().post(new MessageEventBus((IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG__REQUEST_SERVER_APP_VERSION),
                    new AppInfoModel(IPUtil.getLocalIPAddress(mContext), String.valueOf(AppUtils.getVersionCode(mContext)),Constans.SERVER_APP_VERSION,
                            MD5Utils.getFileMD5(assetManager.open("tiantong_update.zip")))));*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送当前时间至服务器
     */
    public void requestServerDatetime() {
      /*  EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG__REQUEST_SERVER_TIME_DATE,
                new DatetimeModel(DateUtil.backTimeFomat(new Date()))));*/
    }

    /**
     * 打开设备移动数据
     */
    public void requestEnableData(boolean isSwitch) {
        // EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_ENABLE_DATA, new EnableDataModel(isSwitch)));
    }

    /**
     * 获取服务初始化数据状态
     */
    public void requestServerMobileStatus() {
        // EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_MOBILE_STATUS));
    }

    /**
     * 查询设备sos初始状态
     */
    public void requestServerSosStatus() {
        //EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_SOS_STATUS));
    }

    /**
     * 关闭设备SOS
     */
    public void closeServerSos() {
        //EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_SOS_CLOSE));
    }

    /*@Subscribe(threadMode = ThreadMode.MAIN)
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
                *//** 屏蔽了数据初始化状态 *//*
                //requestServerMobileStatus();
                requestServerSosStatus();
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
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_SERVER_SOS_STATUS:
                getServerSosStatus(event.getObject());
                break;
        }
    }
*/

    /**
     * 解析设备初始化状态
     */
    private void getServerSosStatus(Object o) {
        PhoneCmd cmd = (PhoneCmd) o;
        TtPhoneSosStateProtos.TtPhoneSosState ttPhoneSosState = (TtPhoneSosStateProtos.TtPhoneSosState) cmd.getMessage();
        if (ttPhoneSosState.getIsResponse()) {
            mView.get().getServerSosStatus(ttPhoneSosState.getIsSwitch());
        }
    }

    /**
     * 保存连接wifi后的ip地址
     */
    private void saveLocalWIFIIP() {
        CommonData.getInstance().setLocalWifiIp(IPUtil.getLocalIPAddress(mContext));
    }

    /**
     * 解析初始化服务底层数据状态
     */
    private void parseServerMobileDataInit(Object o) {
        PhoneCmd cmd = (PhoneCmd) o;
        TtPhoneMobileDataProtos.TtPhoneMobileData ttPhoneMobileData = (TtPhoneMobileDataProtos.TtPhoneMobileData) cmd.getMessage();
        mView.get().getSetverInitMobileStatus(ttPhoneMobileData.getResponseStatus());
    }

    /**
     * 服务器验证MD5文件失败
     */
    private void parseServerUpgradleFailed(Object o) {
        PhoneCmd cmd = (PhoneCmd) o;
        TtPhoneUpdateResponseProtos.UpdateResponse updateResponse = (TtPhoneUpdateResponseProtos.UpdateResponse) cmd.getMessage();
        if (!updateResponse.getIsSendFileFinish()) {
//              NToast.shortToast(mContext,mContext.getResources().getString(R.string.TMT_WIFI_DISCONNECT_OF_UPDATE_PLEASE_OUT));
//            EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG__REQUEST_SERVER_UPLOAD_APP
//                    ,new ServerPortIp(Constans.IP,Constans.UPLOAD_PORT)));
        }
    }

    /**
     * 服务端升级出现链接中断
     */
    private void upgradleBreakoff() {
        mView.get().upgradleNonconnect();
    }

    /**
     * 解析服务端升级的进度条
     */
    private void parseServerPercent(Object o) {
        Integer i = (Integer) o;
        mView.get().serverAppUpgradlePercent(i);
    }

    /**
     * 设备打开数据流量失败或成功
     */
    private void parseServerDataEnable(Object o) {
        PhoneCmd cmd = (PhoneCmd) o;
        TtPhoneMobileDataProtos.TtPhoneMobileData ttPhoneMobileData = (TtPhoneMobileDataProtos.TtPhoneMobileData) cmd.getMessage();
        if (ttPhoneMobileData.getResponseStatus()) {
            mView.get().getMobileDataShow(true);
        } else {
            mView.get().getMobileDataShow(false);
        }
    }

    /**
     * 关闭设备链接
     */
    private void parseAppUploadFinsh(Object o) {
        PhoneCmd cmd = (PhoneCmd) o;
        TtPhoneUpdateResponseProtos.UpdateResponse updateResponse = (TtPhoneUpdateResponseProtos.UpdateResponse) cmd.getMessage();
        if (updateResponse.getIsUpdateFinish()) {
            mView.get().isServerUpdate(true);
            // EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_DISCONNECT_TIANTONG));
            // EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG,new ServerPortIp(Constans.IP,Constans.PORT)));
        }
    }

    /**
     * 返回设备服务端APP是否需要更新
     */
    private void parseServerAppVersion(Object o) {
        PhoneCmd cmd = (PhoneCmd) o;
        TtPhoneUpdateResponseProtos.UpdateResponse updateResponse = (TtPhoneUpdateResponseProtos.UpdateResponse) cmd.getMessage();
        if (updateResponse.getIsUpdate()) {
            mView.get().upgradleServerApp();
        } else {
            //  EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG,new ServerPortIp(Constans.IP,Constans.PORT)));
        }
    }

    /**
     * 返回gps准确位置进行解析
     *
     * @param object
     */
    private void parseGpsPostion(Object object) {
        PhoneCmd cmd = (PhoneCmd) object;
        TtPhonePositionProtos.TtPhonePosition ttPhonePosition = (TtPhonePositionProtos.TtPhonePosition) cmd.getMessage();
        mView.get().getTtPhonePosition(ttPhonePosition);
    }

    /**
     * 解析设备北斗开关是否打开
     *
     * @param object
     */
    private void parseBeiDouSwitch(Object object) {
        PhoneCmd cmd = (PhoneCmd) object;
        TtOpenBeiDouProtos.TtOpenBeiDou ttOpenBeiDou = (TtOpenBeiDouProtos.TtOpenBeiDou) cmd.getMessage();
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

        // EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_DIAL,phoneNumber));
        dialPhoneToServer(phoneNumber);

        Intent intent = new Intent(mContext, TellPhoneActivity.class);
        intent.putExtra("diapadNumber", phoneNumber);
        mContext.startActivity(intent);
    }

    /**
     * 底层打电话接口
     *
     * @param phoneMumber
     */
    private void dialPhoneToServer(String phoneMumber) {
        TtPhoneDataManager.getInstance().dialTtPhone(phoneMumber);
    }

    public void release() {
        // EventBus.getDefault().unregister(this);
    }

    @Override
    public void isTtServerConnected(boolean connected) {
        saveLocalWIFIIP();
        //请求sos状态
        TtPhoneDataManager.getInstance().getTtPhoneSosState();
        mView.get().connectedState(connected);

    }

    @Override
    public void disConnectServer() {
        mView.get().connectedState(false);
    }

    @Override
    public void isTtPhoneGpsPosition(PhoneCmd phoneCmd) {
        parseGpsPostion(phoneCmd);
    }


}
