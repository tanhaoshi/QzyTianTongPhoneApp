package com.tt.qzy.view.presenter.fragment;

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
import com.tt.qzy.view.bean.AppInfoModel;
import com.tt.qzy.view.bean.DatetimeModel;
import com.tt.qzy.view.bean.TtBeidouOpenBean;
import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.utils.AppUtils;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.DateUtil;
import com.tt.qzy.view.utils.MD5Utils;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.utils.NetworkUtil;
import com.tt.qzy.view.utils.SPUtils;
import com.tt.qzy.view.view.MainFragmentView;

import java.io.IOException;
import java.util.Date;

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
//        if (CommonData.getInstance().isConnected()) {
//            Intent intent = new Intent(mContext, UserEditorsActivity.class);
//            ((Activity) mContext).startActivityForResult(intent, 99);
//            return;
//        }

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

        //!ssid.contains(Constans.STANDARD_WIFI_NAME)
        if (TextUtils.isEmpty(ssid) || ssid.length() < 6 || containsWifiSSID(ssid)) {
            NToast.shortToast(mContext, mContext.getString(R.string.TMT_connect_tiantong_please));
            Intent intent = new Intent("android.settings.WIFI_SETTINGS");
            mContext.startActivity(intent);
            return;
        }

        SPUtils.removeShare(mContext, Constans.CRY_HELP_TIMETIMER);
        SPUtils.removeShare(mContext, Constans.CRY_HELP_PHONE);
        SPUtils.removeShare(mContext, Constans.CRY_HELP_SHORTMESSAGE);

        startConnectMain();
    }

    private boolean containsWifiSSID(String ssid){
        if(ssid.contains(Constans.STANDARD_WIFI_NAME) || ssid.contains(Constans.DAXIE_STANDARD_WIFI_NAME)
                || ssid.contains(Constans.TIAN_TONG_WIFI_NAME)){
            return true;
        }else{
            return false;
        }
    }

    private void startConnectMain() {
        setMainFragmentListener();
        if (TtPhoneDataManager.getInstance() != null) {
            TtPhoneDataManager.getInstance().connectTtPhoneServer(Constans.IP, Constans.UPLOAD_PORT);
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
     * 关闭设备SOS
     */
    public void switchServerSos(boolean isOpen) {
        TtPhoneDataManager.getInstance().requestTtPhoneSos(isOpen);
    }

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
        TtPhoneUpdateResponseProtos.UpdateResponse updateResponse = (TtPhoneUpdateResponseProtos.UpdateResponse) o;
        if (!updateResponse.getIsSendFileFinish()) {
            NToast.shortToast(mContext,mContext.getString(R.string.TMT_CHECK_FILE_FAILED));
        }
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
            TtPhoneDataManager.getInstance().disconnectTtPhoneServer();
        }
    }

    /**
     * 返回设备服务端APP是否需要更新
     */
    private void parseServerAppVersion(Object o) {
        TtPhoneUpdateResponseProtos.UpdateResponse updateResponse = (TtPhoneUpdateResponseProtos.UpdateResponse) o;
        if (updateResponse.getIsUpdate()) {
            mView.get().upgradleServerApp();
        } else {
            TtPhoneDataManager.getInstance().connectTtPhoneServer(Constans.IP, Constans.PORT);
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

    @Override
    public void isTtServerConnected(boolean connected) {

        mView.get().connectedState(connected);

        if(connected) {

            saveLocalWIFIIP();

            requireServerUpdate();

            //请求sos状态
            TtPhoneDataManager.getInstance().getTtPhoneSosState();

            //发送手机时间到服务器端
            if (TtPhoneDataManager.getInstance() != null) {
                DatetimeModel datetimeModel = new DatetimeModel(DateUtil.backTimeFomat(new Date()));
                TtPhoneDataManager.getInstance().setDateAndTime(datetimeModel);
            }
        }else{
            mView.get().upgradleNonconnect();
        }
    }

    @Override
    public void isUpdateServer(Object o) {
        parseServerAppVersion(o);
    }

    @Override
    public void updatePercent(Integer percent) {
        parseServerPercent(percent);
    }

    /**
     * 解析服务端升级的进度条
     */
    private void parseServerPercent(Object o){
        Integer i = (Integer) o;
        mView.get().serverAppUpgradlePercent(i);
    }


    public void startUpdate(){
        TtPhoneDataManager.getInstance().startSendPackage();
    }

    @Override
    public void serverUpdateError(Object o) {
        parseServerUpgradleFailed(o);
    }

    @Override
    public void updateServerSucceed(Object o) {
        parseAppUploadFinsh(o);
    }

    private void requireServerUpdate(){
        AssetManager assetManager = mContext.getAssets();
        try {
            TtPhoneDataManager.getInstance().checkServerIsUpdate(new AppInfoModel(IPUtil.getLocalIPAddress(mContext), String.valueOf(AppUtils.getVersionCode(mContext)),Constans.SERVER_APP_VERSION,
                    MD5Utils.getFileMD5(assetManager.open("tiantong_update.zip"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disConnectServer() {
        mView.get().connectedState(false);
    }

    @Override
    public void isTtPhoneGpsPosition(PhoneCmd phoneCmd) {
        parseGpsPostion(phoneCmd);
    }

    @Override
    public void isServerSosStatus(Object o) {
        getServerSosStatus(o);
    }


}
