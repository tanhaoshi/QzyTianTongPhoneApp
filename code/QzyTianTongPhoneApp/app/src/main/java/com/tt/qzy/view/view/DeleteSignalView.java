package com.tt.qzy.view.view;

import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.view.base.BaseView;

import java.util.List;

public interface DeleteSignalView extends BaseView{

    void getRecordHistroy(List<CallRecordDao> list);

}
