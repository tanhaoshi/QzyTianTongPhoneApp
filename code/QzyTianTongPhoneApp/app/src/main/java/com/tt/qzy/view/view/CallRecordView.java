package com.tt.qzy.view.view;

import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.view.base.BaseView;

import java.util.List;

public interface CallRecordView extends BaseView{
    void callRecordHistroy(List<CallRecordDao> list);
}
