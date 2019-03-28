package com.tt.qzy.view.presenter.activity;

import android.content.Context;

import com.qzy.tt.phone.data.TtPhoneDataManager;
import com.qzy.tt.phone.data.impl.IMainAboutListener;
import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.view.MainAboutView;

public class MainAboutPresenter extends BasePresenter<MainAboutView> implements IMainAboutListener {

    private Context mContext;

    public MainAboutPresenter(Context context) {
        this.mContext = context;
        TtPhoneDataManager.getInstance().setIMainAboutListener(this);
    }


    @Override
    public void getServerVersion(Object o) {
        mView.get().getServerVersion(o);
    }
}
