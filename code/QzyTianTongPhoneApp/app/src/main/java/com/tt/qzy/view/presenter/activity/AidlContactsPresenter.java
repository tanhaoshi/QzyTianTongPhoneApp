package com.tt.qzy.view.presenter.activity;

import android.content.Context;

import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.db.manager.CallRecordManager;
import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.view.AidlContactsView;

import java.util.List;

public class AidlContactsPresenter extends BasePresenter<AidlContactsView>{

    private Context mContext;

    public AidlContactsPresenter(Context context){
        this.mContext = context;
    }

    public List<CallRecordDao> getKeyOnPhoneList(String phone){
        List<CallRecordDao> callRecordDaos = CallRecordManager.getInstance(mContext).queryKeyOnPhoneNumber(phone);
        return callRecordDaos;
    }
}
