package com.qzy.tt.phone.data.impl;

import com.tt.qzy.view.presenter.manager.SyncManager;

public interface ITtPhoneManager {

    SyncManager getSyncManager();

    void setISyncDataListener(SyncManager.ISyncDataListener listener);
    void removeISyncDataListener();

    //短信同步回调
    void setISyncMsgDataListener(SyncManager.ISyncMsgDataListener listener);
    void removeISyncMsgDataListener();
}
