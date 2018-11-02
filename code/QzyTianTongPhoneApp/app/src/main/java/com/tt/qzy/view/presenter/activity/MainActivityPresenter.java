package com.tt.qzy.view.presenter.activity;

import android.content.Context;

import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.view.MainActivityView;

public class MainActivityPresenter extends BasePresenter<MainActivityView>{

    private Context mContext;

    public MainActivityPresenter(Context context){
        this.mContext = context;
    }

    public void getAppversionRequest(){

    }
}
