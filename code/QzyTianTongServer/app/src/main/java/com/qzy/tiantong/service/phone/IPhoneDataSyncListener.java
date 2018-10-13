package com.qzy.tiantong.service.phone;

public interface IPhoneDataSyncListener {
    void onSyncCallLogFinish(boolean flag);
    void onSyncSmsFinish(boolean flag);
}
