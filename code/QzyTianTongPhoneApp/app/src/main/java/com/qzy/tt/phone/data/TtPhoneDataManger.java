package com.qzy.tt.phone.data;

import com.qzy.tt.phone.netty.PhoneNettyManager;
import com.tt.qzy.view.bean.DatetimeModel;
import com.tt.qzy.view.bean.SosSendMessageModel;
import com.tt.qzy.view.bean.WifiSettingModel;

/**
 * 数据管理类
 */
public class TtPhoneDataManger implements ITtPhoneHandlerManager {

    private static TtPhoneDataManger instance;

    private PhoneNettyManager phoneNettyManager;

    public static TtPhoneDataManger getInstance() {
        return instance;
    }

    /**
     * 初始化
     */
    public static void init(PhoneNettyManager manager) {
        instance = new TtPhoneDataManger(manager);
    }

    private TtPhoneDataManger(PhoneNettyManager manager) {
        phoneNettyManager = manager;
    }


    /**
     * 注册数据回调
     *
     * @param iTtPhoneDataListener
     */
    public void setTtPhoneDataListener(ITtPhoneDataListener iTtPhoneDataListener) {
        phoneNettyManager.getmCmdHandler().setmDataListener(iTtPhoneDataListener);
    }


    public void free() {
        instance = null;
    }


    @Override
    public void connectTtPhoneServer(String ip, int port) {
        phoneNettyManager.connect(port, ip);
    }

    @Override
    public void openTtPhoneSos() {

    }

    @Override
    public void closeTtPhoneSos() {

    }

    @Override
    public void openTtPhoneGps() {

    }

    @Override
    public void closeTtPhoneGps() {

    }

    @Override
    public void setTtPhoneSosValue(String phoneNumber, String text, SosSendMessageModel sosSendMessageModel) {

    }

    @Override
    public void setWifiPasswd(WifiSettingModel wifiSettingModel) {

    }

    @Override
    public void setDateAndTime(DatetimeModel datetimeModel) {

    }

    @Override
    public void setResetFactorySettings() {

    }

    @Override
    public void openUsbMode() {

    }

    @Override
    public void closeUsbMode() {

    }

    @Override
    public void dialTtPhone() {

    }

    @Override
    public void hangupTtPhone() {

    }

    @Override
    public void answerTtPhone() {

    }

}
