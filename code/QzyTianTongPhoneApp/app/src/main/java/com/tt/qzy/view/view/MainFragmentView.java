package com.tt.qzy.view.view;

import com.qzy.tt.data.TtOpenBeiDouProtos;
import com.qzy.tt.data.TtPhonePositionProtos;
import com.tt.qzy.view.view.base.BaseView;

public interface MainFragmentView extends BaseView{

    void getTtPhonePosition(TtPhonePositionProtos.TtPhonePosition ttPhonePosition);

    void getTtBeiDouSwitch(TtOpenBeiDouProtos.TtOpenBeiDou ttOpenBeiDou);

    void connectedState(boolean isConnected);

    void upgradleServerApp();

    void serverAppUpgradlePercent(Integer i);

    void isServerUpdate(boolean isStatus);

    void upgradleNonconnect();

    void getSetverInitMobileStatus(boolean isInitStatus);

    void getMobileDataShow(boolean isShow);

    void getServerSosStatus(boolean isSwitch);
}
