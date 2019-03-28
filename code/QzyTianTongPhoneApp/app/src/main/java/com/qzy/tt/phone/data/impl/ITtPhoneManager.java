package com.qzy.tt.phone.data.impl;

import com.tt.qzy.view.presenter.manager.SyncManager;

public interface ITtPhoneManager {

    SyncManager getSyncManager();

    void setISyncDataListener(String tag, SyncManager.ISyncDataListener listener);

    void removeISyncDataListener();

    SyncManager.ISyncDataListener getISyncDataListener(String tag);

    //短信同步回调
    void setISyncMsgDataListener(SyncManager.ISyncMsgDataListener listener);

    void removeISyncMsgDataListener();

    void setISendShortMessage(ISendShortMessage listener);

    void removeISendShortMessage();
}
