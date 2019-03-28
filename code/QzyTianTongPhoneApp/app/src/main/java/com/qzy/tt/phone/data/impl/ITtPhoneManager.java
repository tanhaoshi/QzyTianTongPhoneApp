package com.qzy.tt.phone.data.impl;

import com.tt.qzy.view.presenter.manager.SyncManager;

public interface ITtPhoneManager {

    SyncManager getSyncManager();

    void setISyncDataListener(String tag, SyncManager.ISyncDataListener listener);

    void removeISyncDataListener();

    SyncManager.ISyncDataListener getISyncDataListener(String tag);

    //短信同步回调
    void setISyncMsgDataListener(String tag,SyncManager.ISyncMsgDataListener listener);

    void removeISyncMsgDataListener(String tag);

    void setISendShortMessage(ISendShortMessage listener);

    void removeISendShortMessage();
}
