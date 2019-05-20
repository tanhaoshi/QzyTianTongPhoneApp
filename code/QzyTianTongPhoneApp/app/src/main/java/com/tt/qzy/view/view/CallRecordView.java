package com.tt.qzy.view.view;

import com.qzy.tt.data.CallPhoneStateProtos;
import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.view.base.BaseView;

import java.util.List;

public interface CallRecordView extends BaseView{
    void getListSize(int listSize);
    void getDaoListSize(int daoListSize);
    void getDateSize(int dateSize);
    void loadRefresh(List<CallRecordDao> list);
    void loadMore(List<CallRecordDao> list);
    void getCureentPhoneState(CallPhoneStateProtos.CallPhoneState.PhoneState phoneState);
}
